/**
 * 
 */
package bobby.sfdc.prototype;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import bobby.sfdc.prototype.NewsFeedOptions.Depth;
import bobby.sfdc.prototype.NewsFeedOptions.FeedFilter;
import bobby.sfdc.prototype.NewsFeedOptions.Frequency;
import bobby.sfdc.prototype.json.*;
import bobby.sfdc.prototype.mail.SimpleMailer;
import bobby.sfdc.prototype.oauth.AuthenticationException;
import bobby.sfdc.prototype.oauth.AuthenticationHelper;
import bobby.sfdc.prototype.oauth.json.OAuthTokenSuccessResponse;
import bobby.sfdc.prototype.rest.APIExecutor;
import bobby.sfdc.prototype.rest.RawFileResponse;
import bobby.sfdc.soql.Info;
import bobby.sfdc.soql.SOQLHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Main worker that knows way too much about how to create digests :-)
 * 
 * @author bobby.white
 *
 */
public class DigestMaker implements IInfoProvider {
	private static final String INVALID_VALUE_MSG = "Invalid value: ";
	private static final String COMMUNITY_ID_PARAM = "communityId";
	public static final String DEFAULT_CONNECTEDAPP_PROPERTIES_FILE = "/connectedapp.properties";
	public static final String NEWSFEED_JSON_FILE = "newsfeed.json";

	public static final String API_BASE = "/services/data/v32.0";
	public static final String SOBJECTS_API_BASE = "/services/data/v32.0";
	public static final String DEFAULT_LOGIN_URL = "https://login.salesforce.com";
	public static final String COMPACT_LAYOUTS_PROPERTIES_FILE = "/compact_layouts.properties";
	public static final String GET_FEEDS_DIRECTORY = API_BASE + "/chatter/feeds/";
	public static final String GET_NEWS_FEED_RESOURCE = API_BASE + "/chatter/feeds/news/me/feed-elements";
	public static final String GET_USERPROFILE_RESOURCE =  API_BASE + "/connect/user-profiles/";
	public static final String GET_ORGANIZATION_RESOURCE = API_BASE + "/connect/organization";
	public static final String GET_COMMUNITIES = API_BASE + "/connect/communities";
	public static final String GET_COMMUNITY_FEEDS_DIRECTORY_TEMPLATE =  API_BASE + "/connect/communities/%communityId%/chatter/feeds/";
	public static final String GET_COMMUNITY_FEED_RESOURCE_TEMPLATE = API_BASE + "/connect/communities/%communityId%/chatter/feeds/news/me/feed-elements";
	public static final String GET_COMMUNITY_USERPROFILE_RESOURCE_TEMPLATE = API_BASE + "/connect/communities/%communityId%/chatter/users/";
	private static final String EXECUTE_SOQL_RESOURCE = SOBJECTS_API_BASE+ "/query";
	private static final char SINGLE_QUOTE = '\'';
	private static final String CONSUMER_KEY_PROP = "consumer.key";
	private static final String CONSUMER_SECRET_PROP = "consumer.secret";
	private static final String LOGIN_URL_PROP = "login.url";
	private Gson gsonParser;
	private String authToken=null;
	private String instanceUrl=null;
	private boolean isInitialized=false;
	private Map<String,Info> infoCache = new TreeMap<>();
	private String consumerKey=null;
	private String consumerSecret=null;
	private String loginUrl=null;
	private Map<String, String> queryTemplates;
	private String userId;
	private UserProfile profile;
	private boolean chattyPersona=true;
	private boolean strictMode;
	private String ccEmail=null;
	private static  Logger logger = Logger.getLogger(DigestMaker.class.getName());
	
	public static final String DATE_INPUT_PATTERN="yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
	protected static DateTimeFormatter dateInputFormatter = DateTimeFormat.forPattern(DATE_INPUT_PATTERN);

	/**
	 * Default constructor
	 */
	public DigestMaker() {
		registerGsonDeserializers();
		try {
			initObjectLayoutQueryTemplates();
		} catch (IOException e) { //NOSONAR
			logger.log(Level.SEVERE,e.getMessage()); 
		}
		profile = null;
	}


	/**
	 * Main entrypoint for commandline execution
	 * @param args
	 */
	public static void main(String[] args) {
		DigestMaker mgr = new DigestMaker();
		try {
			

			
			NewsFeedOptions options = mgr.handleNewsFeedCommandlineOptions(args);

			mgr.initObjectLayoutQueryTemplates();
			
			String configFile = options.getConfigFile() != null ? options.getConfigFile() : DEFAULT_CONNECTEDAPP_PROPERTIES_FILE;
			
			mgr.initConnectedAppFromConfigFile(configFile);
			
            mgr.login(args);
			
			UserProfile userProfile = mgr.getUserProfile(mgr.getUserID(),null);
			
			
			FeedElementPage news = mgr.getNewsFeed(options);
			
			if (news.getElements() == null || news.getElements().length==0) {
				logger.info("Feed is empty!  Nothing to do");
				return;
			}
						
			/**
			 * Perform the additional queries to get extra context
			 */
			mgr.fetchExtraRecordContext(news);
			mgr.fetchMRUData(news);

			mgr.produceDigest(news, new Scheme4Factory(userProfile.getId()), options, userProfile, mgr.getCcEmail());

			
		} catch (Exception t) { //NOSONAR
			String msg = t.getMessage() != null ? t.getMessage() : t.getClass().getName();
			logger.log(Level.SEVERE,msg);
			t.printStackTrace(); //NOSONAR
		}
		
	}


	private void login(String[] args) {
		if (args.length < 2) {
			printSyntaxStatement();			
			System.exit(1); //NOSONAR
		}
		
		String userName = args[0];
		String password = args[1];
		
		loginUrl = args.length >=3 ? args[2] : DEFAULT_LOGIN_URL;
		if (!loginUrl.startsWith("https:")) {
			loginUrl = DEFAULT_LOGIN_URL;
		}
		
		setCcEmail(args.length >=4 ? args[3] : null);
		if (ccEmail != null && (!ccEmail.contains("@") || ccEmail.startsWith("-"))) {
			ccEmail = null;  //NOSONAR  we are explicitly overriding 
		}
				
		try {
			getAuthToken(userName, password);
		} catch (IllegalStateException | AuthenticationException | HttpException | IOException e) { //NOSONAR
			logger.log(Level.SEVERE,e.getMessage());
		}
		
		if (isInitialized()) {
			System.exit(1);  //NOSONAR
		}	
		}


	/**
	 * @param args
	 * @return
	 */
	protected  NewsFeedOptions handleNewsFeedCommandlineOptions(String[] args) {
		try {
			NewsFeedOptions options = setOptionsFromCommandlineFlags(args);
			
			setStrictMode(options.isSuppressEmail());
			
			logger.info(options.toString());
			
			return options;
			
		} catch (Exception t) {  //NOSONAR
			logger.log(Level.SEVERE,t.getMessage());
			printSyntaxStatement();	
			System.exit(1); //NOSONAR forcing this exit if the commandline is invalid
		}
		return null;
	}

	/**
	 * 
	 * @param strictMode
	 */
	public void setStrictMode(boolean strictMode) {
		this.strictMode=strictMode;
	}

	/**
	 * For all of the News Items, get the Most Recently Viewed data for their parents to use in ranking
	 * @param news
	 */
	public void fetchMRUData(FeedElementPage news) {
		
		if (news==null 
				|| news.getElements()==null 
				|| news.getElements().length==0) { 
			return; 
			}
		
		// Iterate through the news and get a distinct list of Parents (by Id)
		Set<String> parents = getAllParents(news);
		
		if (parents.isEmpty()) {
			return;
		}
		
		// Compose Query for MRU data
		StringBuilder query = new StringBuilder("SELECT Id,LastViewedDate FROM RecentlyViewed ");
		buildWhereClause(query, parents);		

		// Execute the Query and index by results Parent
		Map<String,Info> mruByParent = new TreeMap<>();
		for (Info mruInfo : executeSOQL(query.toString())) {
			mruByParent.put(mruInfo.getId(),mruInfo);
		}
		
		// Iterate through the news again, apply MRU data to parents (as it has been denormalized by the Feed and parents aren't collapsed to a single instance)
		for (BaseFeedElement element : news.getElements()) {
			BaseSummary parent = element.getParent();
			if (parent != null) {
				Info mru = mruByParent.get(parent.getId());
				if (mru!=null) {
				   parent.setMruDate(mru.getValue("LastViewedDate"));
				}
			}
		}

	}


	/**
	 * @param query
	 * @param parents
	 */
	protected void buildWhereClause(StringBuilder query, Set<String> parents) {
		boolean isFirst=true;
		query.append("WHERE Id in (");
		for (String parentId : parents) {
			if (isFirst) {
				isFirst=false;
			} else {
				query.append(", ");
			}
			query.append(SINGLE_QUOTE).append(parentId).append(SINGLE_QUOTE);
		}
		query.append(')');
	}
	
	/**
	 * Overload that takes a List instead of a Set
	 * @param query
	 * @param idList
	 */
	private void buildWhereClause(StringBuilder query, ArrayList<String> idList) {
		Set<String> idSet = new TreeSet<>();
		idSet.addAll(idList);
		buildWhereClause(query,idSet);
	}


	/**
	 * @param news
	 */
	protected Set<String> getAllParents(FeedElementPage news) {
		Set<String> parents  = new TreeSet<>();
		for (BaseFeedElement element : news.getElements()) {
			if (element.getParent() != null) {
				parents.add(element.getParent().getId());
			}
		}
		return parents;
	}
	
	/**
	 * Initialize the List of Groups to Exclude based on Digest settings.
	 * We want to exclude any group for which we receive the digest.
	 * @return map of groupids
	 */
	public Map<String,Info> getGroupsToExclude() {
		StringBuilder query = new StringBuilder("SELECT Id,CollaborationGroupId, CollaborationGroup.Name, NotificationFrequency FROM CollaborationGroupMember WHERE NotificationFrequency <> 'N' AND MemberId=");
		query.append(SINGLE_QUOTE).append(this.getUserID()).append(SINGLE_QUOTE);
		
		// Execute the Query and index by results Parent
		Map<String,Info> exclusionList = new TreeMap<>();
		for (Info settingsInfo : executeSOQL(query.toString())) {
			String groupId = settingsInfo.getValue("CollaborationGroupId");
			exclusionList.put(groupId,settingsInfo);
		}
		
		return exclusionList;
	
	}

	/**
	 * 
	 */
	public static void printSyntaxStatement() {
		System.out.println("userid and password are required parameters!:\n  myuser@example.com mypassword"); //NOSONAR
		System.out.println("Syntax:  DigestMaker <username> <password> [loginURL] [ccEmailAddress] [FLAGS]"); //NOSONAR
		System.out.println("\nIf omitted, default Login URL=" + DEFAULT_LOGIN_URL); //NOSONAR
		System.out.println("For GUS, username form is:  first.last@gus.com"); //NOSONAR
		System.out.println("For Org62, username form is:  first.last@salesforce.com"); //NOSONAR
		
		System.out.println("\n\nFlags:");  //NOSONAR
		
		for (Flags flag : Flags.values()) {
			System.out.println("-"+ flag.getLabel() + " " + flag.getDescription());  //NOSONAR
		}
	}

	/**
	 * Process the Commandline Flags to set parameters for the News Feed
	 * @param args
	 */
	public static NewsFeedOptions setOptionsFromCommandlineFlags(String[] args) {
		NewsFeedOptions options = new NewsFeedOptions();
		options.setFrequency(NewsFeedOptions.Frequency.WEEKLY);
		options.setPageSize(100);
		
		for (String flag : args) {
			if (!flag.startsWith("-")) {
				// it's not a flag, skip it
				continue;
			} 
			String[] parts = flag.substring(1).split(":");
			String flagPart = parts.length >= 1 ? parts[0] : "";
			String valuePart = parts.length > 1 ? parts[1] : "";
			
			
			if (flagPart.compareToIgnoreCase(Flags.FREQUENCY.getLabel())==0) {
				handleFrequencyOptionFlag(options, valuePart);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.DEPTH.getLabel())==0) {
				handleDepthOptionFlag(options, valuePart);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.PAGESIZE.getLabel())==0) {
				handlePageSizeOptionFlag(options, valuePart);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.BUNDLESIZE.getLabel())==0) {
				handleBundleSizeOptionFlag(options, valuePart);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.COMMENTCOUNT.getLabel())==0) {
				handleCommentCountOptionFlag(options, valuePart);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.NETWORK.getLabel())==0) {
				handleNetworkOptionFlag(options, valuePart);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.GROUP.getLabel())==0) {
				handleGroupOverlapOptionFlag(options, valuePart);
			}
		
			if (flagPart.compareToIgnoreCase(Flags.FILTER.getLabel())==0) {
				handleFilterOptionFlag(options, valuePart);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.MAIL.getLabel())==0) {
				options.setSuppressEmail(true);
			}
			
			if (flagPart.compareToIgnoreCase(Flags.CONFIG.getLabel())==0) {
				handleConfigFileOptionFlag(options, valuePart);
			}
			if (flagPart.compareToIgnoreCase(Flags.FEED_OVERRIDE.getLabel())==0) {
				handleFeedOverrideOptionFlag(options, valuePart);
			}


		}
		return options;
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleFeedOverrideOptionFlag(NewsFeedOptions options,
			String valuePart) {
		String resource = valuePart.isEmpty() ? null : valuePart;
		options.setFeedResourceOverride(resource);
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleConfigFileOptionFlag(NewsFeedOptions options,
			String valuePart) {
		String filename = valuePart.isEmpty() ? null : valuePart;
		options.setConfigFile(filename);
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleFilterOptionFlag(NewsFeedOptions options,
			String valuePart) {
		if (valuePart.compareToIgnoreCase(FeedFilter.ALL_QUESTIONS.getName())==0) {
			options.setFilter(FeedFilter.ALL_QUESTIONS);
		} else if (valuePart.compareToIgnoreCase(FeedFilter.SOLVED_QUESTIONS.getName())==0){
			options.setFilter(FeedFilter.SOLVED_QUESTIONS);					
		} else if (valuePart.compareToIgnoreCase(FeedFilter.UNANSWERED_QUESTIONS.getName())==0){
			options.setFilter(FeedFilter.UNANSWERED_QUESTIONS);					
		} else if (valuePart.compareToIgnoreCase(FeedFilter.UNSOLVED_QUESTIONS.getName())==0){
			options.setFilter(FeedFilter.UNSOLVED_QUESTIONS);					
		} else if (valuePart.compareToIgnoreCase(FeedFilter.NONE.getName())==0){
			options.setFilter(FeedFilter.NONE);					
		} else {
			throw new IllegalArgumentException("Invalid Filter keyword: "+valuePart);
		}
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleGroupOverlapOptionFlag(NewsFeedOptions options,
			String valuePart) {
		String value = valuePart.isEmpty() ? null : valuePart;
		Boolean eliminateGroupDigestOverlap = Boolean.parseBoolean(value);
		options.setEliminateGroupDigestOverlap(eliminateGroupDigestOverlap);
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleNetworkOptionFlag(NewsFeedOptions options,
			String valuePart) {
		String networkId = valuePart.isEmpty() ? null : valuePart;
		options.setNetwork(networkId);
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleCommentCountOptionFlag(NewsFeedOptions options,
			String valuePart) {
		try {
			int count = Integer.parseInt(valuePart);
			count = Math.min(Math.max(0, count),25); // force the value to between 0 and 25
			options.setRecentCommentCount(count);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_VALUE_MSG + valuePart + " expected an integer between 0 and 25");
		}
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleBundleSizeOptionFlag(NewsFeedOptions options,
			String valuePart) {
		try {
			int count = Integer.parseInt(valuePart);
			count = Math.min(Math.max(0, count),10); // force the value to between 0 and 10
			options.setElementsPerBundle(count);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_VALUE_MSG + valuePart + " expected an integer between 1 and 10");
		}
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handlePageSizeOptionFlag(NewsFeedOptions options,
			String valuePart) {
		try {
			int count = Integer.parseInt(valuePart);
			count = Math.min(Math.max(1, count),100); // force the value to between 1 and 100
			options.setPageSize(count);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_VALUE_MSG + valuePart + " expected an integer between 1 and 100");
		}
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleDepthOptionFlag(NewsFeedOptions options,
			String valuePart) {
		if (valuePart.compareToIgnoreCase("A")==0) {
			options.setDepth(Depth.ALLUPDATES);
		} else if (valuePart.compareToIgnoreCase("F")==0){
			options.setDepth(Depth.FEWERUPDATES);
		} else {
			throw new IllegalArgumentException("Invalid Depth: "+valuePart);
		}
	}


	/**
	 * @param options
	 * @param valuePart
	 */
	protected static void handleFrequencyOptionFlag(NewsFeedOptions options,
			String valuePart) {
		if (valuePart.compareToIgnoreCase("D")==0) {
			options.setFrequency(Frequency.DAILY);
		} else if (valuePart.compareToIgnoreCase("W")==0){
			options.setFrequency(Frequency.WEEKLY);					
		} else if (valuePart.compareToIgnoreCase("E")==0){
			options.setFrequency(Frequency.EPOC);					
		} else {
			throw new IllegalArgumentException("Invalid Frequency: "+valuePart);
		}
	}
	
	public enum Flags {
		FREQUENCY("F","Frequency - W(weekly), D(daily), or E(epoc)"), 
		DEPTH("D","Depth - A(All) F(Fewer) items"), 
		PAGESIZE("P","Maximum number of Feed Items to retrieve [1..100]"),
		BUNDLESIZE("B","Element Bundle size [0..10]"), 
		COMMENTCOUNT("C","Max Comments to retrieve  [0..25]"),
		FILTER("X","Filter the News Feed: " + FeedFilter.allSettings()),
		NETWORK("N","Community [<NetworkId>]"),
		GROUP("G","Eliminate Group Digest Overlap [true,false]"),
		MAIL("NOSEND","Suppress mail send"),
		CONFIG("Z","Configuration file for connected app"),
		FEED_OVERRIDE("O","Override the feed with a specific resource");
		private String label;
		private String description;

		Flags(String label, String description) {
		this.label=label;
		this.description=description;
		}
		public String getLabel() {
			return label;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
	}

	/**
	 * Produce the Digest using the scheme associated with the Factory in use
	 * @param news
	 * @param factory
	 * @param options 
	 * @param userProfile 
	 * @param ccEmail
	 */
	public void produceDigest(FeedElementPage news,
			IDigestSectionIndexKeyFactory factory, NewsFeedOptions options, UserProfile userProfile, String ccEmail) {
		
		/**
		 * Save this so that it will be available later
		 */
		this.setUserProfile(userProfile);
		
		factory.initialize(getUserID());
		
		Map<SectionIndexKey, BaseFeedElement> index = organizeDigest(news,factory);
		
		
		String salutation;
		
		if (chattyPersona) {
		   salutation = "Hi " + userProfile.getUserDetail().getFirstName();
		   salutation += ", catch up on " + options.getFrequency().getChattyLabel() + ".";
		} else {
		   salutation = options.getFrequency().getLabel() 
					+ " Digest for " + userProfile.getUserDetail().getDisplayName();
		}
		
		String footer = "Digest Prototype, scheme=" + factory.getSchemeName()
				+ " "
				+ factory.describeStrategy()
				+ "\n" + options.toString();
		
		String html = renderDigestAsHTML(index,salutation,footer);

		if (options.isSuppressEmail()) {
			//NOSONAR String output = renderDigestAsXML(index,salutation,footer);
			//NOSONAR saveToFile("digest_prototype."+factory.getSchemeName()+".xml",output);
			
			saveToFile("digest_prototype."+factory.getSchemeName()+".html",html);	
		} else {
			SimpleMailer mailer = new SimpleMailer();
			mailer.sendEmailMessage("Digest Prototype " + options.getFrequency() + " using " + factory.getSchemeName(), html, userProfile.getUserDetail().getEmail(), ccEmail);
		}
		
		
		factory.destroy();
	}
	
	/**
	 * Obtain the AuthToken using the Userid/Password flow
	 * 
	 * Assumes that the connection info has already been initialized
	 * 
	 * @param userName in the form  user1@company.com
	 * @param password you may be required to append your Security Token to your password
	 * @throws IllegalStateException
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws AuthenticationException 
	 */
	public void getAuthToken(String userName, String password) throws AuthenticationException, HttpException, IOException {
		if (this.loginUrl==null) {
			throw new IllegalStateException("loginUrl must be initialized!");
		}
		if (this.consumerKey==null) {
			throw new IllegalStateException("consumerKey must be initialized!");
		}
		if (this.consumerSecret==null) {
			throw new IllegalStateException("consumerSecret must be initialized!");
		}


		OAuthTokenSuccessResponse result = new AuthenticationHelper().getAuthTokenUsingPasswordFlow(this.loginUrl, userName, password, this.consumerKey, this.consumerSecret);
		setAuthToken(result);
	}
	
	/**
	 * Set the Internal Members from the OAuthTokenSuccessResponse
	 * 
	 * @param auth  initialized by an OAuth based flow
	 */
	public void setAuthToken(OAuthTokenSuccessResponse auth) {
		if (auth!=null) {
		   this.setIsInitialized(true);
		   this.authToken = auth.getAccess_token();
		   this.userId = auth.getId();
		   this.instanceUrl = auth.getInstance_url();
		}
	}

	/**
	 * Initialize Connection Information from the Properties File
	 * @param fileName
	 * @throws IOException 
	 */
	public void initConnectedAppFromConfigFile(String fileName) throws IOException {
		Properties props = new Properties();
		InputStream propStream = DigestMaker.class.getResourceAsStream(fileName);
		if (propStream == null) {
			throw new IllegalArgumentException("Configuration file " + fileName + " cannot be opened");
		}
		props.load(propStream);
		this.consumerKey = props.getProperty(CONSUMER_KEY_PROP);
		this.consumerSecret = props.getProperty(CONSUMER_SECRET_PROP);
		this.loginUrl = props.getProperty(LOGIN_URL_PROP);
	}
	
	/**
	 * Initialize the Query templates that will be used per ObjectType.
	 * 
	 * Using a file in this prototype instead of reading from MetaData API
	 * 
	 * *** NOTE *** This implementation is limitation of the Prototype and should be replaced
	 * by Production code that queries the Compact Layout Definitions using the MetaData API
	 * 
	 * @throws IOException 
	 */
	public void initObjectLayoutQueryTemplates() throws IOException  {
		Properties queryTemplatesProps = new Properties();
		InputStream propsResource = DigestMaker.class.getResourceAsStream(COMPACT_LAYOUTS_PROPERTIES_FILE);
		if (propsResource == null) {
			logger.log(Level.WARNING,"Unable to load Compact Layouts Property file!");
			return;
		}
		queryTemplatesProps.load(propsResource);
		
		Enumeration<Object> elements = queryTemplatesProps.keys();
		this.queryTemplates = new TreeMap<>();
		while (elements.hasMoreElements()) {
			String typeId = (String) elements.nextElement();
			String template = queryTemplatesProps.getProperty(typeId);
			queryTemplates.put(typeId,template);
		}
		
	}

	/**
	 * Render the Digest in HTML format
	 * @param index
	 * @param salutation
	 * @param footer
	 * @return
	 */
	private String renderDigestAsHTML(Map<SectionIndexKey, BaseFeedElement> index, String salutation, String footer) {
		StringBuffer html = new StringBuffer();
		HTMLDigestRenderer engine = new HTMLDigestRenderer(instanceUrl,getUserProfile(),html,this.strictMode);
		
		engine.init(this);
		engine.setSalutationText(salutation);
		engine.setFooterText(footer);
		
		SectionBasedRenderer renderer = new SectionBasedRenderer(engine);
		
		
		// Now, walk the index in natural order, checking for new sections, subsections
		for (SectionIndexKey key : index.keySet()) {
			BaseFeedElement currentItem = index.get(key);
			
			renderer.setNewKey(key,currentItem);
		}
		renderer.closeDigest();

		return html.toString();
	}
	
	public void setUserProfile(UserProfile profile) {
		this.profile = profile;
	}

	/**
	 * Return the UserProfile or throw an exception if it hasn't been set
	 * @return
	 */
	private UserProfile getUserProfile() {
		if (this.profile==null) {
			throw new IllegalStateException("UserProfile has not been saved before calling this method");
		}
		return this.profile;
	}

	private static void saveToFile(String fileName, String output) {
		File outputFile = new File(fileName);

		try (FileWriter writer = new FileWriter(outputFile);) {
			
			writer.write(output);
			writer.flush();
			writer.close();
			logger.info("output saved to file==>"+outputFile.getAbsolutePath());
		} catch (IOException e) { //NOSONAR
			logger.log(Level.SEVERE,e.getMessage());
		}
		
	}


	public Map<SectionIndexKey,BaseFeedElement> organizeDigest(FeedElementPage news, IDigestSectionIndexKeyFactory factory) {
		
		
		/** Walk through the elements in their original form, build a three-tier presentation scheme as we go
		 * Parent Type - highest level organization (order by "kindPriority" + type name)
		 *    Parent Instance - second level (order by Parent Instance Name)
		 *       FeedElement - third level  (order by ModifiedDate descending )
		 **/
		Map<SectionIndexKey,BaseFeedElement> masterIndex = factory.createMap();
		
		for ( BaseFeedElement element  : news.getElements()) {
			SectionIndexKey key = factory.createKeyFromElement(element);
			if (key != null) {
				masterIndex.put(key, element);				
			}
		}
		
		return masterIndex;
	}
	
	/**
	 * Iterate through the index and render the digest in XML form
	 * @param masterIndex
	 * @param footer text to include in the footer section
	 * @param salutation a greeting for the recipient
	 * @return an XML fragment which represents the digest.
	 */
	public String renderDigestAsXML(Map<SectionIndexKey,BaseFeedElement> masterIndex, String salutation, String footer) {
		StringBuffer xml = new StringBuffer();
		XMLDigestRenderer engine = new XMLDigestRenderer(instanceUrl,getUserProfile(),xml);
		
		engine.init(this);
		engine.setSalutationText(salutation);
		engine.setFooterText(footer);
		
		SectionBasedRenderer renderer = new SectionBasedRenderer(engine);
		
		
		// Now, walk the index in natural order, checking for new sections, subsections
		for (SectionIndexKey key : masterIndex.keySet()) {
			BaseFeedElement currentItem = masterIndex.get(key);
			
			renderer.setNewKey(key,currentItem);
		}
		renderer.closeDigest();

		return xml.toString();
	}

	
		
	
	public String getUserID() {
		
		if (userId == null) {
			throw new IllegalStateException("Authentication has not been completed successfully and instance is not initialized");
		}
		
		return userId.substring(userId.lastIndexOf('/')+1);
		
	}
	
	/**
	 * Get the UserProfile information about the specified user
	 * 
	 * @param userId
	 * @param networkId 
	 * @return UserProfile for the digest user
	 * @throws URISyntaxException 
	 * @throws AuthenticationException 
	 * @throws IOException 
	 */
	public UserProfile getUserProfile(String userId, String networkId) throws URISyntaxException, IOException, AuthenticationException {
	    CloseableHttpClient client = HttpClientBuilder.create().build();
	    URIBuilder builder;
	    boolean isInternalScope = networkId == null || networkId.isEmpty();
		if (isInternalScope) {
			builder = new URIBuilder(getInstanceUrl() + GET_USERPROFILE_RESOURCE + userId);
		} else {
			builder = new URIBuilder(getInstanceUrl() + getURLFromURLTemplate(GET_COMMUNITY_USERPROFILE_RESOURCE_TEMPLATE,COMMUNITY_ID_PARAM,networkId) + userId);
		}

		HttpGet getUserProfile = new HttpGet(builder.build());
		APIExecutor<UserProfile> api1 = new APIExecutor<>(UserProfile.class,getAuthToken());
		profile = api1.processAPIResponse(client, getUserProfile);
		
		if (!isInternalScope && profile.getUserDetail() == null) {
			/**
			 * Nasty hack to reparse the previous response without resubmitting it
			 */
			APIExecutor<UserSummary> api2 = new APIExecutor<>(UserSummary.class,getAuthToken());
			UserSummary summary = api2.parseJsonBody(api1.getLastResponseBody());
			profile.setSummary(summary);
		}
		return profile;
	}
	
	/**
	 * Retrieve basic information about the current Salesforce Organization
	 * 
	 * @return a populated Organization response body
	 * @throws URISyntaxException 
	 * @throws AuthenticationException 
	 * @throws IOException 
	 */
	public Organization getOrganizationInfo() throws URISyntaxException, IOException, AuthenticationException {
	    CloseableHttpClient client = HttpClientBuilder.create().build();
	    URIBuilder builder = new URIBuilder(getInstanceUrl()+ GET_ORGANIZATION_RESOURCE);				
		HttpGet getOrganizationInfoAPI = new HttpGet(builder.build());
		return new APIExecutor<Organization>(Organization.class,getAuthToken()).processAPIResponse(client, getOrganizationInfoAPI);
	}
	
	/**
	 * Get the List of Communities for this org
	 * @throws URISyntaxException 
	 * @throws AuthenticationException 
	 * @throws IOException 
	 */
	public CommunitiesPage getCommunitiesList() throws URISyntaxException, IOException, AuthenticationException {
	    CloseableHttpClient client = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(getInstanceUrl()+ GET_COMMUNITIES);
						
		HttpGet getCommunitiesOperation = new HttpGet(builder.build());
		return new APIExecutor<CommunitiesPage>(CommunitiesPage.class, getAuthToken()).processAPIResponse(client, getCommunitiesOperation);		
	}
	
	/**
	 * Get the Feeds available in this Internal Org
	 * @throws URISyntaxException 
	 * @throws AuthenticationException 
	 * @throws IOException 
	 */
	public FeedDirectory getFeedDirectory() throws URISyntaxException, IOException, AuthenticationException {
	    CloseableHttpClient client = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(getInstanceUrl()+ GET_FEEDS_DIRECTORY);
						
		HttpGet operation = new HttpGet(builder.build());
		return new APIExecutor<FeedDirectory>(FeedDirectory.class, getAuthToken()).processAPIResponse(client, operation);		
	}
	
	/**
	 * Get the Feeds available in this Community
	 * 
	 * @param networkId id for the Community
	 * @throws URISyntaxException 
	 * @throws AuthenticationException 
	 * @throws IOException 
	 */
	public FeedDirectory getCommunityFeedDirectory(String networkId) throws URISyntaxException, IOException, AuthenticationException {
	    CloseableHttpClient client = HttpClientBuilder.create().build();
		URIBuilder builder = new URIBuilder(getInstanceUrl()+ getURLFromURLTemplate(GET_COMMUNITY_FEEDS_DIRECTORY_TEMPLATE, COMMUNITY_ID_PARAM, networkId));
						
		HttpGet operation = new HttpGet(builder.build());
		return new APIExecutor<FeedDirectory>(FeedDirectory.class, getAuthToken()).processAPIResponse(client, operation);		
	}



	
	/**
	 * For every parent record in the digest, efficiently get extra context
	 *  that can be used to enhance the digest and make it easier to read.
	 *  In a production version, we would use the compact layout for each type:
	 *  (Account, Opportunity, Case) to define the fields we want.
	 *  For this prototype, I've hardwired them.
	 */
	public void fetchExtraRecordContext(FeedElementPage news) {
		// Iterate through the list of feed items, gather a list of ids, grouped by type.
		if (news == null) {
			throw new IllegalArgumentException("News feed is null, can't fetch extra info");
		}
		
		if (news.getElements() == null) {
			logger.info("News.getElements() returned null");
			return;
		}
		
		Map<String,String> ids = new TreeMap<>();
		
		for ( BaseFeedElement element  : news.getElements()) {
			String id = element.getParent().getId();
			ids.put(id, id);
		}
		
		/** Now, walk that list of ids in order, gather a group of ids for the same object type **/
		String type = null;
		ArrayList<String> idList = new ArrayList<>();
		for (String current : ids.keySet()) {
			String thisType = current.substring(0,3); // first three chars is type
			
			if (type != null && type.compareTo(thisType)!=0) {
				// We've exhausted this type
				fetchExtraRecordContextForType(idList);
				// empty the list now
				idList.clear();
			}

			if (type == null || thisType.compareTo(type)!=0) {
				type = thisType; // set the new type
			}
			idList.add(current);
			
		}
		
		// For the final list we've built, execute it
		fetchExtraRecordContextForType(idList);
		
		
		
	}
	
	/**
	 * Execute a SOQL query to get the info
	 * @param idList
	 */
	protected void fetchExtraRecordContextForType(ArrayList<String> idList) {
		String type = getType(idList);
		if (skipThisType(type))
			return;

		
		StringBuilder query = getInfoQueryFieldsByType(type);
		
		if (query == null || query.toString().trim().isEmpty()) {
			String exampleId = idList.get(0);
			String typeID = exampleId.substring(0,3);
			String label = this.getLabelForType(typeID);
			logger.log(Level.WARNING,"No layout/query defined for Type="  + (label != null ? label : typeID)  + " exampleId: " + exampleId);
			return;
		}
		
		buildWhereClause(query,idList);
		
		// Execute the query
		logger.log(Level.FINE,query.toString());
		
		// fetch the results and save the extra info
		for (Info current : executeSOQL(query.toString())) {
			addToUserCache(current);
		}
		

		
	}

	private boolean skipThisType(String type) {
		String suppressedTypes = queryTemplates.get("suppress");
		return (suppressedTypes == null) ? false : suppressedTypes.contains(type);
	}

	private String getType(ArrayList<String> idList) {
		if (idList == null || idList.isEmpty()) {
			return "";
		}
		else {
			return idList.get(0).substring(0,3);
		}
	}

	/**
	 * Save the state to user cache
	 * @param current
	 */
	private void addToUserCache(Info current) {
		this.infoCache .put(current.getId(),current);
	}

	/**
	 * Hard Coded Queries for the prototype
	 * 
	 * @param type  3 character type ID
	 * @return String with select list and From object
	 */
	private StringBuilder getInfoQueryFieldsByType(String type) {
		StringBuilder query = new StringBuilder();
		
		String template = this.queryTemplates.get(type+".query");
		
		if (template!=null) {
			query.append(template);
		}	
		return query;
	}
	
	/**
	 * Get the news feed with the default settings.  A convenience overload signature.
	 * @return a populated FeedElementPage object with the users newsfeed
	 * @throws Exception 
	 */
	public FeedElementPage getNewsFeed() throws Exception {
		return getNewsFeed(new NewsFeedOptions());
	}

	/**
	 * Get the User's NewsFeed
	 * @param options control what kind of posts, how many, and the depth of posts that are returned in the news feed
	 * @throws Exception 
	 * @throws Throwable 
	 */
	public FeedElementPage getNewsFeed(NewsFeedOptions options) throws Exception {
		if (options == null) 
			throw new IllegalStateException("options is a required parameter");

	    CloseableHttpClient client = HttpClientBuilder.create().build();
		try {

	        DateTime cutoff  = calculateDigestCutoffTime(options.getFrequency());

			URIBuilder builder = determineFeedType(options);
			
			if (options.getFilter() != NewsFeedOptions.FeedFilter.NONE) {
				builder.setParameter("filter",options.getFilter().getName());
			}
			builder.setParameter("density", options.getDepth().getName());
			builder.setParameter("pageSize", Integer.toString(options.pageSize));
			builder.setParameter("recentCommentCount",Integer.toString(options.getRecentCommentCount()));
			builder.setParameter("sort","LastModifiedDateDesc");
			
			logger.info("News feed options:"+builder.build());
			
			HttpGet getNewsFeed = new HttpGet(builder.build());
			APIExecutor<FeedElementPage> api = new APIExecutor<>(FeedElementPage.class,getAuthToken());
			FeedElementPage news = api.processAPIResponse(client, getNewsFeed);
			String json = api.getLastResponseBody();
			if (options.isSuppressEmail()) {
			  saveToFile(NEWSFEED_JSON_FILE, json);
			}
			
			logger.log(Level.FINEST,json);
			removeFeedPostsOlderThanCutoff(news, cutoff);
			if (options.isEliminateGroupDigestOverlap()) {
				removeGroupDigestOverlap(news);
			}
			return news;
			
		} catch (IOException | URISyntaxException | AuthenticationException t) {
			logger.log(Level.SEVERE,t.getMessage());
			throw t;
		}
	}

	/**
	 * Since we support building the digest from any of the Chatter Feeds, we need to determine which one applies in this 
	 * run.  Default to NEWS feed.
	 * 
	 * @param options
	 * @return
	 * @throws URISyntaxException
	 */
	protected URIBuilder determineFeedType(NewsFeedOptions options)
			throws URISyntaxException {
		URIBuilder builder;
		if (options.getFeedResourceOverride() != null) {
			builder = new URIBuilder(getInstanceUrl()+options.getFeedResourceOverride());
		} else if (options.getNetwork() == null || options.getNetwork().isEmpty()) {
			builder = new URIBuilder(getInstanceUrl()+GET_NEWS_FEED_RESOURCE);
		} else {
			builder = new URIBuilder(getInstanceUrl() + getURLFromURLTemplate(GET_COMMUNITY_FEED_RESOURCE_TEMPLATE,COMMUNITY_ID_PARAM,options.getNetwork()));
		}
		return builder;
	}


	/**
	 * Remove posts which overlap with the Group Digests
	 * Keep posts which are explicit @mentions of the User
	 * 
	 * @param news
	 */
	private void removeGroupDigestOverlap(FeedElementPage news) {
		if (news==null) {
			throw new IllegalArgumentException("Newsfeed must not be null!");
		}
		if (news.getElements()==null || news.getElements().length == 0   ) {
			return;
		}
		Map<String, Info> exclusionList = this.getGroupsToExclude();
		logger.info("Groups to exclude:" + exclusionList.size());
		
		ArrayList<BaseFeedElement> onesToKeep = new ArrayList<>();
		for(BaseFeedElement current : news.getElements()) {
			boolean skipit=false; // by default, keep it
			if (current.isRecommendation()) {
				// keep all Recommendations
				skipit=false; 
			} else if ((current.getParent() != null && current.getParent().getId().compareTo(this.getUserID())==0)  
					|| current.isAtMentioned(this.getUserID())) {
				// explicitly keep it because it belongs in the "To Me"
				skipit=false;
			} else if (current.getParent() != null) {
				// Directly contained by one of the Groups we want to exclude
				if (exclusionList.containsKey(current.getParent().getId())) {
					skipit=true;
					logger.log(Level.INFO, "skipping FeedItem directly parented by Group: "+ current.getId() + " Group: " + current.getParent().getName());

				}
			} else if (current.isAtMentioned(exclusionList.keySet())) {
				skipit=true;
				logger.log(Level.INFO, "skipping FeedItem Group @mentioned: "+ current.getId());

			}
			if (!skipit) {
				onesToKeep.add(current);
			}
		}
		
		logger.info("Excluding Feed Item Overlap with Group Digests: original count=" + news.getElements().length + " final="+onesToKeep.size());
		BaseFeedElement[] keepers = new BaseFeedElement[onesToKeep.size()];
		onesToKeep.toArray(keepers);
		news.setElements(keepers);
		
	}

	/**
	 * 
	 * @param templateURL - URL must include %parameterName%
	 * @param parameterName  - this must be in the URL, will be replaced with value
	 * @param value - the value to be substituted
	 * @return
	 */
	private String getURLFromURLTemplate(
			String templateURL, String parameterName,
			String value) {
		
		if (templateURL == null) {
			throw new IllegalArgumentException("Template URL must not be null!");
		}
		if (parameterName == null) {
			throw new IllegalArgumentException("parameterName must not be null!");
		}
		if (value == null) {
			throw new IllegalArgumentException("value must not be null!");
		}
		
		String marker = "%" + parameterName + "%";
		if (!templateURL.contains(marker)) {
			throw new IllegalArgumentException("Template (" + templateURL + ") must contain " + marker);
		}
		
		return templateURL.replace(marker, value);
	}

	/**
	 * A destructive method that looks at the news feed, examines the feed elements, discarding those older than the cutoff time.
	 * 
	 * @param news
	 * @param cutoff
	 */
	private void removeFeedPostsOlderThanCutoff(FeedElementPage news,
			DateTime cutoff) {
		
		if (news==null || news.getElements()==null)
			return;
		
		List<BaseFeedElement> onesToKeep = new ArrayList<>();
		
		for (BaseFeedElement current : news.getElements()) {
			if (current.isRecommendation()) {
				onesToKeep.add(current); // Always keep Recommendations, regardless of age
			} else {
				DateTime age = convertToDateTime(current.getModifiedDate());
				
				if (age.isAfter(cutoff)) {
					onesToKeep.add(current);
					logger.log(Level.FINER,"keeping: "+ current.getId() + " created: " + current.getCreatedDate() + " mod: " + current.getModifiedDate());
					continue;
				}
				
			   logger.log(Level.FINE,"discarding old feeditem: id= " + current.getId() + " mod-dt: " + current.getModifiedDate() + " cutoff: " + cutoff.toString());
			}
		}
		
		BaseFeedElement[] keepers = new BaseFeedElement[onesToKeep.size()];
		onesToKeep.toArray(keepers);
		news.setElements(keepers);
	}

	/*
	 * Convert a String to a Joda DateTime
	 * */
	public static DateTime convertToDateTime(String stringDate) {
		if (stringDate==null) {
			return null;
		} else {
			return dateInputFormatter.parseDateTime(stringDate);
		}
	}

	/**
	 * Determine a cutoff time which represents the oldest feed item that we'll include in the digest
	 * @param frequency
	 * @return a JavaDate which represents the precise cutoff time.
	 */
	private DateTime calculateDigestCutoffTime(Frequency frequency) {
		DateTime now = new DateTime(System.currentTimeMillis());
		int days;
		switch(frequency) {
			case WEEKLY:
			  days = 7;
			  break;
			case DAILY:
			  days = 1;
			  break;
			case EPOC:
		      days = 365;
		      break;
			default:
			   throw new IllegalArgumentException("Unknown Frequency setting" + frequency);
		}
		return  now.minusDays(days);
	}

	/**
	 * 
	 * @param query
	 */
	protected List<Info> executeSOQL(String query) {
	    CloseableHttpClient client = HttpClientBuilder.create().build();

		URIBuilder builder;
		try {
			builder = new URIBuilder(getInstanceUrl()+EXECUTE_SOQL_RESOURCE);
			builder.setParameter("q", query);
			
			HttpGet executeSOQL = new HttpGet(builder.build());
			
			executeSOQL.addHeader("Authorization","Bearer "+ getAuthToken());
			HttpResponse response = client.execute(executeSOQL);
			int code = response.getStatusLine().getStatusCode();
			
			logger.log(Level.FINE,response.getStatusLine().toString());
			
			if (code==200) {
				String json = EntityUtils.toString(response.getEntity());
				return SOQLHelper.parseResults(json);
			} else {
				logger.log(Level.WARNING,EntityUtils.toString(response.getEntity()));
				return new ArrayList<>();
			}
			
		} catch (Exception t) {
			logger.log(Level.SEVERE,t.getMessage() + " query=" + query);
		}
		return new ArrayList<>();
	}
	

	private void registerGsonDeserializers() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
//NOSONAR		gsonBuilder.registerTypeAdapter(FeedElementPage.class, new FeedElementPageDeserializer());
		gsonParser = gsonBuilder.create();
	}
	protected Gson getGson() {
		return gsonParser;
	}

	protected String getAuthToken() {
		return authToken;
	}

	protected void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	protected String getInstanceUrl() {
		return instanceUrl;
	}

	protected void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}

	protected boolean isInitialized() {
		return isInitialized;
	}

	protected void setIsInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IInfoProvider2#getInfoForId(java.lang.String)
	 */
	@Override
	public Info getInfoForId(String id) {
		return infoCache.get(id);
	}
	public Map<String, Info> getInfoCache() {
		return infoCache;
	}

	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * @return the loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @param loginUrl the loginUrl to set
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	public String getLabelForType(String type) {
		if (type != null && this.queryTemplates != null) {
			String key = type + ".type.label";
			return this.queryTemplates.get(key);
		} else if (this.queryTemplates == null) {
			logger.log(Level.WARNING,"Compact layouts have not be properly initialized! type="+type);
		}
		return null;
	}

	@Override
	public String getCacheDir() {
		return "./images";
	}

	@Override
	public void fetchAndStore(ContentCapability content,
			String expectedCacheFileName) {
		
		if (content.getThumb120By90RenditionStatus().compareTo("Success")==0) {
			try {
				String renditionEndpoint = content.getRenditionUrl();
				CloseableHttpClient client = HttpClientBuilder.create().build();
				URIBuilder builder = new URIBuilder(getInstanceUrl()+ renditionEndpoint);
								
				HttpGet operation = new HttpGet(builder.build());
				APIExecutor<RawFileResponse> api = new APIExecutor<>(RawFileResponse.class, getAuthToken());
				RawFileResponse imageFile = api.processFileResponse(client, operation);	
				
				// now save the file!
				File cacheFile = new File(expectedCacheFileName);
				if (cacheFile.exists()) {
					cacheFile.delete();
				}
				FileOutputStream fos = new FileOutputStream(cacheFile);
				try {
				   fos.write(imageFile.getBody());
				} finally {
				   fos.close();
				}

				return;
			} catch (Exception t) {
				logger.severe(t.getMessage());
			}
		}
		
	}


	/**
	 * @return the ccEmail
	 */
	public String getCcEmail() {
		return ccEmail;
	}


	/**
	 * @param ccEmail the ccEmail to set
	 */
	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}
}
