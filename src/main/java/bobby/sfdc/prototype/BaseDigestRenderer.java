package bobby.sfdc.prototype;

import java.io.File;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import bobby.sfdc.prototype.json.BaseFeedElement;
import bobby.sfdc.prototype.json.ContentCapability;
import bobby.sfdc.prototype.json.Motif;
import bobby.sfdc.prototype.json.UserProfile;
import bobby.sfdc.soql.Info;

public abstract class BaseDigestRenderer implements IRenderingEngine, IInfoProvider {
	private static final char SPACE_CHAR = ' ';
	private static final char UNDERSCORE_CHAR = '_';
	private static final String CUSTOM_FIELDNAME_SUFFIX = "__c";
	private static final String CUSTOM_FOREIGNKEY_SUFFIX = "__r";
	private static final String ICON_FOR_MIMETYPE_PATTERN = "/sfc/images/docicons/doctype_$$$_48.png?v=188-1";


	protected BaseFeedElement item;
	private final String instanceUrl;
	private IInfoProvider provider;
	private SectionIndexKey key;
	protected final UserProfile profile;
	protected String footer;
	protected String salutation;
	private static final String INPUT_PATTERN="yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
	private static final String OUTPUT_PATTERN="EEEE, MMMM dd hh:mm a";

	protected static DateTimeFormatter inputFormatter = DateTimeFormat.forPattern(INPUT_PATTERN);
	protected static DateTimeFormatter outputFormatter = DateTimeFormat.forPattern(OUTPUT_PATTERN);



	public BaseDigestRenderer(String instanceUrl, UserProfile profile) {
		this.instanceUrl = instanceUrl;
		this.profile = profile;
	}

	@Override
	public void setItem(SectionIndexKey key, BaseFeedElement currentItem) {
		this.item=currentItem;
		this.key = key;
	}
	
	protected String getInstanceUrl() {
		return this.instanceUrl;
	}
	
	public String getDigestUserId() {
		return this.profile.getId();
	}
	
	public UserProfile getUserProfile() {
		return this.profile;
	}
	
	/**
	 * Take a date in 2014-11-25T20:42:26.000Z
	 * and reformat it as a friendly date
	 * Monday, November 25, 2014 8:42 PM  
	 * 
	 * @param dateAsSFDCString
	 * @return friendly date intended for digest
	 */
	protected String formatDate(String dateAsSFDCString) {
		
		DateTime intermediate = inputFormatter.parseDateTime(dateAsSFDCString);
		
		return intermediate.toString(outputFormatter);
	}


	/**
	 * Get the Label for the Current Section
	 **/
	protected String getSectionLabel() {
		return key.getSectionLabel();
	}
	
	/**
	 * Icon that will be used to represent the current section
	 * @return
	 */

	protected Motif getSectionMotif() {
		return key.getSectionMotif();
	}
	
	protected String getUrlFromId(String id) {
		return this.instanceUrl + "/"+id + "?fromEmail=1";
	}
	
	protected String getMimeIconUrl(String mimeType) {
		String icon;
		if (mimeType.contains("pdf")) {
			icon="pdf";
		} else if (mimeType.contains("word")) {
			icon="word";
			
		} else if (mimeType.contains("excel")) {
			icon="xls";
			
		} else if (mimeType.contains("power")) {
			icon="ppt";
			
		} else {
			icon="image";
		}
		return this.instanceUrl + ICON_FOR_MIMETYPE_PATTERN.replace("$$$", icon);
	}

	@Override
	public void init(IInfoProvider provider) {
		this.provider = provider;
	}

	@Override
	public Info getInfoForId(String id) {
		return provider.getInfoForId(id);
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IInfoProvider#getLabelForType(java.lang.String)
	 */
	@Override
	public String getLabelForType(String type) {
		return provider.getLabelForType(type);
	}
	
	/**
	 * Try to improve the the basic field name without actually fetching a label
	 * @param name
	 * @return
	 */
	public String cleanUpFieldName(String name) {
		if (name!=null) {
			String betterName = name;
			// Remove the custom field standard ending suffix
			if (betterName.endsWith(CUSTOM_FIELDNAME_SUFFIX)) {
				betterName = betterName.substring(0,betterName.length()-CUSTOM_FIELDNAME_SUFFIX.length());
			} else if (betterName.endsWith(CUSTOM_FOREIGNKEY_SUFFIX)) {
				betterName = betterName.substring(0,betterName.length()-CUSTOM_FOREIGNKEY_SUFFIX.length());				
			}
			
			betterName=betterName.replace(UNDERSCORE_CHAR, SPACE_CHAR);
			return betterName;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IRenderingEngine#setFooterText()
	 */
	@Override
	public void setFooterText(String footer) {
		this.footer = footer;
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IRenderingEngine#setSalutationText()
	 */
	@Override
	public void setSalutationText(String salutation) {
		this.salutation = salutation;
	}

	/**
	 * Implement a cache for Thumbnails
	 * @param content
	 * @return URI of the cached Thumbnail
	 */
	protected String getCachedFileThumbNailURI(ContentCapability content) {
		//FIXME implement a cacheKey that can't be guessed
		String cacheKey = content.getId();
		String expectedCacheFileName = getCacheDir() + File.separator + cacheKey;
		
		if (!(new File(expectedCacheFileName).exists())) {
			fetchAndStore(content,expectedCacheFileName);
		}
		return expectedCacheFileName;
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IInfoProvider#getCacheDir()
	 */
	@Override
	public String getCacheDir() {
		return provider.getCacheDir();
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IInfoProvider#fetchAndStore(bobby.sfdc.prototype.json.ContentCapability, java.lang.String)
	 */
	@Override
	public void fetchAndStore(ContentCapability content,
			String expectedCacheFileName) {
		provider.fetchAndStore(content, expectedCacheFileName);
		
	}





}