package bobby.sfdc.prototype;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.message.BasicNameValuePair;

import bobby.sfdc.prototype.json.BannerCapability;
import bobby.sfdc.prototype.json.BaseFeedElement;
import bobby.sfdc.prototype.json.BaseRecommendation;
import bobby.sfdc.prototype.json.BaseSummary;
import bobby.sfdc.prototype.json.CanvasCapability;
import bobby.sfdc.prototype.json.ChatterLikesCapability;
import bobby.sfdc.prototype.json.Comment;
import bobby.sfdc.prototype.json.CommentsCapability;
import bobby.sfdc.prototype.json.ContentCapability;
import bobby.sfdc.prototype.json.EnhancedLinkCapability;
import bobby.sfdc.prototype.json.FeedElementCapabilities;
import bobby.sfdc.prototype.json.FeedItemBody;
import bobby.sfdc.prototype.json.FeedTrackedChange;
import bobby.sfdc.prototype.json.IActor;
import bobby.sfdc.prototype.json.Icon;
import bobby.sfdc.prototype.json.LinkCapability;
import bobby.sfdc.prototype.json.MessageSegment;
import bobby.sfdc.prototype.json.Motif;
import bobby.sfdc.prototype.json.QuestionAndAnswersCapability;
import bobby.sfdc.prototype.json.RecommendationsCapability;
import bobby.sfdc.prototype.json.Topic;
import bobby.sfdc.prototype.json.TopicsCapability;
import bobby.sfdc.prototype.json.TrackedChangesCapability;
import bobby.sfdc.prototype.json.UserProfile;
import bobby.sfdc.prototype.json.UserSummary;
import bobby.sfdc.soql.Info;

/**
 * Render the Digest in HTML form suitable for Email
 * 
 * @author bobby.white
 *
 */
public class HTMLDigestRenderer extends BaseDigestRenderer implements  IRenderingEngine {
	private static final String COMMENT_SINGULAR_MSG = "Comment";
	private static final String HEIGHT_ATTR = "height";
	private static final String TITLE_ATTR = "title";
	private static final String WIDTH_ATTR = "width";
	private static final String TARGET_ATTR = "target";
	private static final String TARGET_WINDOW = "FromChatterEmail";



	private static final String ROWSPAN_ATTR = "rowspan";

	private static  Logger logger = Logger.getLogger(HTMLDigestRenderer.class.getName());

	private static final String TABLE_TAG = "table";
	private static final String ELLIPSIS = "...";
	// Hacked in Controls to Suppress/Enable some rendering and enforce HTML style rules in development mode
	private  boolean strictMode=false;
	private static boolean showParentDescription=false;
	private static boolean showFollowingInfo=false;
	private static boolean showTopics=true;
	private static boolean showFilePreviewThumbnails=false;

	private static final double MAX_CHARS_PER_LINE = 70.0;
	private static final Logger _logger = Logger.getLogger(HTMLDigestRenderer.class.getName());
	private static final String LINEBREAK_TAG = "<br/>\n";
	/**
	 * @author bobby.white
	 *
	 */

	private static Set<String> allCssClasses = new TreeSet<>();

	protected static final Stack<BaseHtmlTag> tagStack=new Stack<>();
	protected static int masterTagId=0;

	private static final String IMG_DOWNLOAD = "/img/chatterEmail/download.png";
	private static final String IMG_LIKE = "/img/chatterEmail/like16.png";
	private static final String IMG_LINK = "/img/chatterEmail/link_lg.png";

//NOSONAR	private static final String IMG_FILES = "/img/icon/files16.png";
	
	protected static final String TOPICS_SEARCH_LANDING_PAGE_URL = "/_ui/core/feeds/notification/TopicLandingPage?ref=hash_mention&name=";

	private static final char SPACE = ' ';
	private static final char EQUALSIGN = '=';

	private static final Object CLASS_SELECTOR = '.';
	private static final int MAX_POST_LINES = 5;
	private static final int MAX_COMMENT_LINES = 4;
	private static final int MAX_LIKE_LINES = 999999;
	private static final int MAX_DESCRIPTION_LINES = 5;
	private static final String ROLE_MANAGER = "GroupManager";
	private static final String ROLE_OWNER = "GroupOwner";
	private static final String ROLE_PENDING = "NotAMemberPrivateRequested";
	protected final StringBuffer html;
	protected static final char NEWLINE='\n';
	protected static final char QUOTECHAR = '\"';
	
	protected HtmlTable digestTable=null;
	
	
	private TableRowTag sectionBodyRow;
	private TableCellTag sectionBodyCell;
	private HtmlTable sectionBodyTable;
	private HtmlTable subSectionMasterTable;
	private TableRowTag subSectionOuterRow;
	private TableCellTag subSectionWrapperCell;
	private BodyTag bodyTag;

	static Map<String,String> allowedClassNameEnding = new TreeMap<>();
    static {
    	allowedClassNameEnding.put(TABLE_TAG,"Table");
    	allowedClassNameEnding.put("tr", "Row");
    	allowedClassNameEnding.put("td", "Cell");    	
    }
    
	/*Control the Digest Layout Width in one place for convenience */
	private static final int DIGEST_WIDTH = 600;
	private static final String COMMENT_AND_LIKE_COLOR="background-color:#F0F1F2;";

	private static final String L0_WIDTH_STYLE_SETTING = "table-layout:fixed;width:" + DIGEST_WIDTH + "px;";
	private static final String L1_WIDTH_STYLE_SETTING = "table-layout:auto; width:100%;";
	private static final String L2_WIDTH_STYLE_SETTING = "table-layout: auto; width:90%;";
	private static final String L3_WIDTH_STYLE_SETTING = "width:"+(DIGEST_WIDTH-250)+"px;";
	private static final int MAX_LINK_NAME=40;
	private static final String BORDER_NONE = "border:none;";

/**
 * Rendering for Email Digest in HTML format.
 * @param instanceUrl
 * @param profile user profile of owner of digest
 * @param html
 */
	public HTMLDigestRenderer(String instanceUrl, UserProfile profile, StringBuffer html, boolean strictMode) {
		super(instanceUrl,profile);
		this.html = html;
		this.strictMode = strictMode;
	}

	@Override
	public void addComment(String comment) {
		html.append(NEWLINE).append("<!--").append(comment).append("-->");
	}
	
	/**
	 * Generate inline CssStyles from the Class & Default Styles
	 */
	private void inlineCssStyles() {
		StyleTag styleTag = new StyleTag();
		styleTag.addAttribute("type", "text/css");
		styleTag.open();
		
		html.append(NEWLINE);
		/* Inline the rules that apply to Elements first */
		for (CssRule cssRule : CssRule.values()) {
			if (!cssRule.getSelector().isEmpty() && cssRule.getStyle() != null && !cssRule.getStyle().isEmpty()) {
				html.append(cssRule.getSelector()).append(" {").append(cssRule.getStyle()).append('}').append(NEWLINE);
			}
			
		}
		html.append(NEWLINE);
		
		/* Inline the CssClass definitions next  */
		
		html.append(NEWLINE);
		for (CssClass cssClass : CssClass.values()) {
			if (!cssClass.getName().isEmpty() && cssClass.getStyle() != null && !cssClass.getStyle().isEmpty()) {
				html.append(CLASS_SELECTOR).append(cssClass.getName()).append(" {").append(cssClass.getStyle()).append('}').append(NEWLINE);
			}
			
		}
		html.append(NEWLINE);
		
		styleTag.close();

	}

	/**
	 * 
	 */
	@Override
	public void renderBeginDocument() {
		bodyTag = new BodyTag();
		bodyTag.open();
		
		inlineCssStyles();
		
		renderSalutation();
	}

	/**
	 * Greet the recipient and show some Org context
	 */
	private void renderSalutation() {
		HtmlTable table = new HtmlTable(CssClass.SALUTATION_TABLE);
		TableRowTag row = new TableRowTag(CssClass.SALUTATION_ROW);
		TableCellTag cell = createPlainWrapperCell();

		table.open();
		row.open();
		cell.open();
		
		insertSpan(CssClass.SALUTATION_TEXT, this.salutation);
		
		cell.close();
		row.close();
		table.close();
	}

	@Override
	public void renderEndDocument() {
		renderFooterMessage();
		
		bodyTag.close();
		
		if (!tagStack.isEmpty()) {
			throw new IllegalStateException("End of Digest, but HTML tags remain unclosed!");
		}		
	}

	private void renderFooterMessage() {
		html.append(LINEBREAK_TAG);
		HtmlTable footerTable = new HtmlTable(CssClass.FOOTER_TABLE);
		TableRowTag footerRow = new TableRowTag(CssClass.FOOTER_ROW);
		TableCellTag footerCell = new TableCellTag(CssClass.FOOTER_CELL);
		
		footerTable.open();
		footerRow.open();
		footerCell.open();
		
		//NOSONAR this.insertSpan(CssClass.FOOTER_TEXT, this.footer);
		this.renderTextPreservingLineBreaks(this.footer, 1000);
		
		footerCell.close();
		footerRow.close();
		
		footerTable.close();
	}

	@Override
	public void renderBeginDigestFeedContent() {
		digestTable = new HtmlTable(CssClass.DIGEST_OUTER_TABLE);
		digestTable.open();
	}

	private void debugTagStack(String where) {
		_logger.info("***** Tag Stack ***** BOTTOM @" + where);
		for (BaseHtmlTag current : tagStack) {
			_logger.info(current.toString());
		}
		_logger.info("***** Tag Stack ***** TOP");
		
	}

	@Override
	public void renderEndDigestFeedContent() {
		//NOSONAR debugTagStack("renderEndDigest");
		digestTable.close();
	}

	@Override
	public void renderBeginSection() {
		
		//NOSONAR addComment("begin section");

		TableRowTag sectionOuterRow = new TableRowTag(CssClass.SECTION_HEADER_OUTER_ROW);
		sectionOuterRow.open();
		
		TableCellTag sectionHeadingCell = new TableCellTag(CssClass.SECTION_HEADER_CELL);
		
		sectionHeadingCell.open();
		
		HtmlTable sectionHeaderTable = new HtmlTable(CssClass.SECTION_HEADER_TABLE,"Section Header");
		TableRowTag sectionHeaderRow = new TableRowTag(CssClass.SECTION_HEADER_INNER);
		TableCellTag headerLabelCell = new TableCellTag(CssClass.HEADER_LABEL_CELL);

		sectionHeaderTable.open();
		sectionHeaderRow.open();
		
		if (getSectionMotif() != null) {
			TableCellTag motifCell = new TableCellTag(CssClass.HEADER_MOTIF);
			motifCell.open();
			ImgTag motif = new ImgTag(getInstanceUrl() + getSectionMotif().getLargeIconUrl(), getSectionLabel(), CssClass.HEADER_MOTIF);
			motif.inject();
			motifCell.close();
		}
		
		
		headerLabelCell.open();
		
		Span labelSpan = new Span(CssClass.HEADER_LABEL);
		labelSpan.open();
		labelSpan.content(getSectionLabel());
		labelSpan.close();
		
		headerLabelCell.close();
		sectionHeaderRow.close();
		sectionHeaderTable.close();

		sectionHeadingCell.close();
		sectionOuterRow.close();
		
		// Start a new Table for the Body of a section
		sectionBodyRow = new TableRowTag(CssClass.SECTION_BODY_OUTER_ROW);
		sectionBodyCell = createPlainWrapperCell();
		sectionBodyTable = new HtmlTable(CssClass.SECTION_BODY,"Section Body");
		sectionBodyRow.open();
		sectionBodyCell.open();
		sectionBodyTable.open();
		
	}

	@Override
	public void renderEndOfSection() {
		
		addComment("end of section");
		sectionBodyTable.close();
		sectionBodyCell.close();
		sectionBodyRow.close();

	}

	@Override
	public void renderBeginSubSection() {
		
		addComment("begin subsection");		

		subSectionOuterRow = new TableRowTag(CssClass.SUBSECTION_HEADER_OUTER_ROW);
		subSectionOuterRow.addAttribute("id", item.getParent().getName()); // for debugging!
		subSectionOuterRow.open();
		subSectionWrapperCell = createPlainWrapperCell();
		subSectionWrapperCell.open();
		
		subSectionMasterTable = new HtmlTable(CssClass.SUBSECTION_MASTER,"Subsection master table");
		subSectionMasterTable.open();
		
		if (item.getType()!=null) {
		    renderSubSectionHeader();
		}
		
	}

	/**
	 * Convenience Method when you just need a plain cell
	 * @return
	 */
	protected TableCellTag createPlainWrapperCell() {
		return new TableCellTag(CssClass.PLAIN_WRAPPER_CELL);
	}

	/**
	 * SubSection Header contains the Parent of the Feed Item.  It's an opportunity to give good context.
	 */
	private void renderSubSectionHeader() {
		
		BaseSummary parent = item.getParent();
		
		if (parent!= null && parent.isUser()) {
			_logger.fine("Suppressing header for User Wall");
			return;
		}
		
		TableRowTag subSectionHeaderOuterRow = new TableRowTag(CssClass.SUBSECTION_HEADER_OUTER_ROW);
		subSectionHeaderOuterRow.open();
		
		TableCellTag headerWrapperCell = new TableCellTag(CssClass.SUBSECTION_HEADER_WRAPPER_CELL);
		headerWrapperCell.open();
		
		HtmlTable subSectionHeaderTable = new HtmlTable(CssClass.SUBSECTION_HEADERTABLE,"SubSection Header Table");
		TableRowTag headerRow = new TableRowTag(CssClass.SECTION_HEADER_INNER);
		
		subSectionHeaderTable.open();
		headerRow.open();
		
		

		if (parent.isGroup() && parent.getPhoto()!=null) {
		   TableCellTag parentObjectPhotoCell = new TableCellTag(CssClass.SUBSECTION_PHOTO_CELL);
		   parentObjectPhotoCell.open();
		
			AnchorImgTag profilePicture = new AnchorImgTag(parent.getPhoto().getFullEmailPhotoUrl()
					,parent.getName()
					,getUrlFromId(parent.getId())
					,CssClass.SUBSECTION_PHOTO_ANCHOR
					,CssClass.SUBSECTION_PHOTO); 
			profilePicture.open().close();
			
			parentObjectPhotoCell.close();
			
		}
		
		// For the Subsection Detail - a single column
		TableCellTag subSectionDetailWrapperCell = createPlainWrapperCell();

		subSectionDetailWrapperCell.open();
		
		HtmlTable subSectionDetailTable = new HtmlTable(CssClass.SUBSECTION_DETAIL_TABLE,"subsection detail");

		subSectionDetailTable.open();
		
		
		{ //NOSONAR
			TableRowTag parentNameRow = new TableRowTag(CssClass.SUBSECTION_DETAIL_INNER_ROW);
			parentNameRow.open();

			TableCellTag nameRowWrapperCell = createPlainWrapperCell();
			nameRowWrapperCell.open();
			
			{ //NOSONAR
		
				HtmlTable nameInnerTable = new HtmlTable(CssClass.NAME_INNER_TABLE);
				
				TableRowTag innerRow = new TableRowTag(CssClass.NAME_INNER_ROW);
				nameInnerTable.open();
				innerRow.open();
				
				// Suppress the Motif for groups
				if (parent != null 
						&& !parent.isGroup()) {
					renderMotif(parent.getMotif(),CssClass.SUBSECTION_MOTIF,this.getSectionLabel(),CssClass.SUBSECTION_MOTIF);			
				}

				
				TableCellTag parentNameCell = createPlainWrapperCell();
				parentNameCell.open();
				
				// by default, for all objects use the parent name
				String subSectionName = item.getParent().getName();
				
				if (getInfoForId(item.getParent().getId()) != null) {
					// Special case for Records with Summaries, we'll just use the type name
					String typeLabel = getLabelForType(getTypeFromId(item.getParent().getId()));
					if (typeLabel == null)  {
						typeLabel = item.getParent().getType();
					}
					subSectionName = typeLabel;
				}
					
				
				
				
				IdLinkTag parentLink = new IdLinkTag(item.getParent().getId(),subSectionName,CssClass.PARENT_ENTITY_LINK);
				parentLink.open().close();
						
				parentNameCell.close();
				
				renderFollowingInfo(parent);
				
				
				innerRow.close();
				nameInnerTable.close();
			}

			nameRowWrapperCell.close();

			parentNameRow.close();
		}
		
		TableRowTag parentDescriptionRow = new TableRowTag(CssClass.SUBSECTION_DETAIL_INNER_ROW);
		parentDescriptionRow.open();
		
		if (showParentDescription && item.getParent().getDescription()!=null) {
			TableCellTag parentDescriptionCell = new TableCellTag(CssClass.SUBSECTION_HEADER_DESCRIPTION);
			parentDescriptionCell.open();

			renderTextPreservingLineBreaks(item.getParent().getDescription(),MAX_DESCRIPTION_LINES);

			parentDescriptionCell.close();
		}
		
		if (parent.getType().compareTo("CollaborationGroup")==0) {
			// do group stuff
		} else if (parent.getType().compareTo("User")==0) {
			// do user stuff
		} else {
			// Grab the extra Context info and render it for clarity
			Info info = getInfoForId(item.getParent().getId());
			
			if (info!=null) {
				TableCellTag infoWrapperCell = createPlainWrapperCell();
				infoWrapperCell.open();
				HtmlTable contentInfoTable = new HtmlTable(CssClass.RECORD_CONTEXT_INFO_TABLE);
				contentInfoTable.open();
				
				for (BasicNameValuePair nvp : info.getFields()) {
					String betterFieldName = cleanUpFieldName(nvp.getName());
					TableRowTag infoRow = new TableRowTag(CssClass.RECORD_CONTEXT_INFO_INNER_ROW);
					infoRow.open();
					TableCellTag nameCell = new TableCellTag(CssClass.RECORD_CONTEXT_NAME_CELL);
					nameCell.open();
					insertSpan(CssClass.RECORD_CONTEXT_FIELDNAME, betterFieldName);
					nameCell.close();
					TableCellTag valueCell = new TableCellTag(CssClass.RECORD_CONTEXT_VALUE_CELL);
					valueCell.open();
					insertSpan(CssClass.RECORD_CONTEXT_VALUE,nvp.getValue());
					valueCell.close();
					infoRow.close();
				}
				
				contentInfoTable.close();
				infoWrapperCell.close();
				
			}

		}

		parentDescriptionRow.close();
		
		subSectionDetailTable.close();
		subSectionDetailWrapperCell.close();

		
		headerRow.close();
		subSectionHeaderTable.close();
		headerWrapperCell.close();

				
		// Main Row for the Subsection
		subSectionHeaderOuterRow.close();
		
	}

	/**
	 * Type a standard Id and Extract the 3 character Type
	 * @param id
	 * @return 3 character TypeId
	 */
	private String getTypeFromId(String id) {
		return (id == null || id.isEmpty()) ? "" : id.substring(0,3);
	}

	/**
	 * @param parent
	 */
	protected void renderFollowingInfo(BaseSummary parent) {
		
		if (showFollowingInfo) {
			String followingText = null;
			
			if (parent.getMySubscription() != null && parent.isGroup()) {
				if (parent.getMyRole().compareTo(ROLE_OWNER)==0) {
				   followingText = "Owner";
				} else if (parent.getMyRole().compareTo(ROLE_MANAGER)==0) {
				   followingText = "Manager";	
				} else if (parent.getMyRole().compareTo(ROLE_PENDING)==0) {
				   followingText = "Pending";	
				} else {
				   followingText = "Member";
				}
			} else if (parent.getMySubscription() != null) {
				followingText = "Following";
			}
			
			if (followingText != null) {
				TableCellTag followingCell = new TableCellTag(CssClass.FOLLOWING_CELL);
				followingCell.open();
				insertSpan(CssClass.FOLLOWING_TEXT, followingText);
				followingCell.close();
			}			
		}
	}

	/**
	 * Render the supplied text converting NEWLINE characters into LINEBREAK tags
	 * @param originalText
	 * @param remainingLines 
	 */
	private int renderTextPreservingLineBreaks(String originalText, int remainingLines) {
		
		for (String thisLine : originalText.split(Character.toString(NEWLINE))) {
			if (remainingLines <= 0) { 
				return remainingLines;
			}
			
			// consider the number of characters in the line so that long wrapping lines are counted as multiple lines.
			int howLongIsThisLineWithWrapping = Math.max(((int)(((float)thisLine.length()/MAX_CHARS_PER_LINE)+.999)),1);
			
			if (howLongIsThisLineWithWrapping > remainingLines) {
				// truncate the line to limit the length of the email
				_logger.log(Level.FINE,"truncating line: " + thisLine);
				thisLine = thisLine.substring(0,(int)(remainingLines*MAX_CHARS_PER_LINE));
				_logger.log(Level.FINE,"truncated to: " + thisLine);
				html.append(thisLine); // No additional linebreak
				remainingLines = -1;

			} else {
			    html.append(thisLine).append(LINEBREAK_TAG);
			    remainingLines -= howLongIsThisLineWithWrapping;
			}

		}
		
		return remainingLines;
	}

	@Override
	public void renderEndOfSubSection() {
		
		//NOSONAR debugTagStack("renderEndOfSubSection");
		
		subSectionMasterTable.close();
		subSectionWrapperCell.close();
		subSectionOuterRow.close();

		addComment("end of subsection");
		
	}

	@Override
	public void renderDetail() {

		// Wrap the detail with a table for alignment
		addComment("begin detail");
		TableRowTag detailOuterRow = new TableRowTag(CssClass.DETAIL_OUTER_ROW);
		detailOuterRow.open();
		
		TableCellTag detailTableWrapperCell = createPlainWrapperCell();
		detailTableWrapperCell.open();
		
		HtmlTable detailTable = new HtmlTable(CssClass.FEEDITEM_DETAIL,"FeedItemDetail");
		detailTable.open();

		TableRowTag feedItemBodyRow = new TableRowTag(CssClass.FEEDITEM_INNER_DETAIL_ROW);
		feedItemBodyRow.open();
		
		String type = item.getType();
		
		if (type.compareTo("AdvancedTextPost")==0 && item.getCapabilities() !=null && item.getCapabilities().getBanner() !=null) {
			BannerCapability banner = item.getCapabilities().getBanner();
			logger.info(item.getType() + " banner:" + banner.toString() );
		}
		
		if (item.getType() != null) { // Anything BUT a Recommendation
		   // Render the Photo of the Person who created this Post
		   renderActor(item.getActor()
				   ,CssClass.PROFILE_CELL
				   ,CssClass.PROFILE_PICTURE_ANCHOR
				   ,CssClass.PROFILE_PICTURE);
		}
		
		// Render the Body in it's own cell with a Link for the Actor's Name		
		renderFeedItemBodyAndCapabilities(item);
		
		
		feedItemBodyRow.close();
		
		
		detailTable.close();
		detailTableWrapperCell.close();
		detailOuterRow.close();
		
		addComment("end of detail");

	}

	/**
	 * @param actor
	 * @param body 
	 */
	private void renderFeedItemBodyAndCapabilities(BaseFeedElement element) {
		FeedItemBody body = element.getBody();
		UserSummary actor = element.getActor();

		TableCellTag feedItemBodyWrapperCell = createPlainWrapperCell();
		feedItemBodyWrapperCell.open();
		
		HtmlTable innerDetailTable = new HtmlTable(CssClass.FEEDITEM_INNER_DETAIL_TABLE);
		TableRowTag innerDetailFeedBodyRow = new TableRowTag(CssClass.FEEDITEM_INNER_DETAIL_ROW);
		TableCellTag innerDetailFeedBodyOuterCell = createPlainWrapperCell();
		innerDetailTable.open();
		innerDetailFeedBodyRow.open();
		innerDetailFeedBodyOuterCell.open();
		
		{ //NOSONAR
			// Table for the Feed Post Body
			HtmlTable feedItemBodyTable = new HtmlTable(CssClass.FEEDITEM_BODY_TABLE,"FeedItem Body");
			feedItemBodyTable.open();
			TableRowTag innerRow1 = new TableRowTag(CssClass.FEEDITEM_BODY_INNER_ROW);
			innerRow1.open();
			
			{ //NOSONAR To avoid alignment issues with future rows in the FeedItem Body, we need to create another nested table here
				TableCellTag preambleWrapperCell = this.createPlainWrapperCell();
				preambleWrapperCell.open();
				
				HtmlTable preambleTable = new HtmlTable(CssClass.PREAMBLE_TABLE);
				preambleTable.open();
				
				TableRowTag preambleRow = new TableRowTag(CssClass.PREAMBLE_ROW);
				preambleRow.open();
				
				TableCellTag actorLinkCell = new TableCellTag(CssClass.ACTOR_CELL);
				actorLinkCell.open();
				renderActorLink(actor);
	
				// Only for Wall Posts not on the poster's own wall, show the mini-preamble here
				if (item.getParent() != null 
						&& actor != null
						&& item.getParent().isUser() 
						&& !(item.getParent().getId().compareTo(actor.getId())==0)) {
					actorLinkCell.content(" to ");
					renderActorLink(item.getParent());
				}
				
				actorLinkCell.close();
				
				preambleRow.close();
				
				preambleTable.close();
				preambleWrapperCell.close();
			}
			
			innerRow1.close();
			
			// After the ActorLink, before the text body
			renderQuestionCapability(element);

			// Deal with Announcement styling
			BannerCapability banner = item.getCapabilities().getBanner();
			boolean isAnnouncement = (banner != null && banner.getStyle().compareTo("Announcement")==0);
			
			
			TableRowTag innerRow2 = new TableRowTag(CssClass.FEEDITEM_BODY_INNER_ROW);
			innerRow2.open();
			if (isAnnouncement) {
				TableCellTag announcementWrapper = createPlainWrapperCell();
				announcementWrapper.open();
				{ //NOSONAR
					HtmlTable announcementTable = new HtmlTable(CssClass.ANNOUNCEMENT_TABLE);
					TableRowTag announcementRow = new TableRowTag(CssClass.ANNOUNCEMENT_ROW);
					announcementTable.open();
					announcementRow.open();
					
				    renderMotif(banner.getMotif(),CssClass.ANNOUNCEMENT_CELL, banner.getStyle(), CssClass.ANNOUNCEMENT_MOTIF);
				    renderBodyText(body,MAX_POST_LINES,element.getId());
				    
				    announcementRow.close();
				    announcementTable.close();
				}
				announcementWrapper.close();

			} else {
				renderBodyText(body,MAX_POST_LINES,element.getId());
			}
		    innerRow2.close();
		    feedItemBodyTable.close();
		
		}
		
		innerDetailFeedBodyOuterCell.close();

		innerDetailFeedBodyRow.close();

				
		
		if (element.getCapabilities() != null) {
			renderAllCapabilities(element.getCapabilities(),element.getId());
		}
		
		
		innerDetailTable.close();

		feedItemBodyWrapperCell.close();
	}


	/**
	 * @param element
	 */
	protected void renderQuestionCapability(BaseFeedElement element) {
		// Question Post special rendering
		if (element.getCapabilities().getQuestionAndAnswers() !=null ) {
			QuestionAndAnswersCapability questionCapability = element.getCapabilities().getQuestionAndAnswers();
			TableRowTag questionTitleRow = new TableRowTag(CssClass.FEEDITEM_BODY_INNER_ROW);
			questionTitleRow.open();
			TableCellTag questionTitleCell = createPlainWrapperCell();
			questionTitleCell.open();
			
			this.insertSpan(CssClass.QUESTION_TITLE, questionCapability.getQuestionTitle());
			questionTitleCell.close();
			
			questionTitleRow.close();
		}
	}

	/**
	 * Intended to be reused across FeedPosts, FeedComments, and potentially anything that implements the notion of segments.
	 * 
	 * If the text is limited by the maxLines, show a more action button.
	 * 
	 * @param body the FeedItemBody to be rendered
	 * @param maxLines fixes the upper bound of how many rows of text will be displayed
	 * @param contextId id of the object that "owns" this text in case we need to put a "More" button in.
	 */
	private void renderBodyText(FeedItemBody body, int maxLines, String contextId) {
		TableCellTag innerCell = new TableCellTag(CssClass.BODY_TEXT_CELL);
		innerCell.open();
		

		if (body != null && body.getMessageSegments() != null) {
			renderMessageSegments(body.getMessageSegments(), innerCell,maxLines,contextId,true);
		}
		   
		innerCell.close();
	}

	/**
	 * Rendering a Link for the Actor
	 * @param actor can be anything that implements the IUserSummary interface
	 */
	private void renderActorLink(IActor actor) {
		if (actor!=null) {
		   IdLinkTag actorLink = new IdLinkTag(actor.getId(), actor.getDisplayName(),CssClass.ENTITY_LINK);
		   actorLink.addAttribute(TITLE_ATTR, actor.getAboutMe() != null ? actor.getAboutMe() : actor.getDisplayName());
		   actorLink.open().close();
		}
	}

	/**
	 * This method expects to have an open BinaryHtmlTag to write into
	 * @param messageSegments array to render
	 * @param htmlTag
	 * @param maxLines 
	 * @param contextId parent Object / feed / comment that owns this text in case we need to put a more button in
	 * @param preserveLineBreaks 
	 */
	private void renderMessageSegments(MessageSegment[] messageSegments, BinaryHtmlTag htmlTag, int maxLines, String contextId, boolean preserveLineBreaks) {
		if (messageSegments==null) {
			return;
		}
		int remainingLines = maxLines;
		boolean truncated = false;
		
		for (MessageSegment segment : messageSegments) {
			if (remainingLines <= 0) {
				// Don't attempt display any more lines
				truncated=true;
				break;
			}

			
			if (segment.getType().compareTo("Text")==0) {
				if (preserveLineBreaks) {
				    remainingLines = renderTextPreservingLineBreaks(segment.getText(),remainingLines);
				} else {
					htmlTag.content(segment.getText());
				}

			} else if (segment.getType().compareTo("Mention")==0) {
				// Need special handling for Mentions, we want a link with the User's name
				htmlTag.content("@");
				CssClass mentionStyle = (segment.getRecord().getId().compareTo(getDigestUserId())==0) ? CssClass.MENTION_ME : CssClass.MENTION;
				if (!segment.isAccessible()) {
					mentionStyle = CssClass.BANG_MENTION;
				}
				IdLinkTag mentionTag = new IdLinkTag(segment.getRecord().getId(), segment.getName()
						,mentionStyle);
				mentionTag.open().close();
			} else if (segment.getType().compareTo("Link")==0) {
				// Need special handling for Link to force it to wrap and not exceed the width
				String linkName = segment.getText();
				if (linkName.length() > MAX_LINK_NAME) { // Shorten it!
					linkName = linkName.substring(0,MAX_LINK_NAME-3) + ELLIPSIS;
				}
				LinkTag linkTag = new LinkTag(segment.getUrl(),CssClass.URL_LINK);
				linkTag.open();

				insertSpan(CssClass.URL_LINK,linkName);

				linkTag.close();
				
				remainingLines--;
				
			} else if (segment.getType().compareTo("EntityLink")==0) {
				IdLinkTag entityTag = new IdLinkTag(segment.getReference().getId(), segment.getText(),CssClass.ENTITY_LINK);
				entityTag.open().close();
			} else if (segment.getType().compareTo("Hashtag")==0) {
				String topicSearchUrl = getInstanceUrl() + TOPICS_SEARCH_LANDING_PAGE_URL + segment.getTag();
				LinkTag hashTag = new LinkTag(topicSearchUrl,CssClass.HASHTAG);
				hashTag.open();
				insertSpan(CssClass.HASHTAG,segment.getText()); // includes the # symbol as well as the text
				hashTag.close();

				
				remainingLines--;
				
			} else if (segment.getType().compareTo("ResourceLink")==0) {
				if (preserveLineBreaks) {
				    remainingLines = renderTextPreservingLineBreaks(segment.getText(),remainingLines);
				} else {
					htmlTag.content(segment.getText());
				}
			} else {
				// taking the default of just showing the text which isn't ideal
				_logger.log(Level.WARNING,"Using default rendering for "+segment.toString());
				if (preserveLineBreaks) {
				    remainingLines = renderTextPreservingLineBreaks(segment.getText(),remainingLines);
				} else {
					htmlTag.content(segment.getText());
				}
			}
			
		}
		
		// Need to detect forced truncation by long lines which wrap
		if (remainingLines < 0) {
			truncated=true;
		}
		
		if (truncated) {
			// display the more button
			html.append(ELLIPSIS);
			IdLinkTag moreButton = new IdLinkTag(contextId, "More", CssClass.MORE_BUTTON);
			moreButton.open().close();
		}
	}

	/**
	 * Render a Profile Picture with Link
	 * Assumes that a table row is open
	 * 
	 * @param actor person or agent
	 * @param cellClass determines the alignment of the outer the cell
	 * @param anchorClass determines the border around the picture
	 * @param pictureClass determines the size of the picture itself
	 */
	private void renderActor(UserSummary actor
			, CssClass cellClass
			, CssClass anchorClass
			, CssClass pictureClass) {
		TableCellTag posterPhotoCell = new TableCellTag(cellClass);
		
		posterPhotoCell.open();
		
		try {
			if (actor!=null && actor.getPhoto() != null) {
				AnchorImgTag postedByActor = new AnchorImgTag(actor.getPhoto().getStandardEmailPhotoUrl()  // have to use an unauthenticated link
						,actor.getDisplayName()
						,getUrlFromId(actor.getId())
						,anchorClass
						,pictureClass);
				
				postedByActor.open().close();
			}
		} catch (Throwable t) {
			String msg;
			if (t.getMessage() != null) {
				msg = t.getMessage();
			} else {
				msg = t.getClass().getName();
			}
			for (StackTraceElement elem : t.getStackTrace()) {
				msg += "\n" + elem.toString();
			}
			_logger.log(Level.SEVERE,msg);
			// Note, we're intentionally continuing on here in an attempt to be resilient
		}
		posterPhotoCell.close();
	}
	
	
	/**
	 * Iterate through all of the attached capabilities and render them
	 * Impose a priority ordering:  File, Link, etc.
	 * Force Comments to be last.  Render them sparsely
	 * 
	 * @param capabilities
	 */
	private void renderAllCapabilities(FeedElementCapabilities capabilities, String contextId) {
		
		addComment("Rendering Capabilities");

		// TrackedChanges (Moved to First in case the Body is empty!
		renderTrackedChangesCapability(item.getActor(),capabilities.getTrackedChanges());
				
		//File
		renderContentCapability(capabilities.getContent());
		
		// Links
		renderLinkCapability(capabilities.getLink());
		
		// Enhanced Links (for Thanks! and possibly other things)
		renderEnhancedLinkCapability(capabilities.getEnhancedLink());
		
		// Canvas
		renderCanvasCapability(capabilities.getCanvas());
		
		// Topics  (Moved before Comments)
		renderTopicsCapability(capabilities.getTopics());
		
		if (item.getType()!=null) {
			   // Render the FeedItem "metadata" -- timestamp as the final pseudo-capability
			   renderMetaDataLine(item.getId(),formatDate(item.getCreatedDate()),true,capabilities.getComments());
			}

		//-------- Below this line are Capabilities associated with responses to the post

		//Likes
		renderLikesCapability(capabilities.getChatterLikes(),contextId);
		
		//Comments
		renderCommentsCapability(capabilities.getComments());

		//Recommendations
		renderRecommendationsCapability(capabilities.getRecommendations());
				
	}

	/**
	 * 
	 * @param enhancedLink
	 */
	private void renderEnhancedLinkCapability(
			EnhancedLinkCapability enhancedLink) {
			//Icon (on left in a column by itself
			// Title (on right in column stacked with description)
			// Description (below title)

			if (enhancedLink != null ) {
				TableRowTag enhancedLinkCapabilityRow = new TableRowTag(CssClass.ELINK_CAPABILITY_OUTER_ROW);
				TableCellTag enhancedLinkOuterCell = new TableCellTag(CssClass.ELINK_CAPABILITY_CELL);
				enhancedLinkCapabilityRow.open();
				enhancedLinkOuterCell.open();
				
				{ //NOSONAR
					HtmlTable enhancedLinkInnerTable = new HtmlTable(CssClass.ELINK_INNER_TABLE,"EnhancedLink Capability");
					TableRowTag innerRow = new TableRowTag(CssClass.ELINK_INNER_ROW);
					enhancedLinkInnerTable.open();
					innerRow.open();
					
					if (enhancedLink.getIcon()!=null) {
						Icon icon = enhancedLink.getIcon();
						String width = Math.min(45, icon.getWidth()) + "px";
						String height = Math.min(45, icon.getHeight()) + "px";
						TableCellTag enhancedLinkIconCell = new TableCellTag(CssClass.ELINK_ICON_CELL);
						enhancedLinkIconCell.addAttribute(ROWSPAN_ATTR, "2");
						enhancedLinkIconCell.addAttribute(WIDTH_ATTR, width);
						enhancedLinkIconCell.addAttribute(HEIGHT_ATTR, height);
							
						enhancedLinkIconCell.open();
						
						ImgTag enhancedLinkIcon = new ImgTag(icon.getUrl(), enhancedLink.getTitle(), CssClass.ELINK_ICON);
						enhancedLinkIcon.addAttribute(WIDTH_ATTR, width);
						enhancedLinkIcon.addAttribute(HEIGHT_ATTR, height);
	
						enhancedLinkIcon.inject();
						
						enhancedLinkIconCell.close();
					}

					TableCellTag enhancedLinkTitleCell = this.createPlainWrapperCell();
					enhancedLinkTitleCell.open();
					this.insertSpan(CssClass.ELINK_TITLE_TEXT, enhancedLink.getTitle());
					enhancedLinkTitleCell.close();
									
					innerRow.close();
					TableRowTag row2 = new TableRowTag(CssClass.ELINK_INNER_ROW);
					row2.open();
					
					TableCellTag descCell = this.createPlainWrapperCell();
					descCell.open();
					this.insertSpan(CssClass.ELINK_DESC_TEXT,enhancedLink.getDescription());
					descCell.close();
					

					row2.close();
					enhancedLinkInnerTable.close();
				
				}
				
				enhancedLinkOuterCell.close();
				enhancedLinkCapabilityRow.close();


			}
			
	}

	/**
	 * Render Canvas capability
	 * @param canvas
	 */
	private void renderCanvasCapability(CanvasCapability canvas) {
		//Icon (on left in a column by itself
		// Title (on right in column stacked with description)
		// Description (below title)

		if (canvas != null ) {
			TableRowTag canvasCapabilityRow = new TableRowTag(CssClass.CANVAS_CAPABILITY_OUTER_ROW);
			TableCellTag canvasOuterCell = new TableCellTag(CssClass.CANVAS_CAPABILITY_CELL);
			canvasCapabilityRow.open();
			canvasOuterCell.open();
			
			{ //NOSONAR
				HtmlTable canvasInnerTable = new HtmlTable(CssClass.CANVAS_INNER_TABLE,"canvas Capability");
				TableRowTag innerRow = new TableRowTag(CssClass.CANVAS_INNER_ROW);
				canvasInnerTable.open();
				innerRow.open();
				
				TableCellTag canvasIconCell = new TableCellTag(CssClass.CANVAS_ICON_CELL);
				canvasIconCell.addAttribute(ROWSPAN_ATTR, "2");
				canvasIconCell.open();
				
				ImgTag canvasIcon = new ImgTag(canvas.getThumbnailUrl(), canvas.getTitle(), CssClass.CANVAS_ICON);
				canvasIcon.inject();
				
				canvasIconCell.close();

				TableCellTag canvasTitleCell = this.createPlainWrapperCell();
				canvasTitleCell.open();
				this.insertSpan(CssClass.CANVAS_TITLE_TEXT, canvas.getTitle());
				canvasTitleCell.close();
								
				innerRow.close();
				TableRowTag row2 = new TableRowTag(CssClass.CANVAS_INNER_ROW);
				row2.open();
				
				TableCellTag descCell = this.createPlainWrapperCell();
				descCell.open();
				this.insertSpan(CssClass.CANVAS_DESC_TEXT,canvas.getDescription());
				descCell.close();
				

				row2.close();
				canvasInnerTable.close();
			
			}
			
			canvasOuterCell.close();
			canvasCapabilityRow.close();

		}
		
	}

	/**
	 * Render the Topics that have been tagged onto this Post, if there are none, skip this capability
	 **/
	private void renderTopicsCapability(TopicsCapability topics) {
		if (showTopics 
				&& topics != null 
				&& topics.getItems() != null 
				&& topics.getItems().length > 0) {
			TableRowTag topicsCapabilityRow = new TableRowTag(CssClass.TOPICS_CAPABILITY_OUTER_ROW);
			TableCellTag topicsOuterCell = new TableCellTag(CssClass.TOPICS_CAPABILITY_CELL);
			topicsCapabilityRow.open();
			topicsOuterCell.open();
			
			{ //NOSONAR
				HtmlTable topicsInnerTable = new HtmlTable(CssClass.TOPICS_INNER_TABLE,"topics Capability");
				TableRowTag innerRow = new TableRowTag(CssClass.TOPICS_INNER_ROW);
				topicsInnerTable.open();
				innerRow.open();

			    /**
				TableCellTag topicsLabelCell = new TableCellTag(CssClass.TOPICS_LABEL_CELL);
				topicsLabelCell.open();
				insertSpan(CssClass.TOPICS_LABEL, "Topics:");
				topicsLabelCell.close();
				**/
				
				TableCellTag topicsMessageCell = new TableCellTag(CssClass.TOPICS_TAGS_CELL);
				topicsMessageCell.open();
				
				//Iterate through the assigned topics here!, Each should be a link with the Topic Name as the Label
				for(Topic current : topics.getItems()) {
					IdLinkTag topicLink = new IdLinkTag(current.getId(), current.getName(), CssClass.TOPIC_LINK_TAG);
					topicLink.open().close();
				}
				
				topicsMessageCell.close();
				
				innerRow.close();
				topicsInnerTable.close();
			
			}
			
			topicsOuterCell.close();
			topicsCapabilityRow.close();
		}
		
	}

	/**
	 * Render the Metadata line for the FeedItem that indicates when it was created
	 */
	private void renderMetaDataLine(String id, String createdDate, boolean includeCommentActionLink,CommentsCapability commentsCapability) {
		TableRowTag metadataRow = new TableRowTag(CssClass.METADATALINE_OUTER_ROW);
		TableCellTag wrapper = createPlainWrapperCell();
		HtmlTable metadataInnerTable = new HtmlTable(CssClass.METADATA_CAPABILITY);
		TableRowTag innerRow = new TableRowTag(CssClass.METADATA_INNER_ROW);		
		TableCellTag metadataCell = new TableCellTag(CssClass.TIMESTAMP_CELL);
		
		
		if (!includeCommentActionLink) {
		   metadataCell.addAttribute("colspan", "2");
		}

		IdLinkTag timestampTag = new IdLinkTag(id,createdDate,CssClass.TIMESTAMP_LINK);
		
		metadataRow.open();
		wrapper.open();
		metadataInnerTable.open();
		innerRow.open();

		

		metadataCell.open();
		
		timestampTag.open().close();
		
		metadataCell.close();
		
		if (includeCommentActionLink) {
			String commentsLabel = COMMENT_SINGULAR_MSG;
			
			if (commentsCapability !=null 
					&& commentsCapability.getPage() !=null) {
				
				int total = commentsCapability.getPage().getTotal();
				int commentsShownInDigestCount = commentsCapability.getPage().getItems() != null ?
						commentsCapability.getPage().getItems().length : 0;

				if (commentsShownInDigestCount == 0) {
					if (total == 0) {
					commentsLabel = COMMENT_SINGULAR_MSG; // The verb "Comment" as a call to action"
					} else if (total == 1) {
						commentsLabel = "1 Comment"; // singular
					} else {
						commentsLabel = total + " Comments";  // plural
					}
				} else {
					// We're showing some comments in the digest
					if (total > commentsShownInDigestCount) {
						commentsLabel = "Show all " + total + " comments";
					} else {
						commentsLabel = COMMENT_SINGULAR_MSG; // reverts to a "Call to action" verb
					}
				}
			}
			
			TableCellTag commentActionCell = new TableCellTag(CssClass.COMMENT_ACTION_CELL);
			commentActionCell.open();
			
			String commentActionURL = getUrlFromId(item.getId())+"&OpenCommentForEdit=1";
			LinkTag commentActionLink = new LinkTag(commentActionURL,CssClass.COMMENT_ACTION_LINK);
			
			commentActionLink.open();
			commentActionLink.content(commentsLabel);
			commentActionLink.close();
			
			commentActionCell.close();
		}
		innerRow.close();
		metadataInnerTable.close();
		wrapper.close();
		metadataRow.close();
	}

	/**
	 * @param changes
	 */
	private void renderTrackedChangesCapability(UserSummary actor, TrackedChangesCapability changes) {
		if (changes !=null) {
			TableRowTag outerRow = new TableRowTag(CssClass.FTC_OUTER);
			TableCellTag wrapperCell = createPlainWrapperCell();
			outerRow.open();
			

			
			wrapperCell.open();
			HtmlTable outerTable = new HtmlTable(CssClass.FTC_CAPABILITY_TABLE,"FTCTableOuter");
			outerTable.open();
			
			{  //NOSONAR
				TableRowTag detailRow1 = new TableRowTag(CssClass.FTC_DETAIL_ROW);
				detailRow1.open();
				
				TableCellTag actorCell = createPlainWrapperCell();
				actorCell.open();
				renderActorLink(actor);
				insertSpan(CssClass.FTC_NORMAL_TEXT,"changed");
				actorCell.close();
				
				detailRow1.close();
			}
			
			TableRowTag detailRow2 = new TableRowTag(CssClass.FTC_DETAIL_ROW);
			detailRow2.open();

			
			TableCellTag innerWrapperCell = createPlainWrapperCell();
			innerWrapperCell.open();
			
			HtmlTable innerTable = new HtmlTable(CssClass.FTC_INNER_TABLE,"FTCTableInner");
			innerTable.open();
			

			boolean firstRow=true;
			int spanCount = changes.getChanges().length;
			for (FeedTrackedChange change : changes.getChanges()) {
				TableRowTag ftcRow = new TableRowTag(CssClass.FTC_INNER_ROW);
				ftcRow.open();
				
				{  //NOSONAR
					TableCellTag nameCell = new TableCellTag(CssClass.FTC_FIELDNAME_CELL);
					nameCell.open();
					insertSpan(CssClass.FTC_FIELDNAME,change.getFieldName());
					nameCell.close();
				}
				
				if (firstRow) {
					TableCellTag fromLabel = new TableCellTag(CssClass.FTC_LABEL_CELL);
					fromLabel.addAttribute(ROWSPAN_ATTR, Integer.toString(spanCount));
					fromLabel.open();
					insertSpan(CssClass.FTC_FIELDNAME,"from");
					fromLabel.close();
				}


				{ //NOSONAR
					TableCellTag orgValueCell = new TableCellTag(CssClass.FTC_ORIGINAL_VALUE_CELL);
					orgValueCell.open();
					insertSpan(CssClass.FTC_TEXT,change.getOldValue());
					orgValueCell.close();
				}
				
				if (firstRow) {
					TableCellTag toCell = new TableCellTag(CssClass.FTC_LABEL_CELL);
					toCell.addAttribute(ROWSPAN_ATTR, Integer.toString(spanCount));
					toCell.open();
					insertSpan(CssClass.FTC_FIELDNAME,"to");
					toCell.close();
				}

				
				{  //NOSONAR
					TableCellTag newValueCell = new TableCellTag(CssClass.FTC_NEW_VALUE_CELL);
					newValueCell.open();
					insertSpan(CssClass.FTC_TEXT,change.getNewValue());
					newValueCell.close();

				}
				
				
				ftcRow.close();
				firstRow=false;
			}
			innerTable.close();
			innerWrapperCell.close();
			detailRow2.close();
			outerTable.close();
			
			wrapperCell.close();
			outerRow.close();
			
		}
	}

	/**
	 * Convenience method to insert a span tag with the designated content and apply a style
	 * 
	 * @param style
	 * @param content
	 */
	private void insertSpan(CssClass cssClass, String content) {
		Span span = new Span(cssClass);
		span.open();
		span.content(content);
		span.close();
	}

	/**
	 * Render the Recommendations Capability in a table that can be embedded.  If there are none, this implementation will render nothing
	 * 
	 * @param recs Recommendations Capability to be rendered, could contain 0..n recommendations
	 */
	private void renderRecommendationsCapability(
			RecommendationsCapability recs) {
		if (recs!=null && recs.getItems()!=null && recs.getItems().length > 0) {
			
			TableRowTag outerRow = new TableRowTag(CssClass.RECOMMENDATION_CAPABILITY_OUTER_ROW);
			TableCellTag outerCell = new TableCellTag(CssClass.RECOMMENDATION_CAPABILITY_CELL);
			HtmlTable recsTable = new HtmlTable(CssClass.RECOMMENDATION_INNER_TABLE,"Recommendations Table");
			outerRow.open();
			outerCell.open();
			recsTable.open();

			for (BaseRecommendation rec : recs.getItems()) {
				
				TableRowTag innerRow = new TableRowTag(CssClass.REC_INNER_ROW);
				TableCellTag innerCell = createPlainWrapperCell();
				innerRow.open();
				innerCell.open();
				
				if (rec.getMotif() != null) {
				   try {
					ImgTag motifImg = new ImgTag(rec.getMotif(),"Recommendation",CssClass.RECOMMENDATION_MOTIF);
					   motifImg.inject();
					} catch (Throwable t) {
						_logger.warning("problems rendering Recommendation motif" +t.getMessage());
					}
				}
				
				
				innerCell.content(rec.getAction());
				Span displayLabel = new Span(CssClass.RECOMMENDATION_LABEL);
				displayLabel.open();
				displayLabel.content(rec.getDisplayLabel());
				displayLabel.close();
				innerCell.content(rec.getActOnUrl());
				innerCell.content(rec.getExplanation().getSummary());
				
				if (rec.getEntity() != null ) {
					renderMiniDetail(rec.getEntity());
				}

				
				innerCell.close();
				innerRow.close();
				
			}
			
			recsTable.close();
			outerCell.close();
			outerRow.close();

		}
	}

	/**
	 * Render a brief summary of an Entity.
	 * Motif + Name(link) + Extra
	 * @param entity
	 */
	private void renderMiniDetail(BaseSummary entity) {
		HtmlTable miniDetailTable = new HtmlTable(CssClass.MINI_DETAIL_TABLE);
		TableRowTag innerRow = new TableRowTag(CssClass.MINI_DETAIL_ROW);
		TableCellTag nameCell = new TableCellTag(CssClass.MINI_DETAIL_CELL);
		miniDetailTable.open();
		innerRow.open();
		
		renderMotif(entity.getMotif(),CssClass.LIKE_MOTIF_CELL,entity.getName(), CssClass.LIKE_MOTIF);
		
		nameCell.open();
		IdLinkTag entityLink = new IdLinkTag(entity.getId(), entity.getName(),CssClass.ENTITY_LINK);
		entityLink.open().close();
		nameCell.close();

		
		innerRow.close();
		miniDetailTable.close();
	}
	/**
	 * Render a Motif including the Cell
	 * 
	 * @param motif
	 * @param motifCellClass
	 * @param label
	 * @param imageClass
	 */
	private void renderMotif(Motif motif, CssClass motifCellClass, String label, CssClass imageClass) {
		TableCellTag motifCell = new TableCellTag(motifCellClass);
		motifCell.open();
		
		String imgUrl = motif.getSmallIconUrl();
		if (!imgUrl.startsWith("http")) {
			imgUrl = getInstanceUrl()+imgUrl; // pre-append if necessary
		}
		ImgTag motifImg = new ImgTag(imgUrl,label,imageClass);
		motifImg.inject();
		
		motifCell.close();
	
	}


	/**
	 * Render the Comments Capability in an embeddable table.  Do it sparsely so if there are none, nothing is created.
	 * 
	 * @param commentsCap  could contain 0..n comments
	 */
	private void renderCommentsCapability(CommentsCapability commentsCap) {
		if (commentsCap != null
				&& commentsCap.getPage()!=null
				&& commentsCap.getPage().getItems() !=null
				&& commentsCap.getPage().getTotal() != 0) {
			
			TableRowTag outerRow = new TableRowTag(CssClass.COMMENT_CAPABILITY_ROW);
			TableCellTag outerCell = new TableCellTag(CssClass.COMMENT_CAPABILITY_CELL);
			HtmlTable commentsTable = new HtmlTable(CssClass.COMMENT_CAPABILITY_TABLE,"Comments Table");
			outerRow.open();
			outerCell.open();
			commentsTable.open();
			
			
			/** Disabling this for now to try the alternative rendering out
			int total = commentsCap.getPage().getTotal();

			if (total != commentsCap.getPage().getItems().length) {
				// This means there are more comments than were returned
				TableRowTag commentCountRow = new TableRowTag(CssClass.COMMENT_OUTER_ROW);
				commentCountRow.open();
				
				TableCellTag commentCountCell = new TableCellTag(CssClass.COMMENTS_COUNT_CELL);
				commentCountCell.addAttribute("colspan", ""+2);
				commentCountCell.open();
				
				IdLinkTag showAllCommentsLink = new IdLinkTag(item.getId(),"Show all " + total + " comments", CssClass.COMMENT_ACTION_LINK);
				
				showAllCommentsLink.open();
				showAllCommentsLink.close();
				
				commentCountCell.close();
				commentCountRow.close();
			} **/
			
			/** 
			 * Each Comment gets its own row with the "author" in the first column and the text in the second
			 **/
			for (Comment comment : commentsCap.getPage().getItems()) {
				TableRowTag commentOuterRow = new TableRowTag(CssClass.COMMENT_OUTER_ROW);
				commentOuterRow.open();
				
				renderActor(comment.getUser()
						,CssClass.COMMENT_PROFILE_PICTURE_CELL
						,CssClass.COMMENT_PROFILE_PICTURE_ANCHOR
						,CssClass.COMMENT_PROFILE_PICTURE);
				
				{ //NOSONAR Force these items into an inner table for single column alignment
				   TableCellTag innerTableWrapper = createPlainWrapperCell();
				   innerTableWrapper.open();
				   
				   HtmlTable commentsInnerTable = new HtmlTable(CssClass.COMMENT_INNER_TABLE,"Comment Inner");
				   commentsInnerTable.open();
				   
				   
  			       TableRowTag commentRow1 = new TableRowTag(CssClass.COMMENT_INNER_ROW);
				   commentRow1.open();
				   TableCellTag actorLinkCell =createPlainWrapperCell();
				   actorLinkCell.open();
				   renderActorLink(comment.getUser());
				   actorLinkCell.close();
				   commentRow1.close();

  			       TableRowTag commentRow2 = new TableRowTag(CssClass.COMMENT_INNER_ROW);
				   commentRow2.open();
				   renderBodyText(comment.getBody(),MAX_COMMENT_LINES,comment.getId());
				   commentRow2.close();
				   
				   renderMetaDataLine(comment.getId(),formatDate(comment.getCreatedDate()),false,null);
				   
				   commentsInnerTable.close();
				   innerTableWrapper.close();
				}
				

				commentOuterRow.close();
				
				
			}
			
			commentsTable.close();
			outerCell.close();
			outerRow.close();
		}
	}

	/**
	 * Render Likes on FeedItem or FeedComment
	 * @param capabilities
	 */
	private void renderLikesCapability(ChatterLikesCapability likes, String contextId) {

		if (likes!=null && likes.getLikesMessage()!=null) {
			//NOSONAR int likeCount = (likes.getLike() != null && likes.getLike().getItems() != null) ? likes.getLike().getItems().length : 0;
			
			TableRowTag likeCapabilityRow = new TableRowTag(CssClass.LIKE_CAPABILITY_OUTER_ROW);
			TableCellTag likeOuterCell = new TableCellTag(CssClass.LIKE_CAPABILITY_CELL);
			likeCapabilityRow.open();
			likeOuterCell.open();
			
			{  //NOSONAR
				HtmlTable likeInnerTable = new HtmlTable(CssClass.LIKE_INNER_TABLE,"Likes Capability");
				TableRowTag innerRow = new TableRowTag(CssClass.LIKE_INNER_ROW);
				likeInnerTable.open();
				innerRow.open();

			
				TableCellTag likeMotifCell = new TableCellTag(CssClass.LIKE_MOTIF_CELL);
				ImgTag likeImg = new ImgTag(getInstanceUrl()+IMG_LIKE, "Like this",CssClass.LIKE_MOTIF);
				likeMotifCell.open();
				likeImg.inject();
				likeMotifCell.close();
				
				TableCellTag likeMessageCell = createPlainWrapperCell();
				likeMessageCell.open();
				
				renderMessageSegments(likes.getLikesMessage().getMessageSegments(), likeMessageCell,MAX_LIKE_LINES,contextId,false);
				likeMessageCell.close();
				
				innerRow.close();
				likeInnerTable.close();
			
			}
			
			likeOuterCell.close();
			likeCapabilityRow.close();
			
		}
	}

	/**
	 * @param capabilities
	 */
	private void renderLinkCapability(LinkCapability link) {
		//Link
		if (link !=null && link.getUrl() !=null) {
			TableRowTag outerRow = new TableRowTag(CssClass.LINK_CAPABILITY_OUTER_ROW);
			TableCellTag wrapperCell = createPlainWrapperCell();
			
			outerRow.open();
			wrapperCell.open();
			
			{ //NOSONAR
				HtmlTable innerTable = new HtmlTable(CssClass.LINK_CAPABILITY_TABLE,"Link Capability");
				innerTable.open();
				
				{ //NOSONAR First Row
					TableRowTag row1 = new TableRowTag(CssClass.LINK_INNER_ROW);
					TableCellTag innerCell = createPlainWrapperCell();
					row1.open();
					innerCell.open();
					ImgTag linkIcon = new ImgTag(getInstanceUrl()+IMG_LINK, "Link Icon", CssClass.LINK_ICON);
					linkIcon.inject();
		
					LinkTag  linkTag1 = new LinkTag(link.getUrl(),CssClass.URL_LINK); 
					linkTag1.open();
					linkTag1.content(link.getUrlName());
					linkTag1.close();
					innerCell.close();
					row1.close();
				}
				{  //NOSONAR Second Row
					TableRowTag row2 = new TableRowTag(CssClass.LINK_INNER_ROW);
					TableCellTag innerCell = createPlainWrapperCell();
					row2.open();
					innerCell.open();

					LinkTag  linkTag2 = new LinkTag(link.getUrl(),CssClass.URL_LINK); 
					linkTag2.open();
					// Truncate if it exceeds max width
					String linkTitle = link.getUrl();
					if (linkTitle.length() > MAX_CHARS_PER_LINE) {
						linkTitle = linkTitle.substring(0,(int)(MAX_CHARS_PER_LINE - ELLIPSIS.length())) + ELLIPSIS;
					}
					linkTag2.content(linkTitle);
					linkTag2.close();
					
					innerCell.close();
					row2.close();

				}
				innerTable.close();
			}

			
			wrapperCell.close();
			outerRow.close();
		}
	}

	/**
	 * @param contentCapability
	 */
	private void renderContentCapability(ContentCapability content) {
		if (content != null && content.getTitle()!=null) {
			TableRowTag contentCapabilityRow = new TableRowTag(CssClass.CONTENT_CAPABILITY_OUTER_ROW);
			contentCapabilityRow.open();
			TableCellTag contentCapabilityOuterCell = createPlainWrapperCell();
			contentCapabilityOuterCell.open();
			
			{ //NOSONAR
				
				HtmlTable contentTable = new HtmlTable(CssClass.CONTENT_CAPABILITY,"Content Capability");
				contentTable.open();
				TableRowTag innerRow1 = new TableRowTag(CssClass.CONTENT_INNER_ROW);
				innerRow1.open();
				
				// Default to MimeType
				String fileImageUrl = getMimeIconUrl(content.getMimeType());
				String altLabel = "Mimetype is " + content.getMimeType();
				CssClass imageClass = CssClass.CONTENT_ICON;
				CssClass imageContainerClass = CssClass.CONTENT_ICON_CELL;
				
				if (showFilePreviewThumbnails && content.getThumb120By90RenditionStatus().compareTo("Success")==0) {
					// Override with Thumbnail if available
					fileImageUrl = getCachedFileThumbNailURI(content);
					altLabel = "Thumbnail preview for " + content.getTitle();
					imageClass = CssClass.CONTENT_THUMBNAIL;
					imageContainerClass = CssClass.CONTENT_THUMBNAIL_CELL;
				}
				
				ImgTag fileTypeImg = new ImgTag(fileImageUrl, altLabel, imageClass);
				TableCellTag mimeIconCell = new TableCellTag(imageContainerClass);
				mimeIconCell.addAttribute(ROWSPAN_ATTR, "3");  // For alignment, this needs to span all of the rows!
				mimeIconCell.open();				
				fileTypeImg.inject();
				mimeIconCell.close();
				
				TableCellTag detailCell = new TableCellTag(CssClass.CONTENT_TITLE_CELL);
				IdLinkTag fileDetailLink = new IdLinkTag(content.getId(),content.getTitle(),CssClass.ENTITY_LINK);
				detailCell.open();
				fileDetailLink.open().close();
				detailCell.close();
				
				
				innerRow1.close();
				
				TableRowTag innerRow2 = new TableRowTag(CssClass.CONTENT_INNER_ROW);
				TableCellTag downloadCell = createPlainWrapperCell();
				ImgTag downloadImg = new ImgTag(getInstanceUrl()+IMG_DOWNLOAD, "Download", CssClass.DOWNLOAD_ICON);
	
				String downloadUrl = getInstanceUrl()+ "/" + content.getId();
				LinkTag downloadLink = new LinkTag(downloadUrl,CssClass.URL_LINK);

	
				innerRow2.open();
				downloadCell.open();
				downloadImg.inject();
				downloadLink.open().content("Download ("+content.getMimeType()+")");
				downloadLink.close();
				downloadCell.close();

				innerRow2.close();
				
				TableRowTag innerRow3 = new TableRowTag(CssClass.CONTENT_INNER_ROW);
				TableCellTag descriptionCell = createPlainWrapperCell();

				
				innerRow3.open();
				descriptionCell.open().content(content.getDescription());
				descriptionCell.close();
				innerRow3.close();
				contentTable.close();
			}
			
			contentCapabilityOuterCell.close();
			contentCapabilityRow.close();
		}
	}


	/**
	 * Basic HTML tag that can have attributes
	 * @author bobby.white
	 *
	 */
	public abstract class BaseHtmlTag {

		private static final String PADDING_ATTR = "padding";
		private static final String BORDER_ATTR = "border";
		private static final String HTML_VALIGN_ATTR = "valign";
		private static final String CSS_VERTICAL_ALIGN = "vertical-align";
		private static final String HTML_ALIGN_ATTR = "align";
		private static final String CSS_TEXT_ALIGN = "text-align";
		public static final String WIDTH_ATTR = "width";
		public static final String HEIGHT_ATTR = "height";
		public static final String STYLE = "style";
		protected final String tagName;
		protected final int tagId;
		protected Map<String,String> attrs = new HashMap<>();
		private boolean isFrozen;
		private final CssClass cssClass;

		public BaseHtmlTag(String tagName, CssClass cssClass) {
			this.tagName = tagName;
			this.isFrozen=false;
			tagId=masterTagId++;
			attrs.put("id", Integer.toString(tagId));
			this.cssClass = cssClass;
			setStyleFromClass();
			setAttributesFromStyle();
		}
		
		/**
		 * Used to enforce that required HTML attributes are present by the time the tag is
		 * opened
		 * @return
		 */
		protected abstract Set<String> requiredAttrs();
		
		/**
		 * List of tags that can contain this tag.  If empty, any tag can contain this one.
		 * @return
		 */
		protected String[] validParents() {
			return new String[0];
		}
		
		public final String validParentListAsString() {
			StringBuilder builder = new StringBuilder();
			boolean firstOne = true;
			for (String current : validParents()) {
				if (!firstOne) {
					builder.append(',');
				}
				firstOne=false;
				builder.append(current);
			}
			return builder.toString();
		}
				
		/**
		 * Explicitly set the style attribute from the CssClass if one was set.
		 * 
		 * This will overwrite the style attribute if one had previously been set.
		 */
		private void setStyleFromClass() {
			if (this.cssClass != null 
					&& this.cssClass.getStyle() != null
					&& !this.cssClass.getStyle().isEmpty())  {
				
				// Enforce naming convention of CssClass for Tables, Rows, Cells
				enforceClassNamingConvention();
				
				this.addAttribute(STYLE, this.cssClass.getStyle());
				
			}
			
		}
		

		
		/**
		 * Ensure that CssClasses are properly defined and applied to HTML elements
		 */
		private void enforceClassNamingConvention() {
			String ending = allowedClassNameEnding.get(tagName);
			if (ending!=null && this.cssClass !=null && !this.cssClass.getName().endsWith(ending)) {
				String msg = "Element:" + tagName + " requires a CssClass named ending with " + ending + " found: " + cssClass.getName();
				if (strictMode) {
				   throw new IllegalArgumentException(msg);
				} else {
					_logger.warning(msg);
				}
			}
		}

		/**
		 * Examine the CSS Styles and "flatten" them out to set equivalent HTML attributes when possible 
		 * -- width -> width
		 * -- height -> height
		 * -- padding --> padding
		 * -- text-align --> align
		 * -- vertical-align --> valign
		 * 
		 * If style is missing, do nothing
		 **/
		private void setAttributesFromStyle() {
			if (hasAttribute(STYLE) && getAttribute(STYLE) != null) {
				String style = getAttribute(STYLE);
				Map<String, String> styleMap = parseStyles(style);
				if (styleMap.containsKey(WIDTH_ATTR)) {
					this.addAttribute(WIDTH_ATTR, styleMap.get(WIDTH_ATTR));
				}
				if (styleMap.containsKey(HEIGHT_ATTR)) {
					this.addAttribute(HEIGHT_ATTR, styleMap.get(HEIGHT_ATTR));
				}
				if (styleMap.containsKey(CSS_TEXT_ALIGN)) {
					this.addAttribute(HTML_ALIGN_ATTR, styleMap.get(CSS_TEXT_ALIGN));
				}
				if (styleMap.containsKey(CSS_VERTICAL_ALIGN)) {
					this.addAttribute(HTML_VALIGN_ATTR, styleMap.get(CSS_VERTICAL_ALIGN));
				}
				if (styleMap.containsKey(PADDING_ATTR)) {
					this.addAttribute(PADDING_ATTR, styleMap.get(PADDING_ATTR));
				}
				if (styleMap.containsKey(BORDER_ATTR)) {
					this.addAttribute(BORDER_ATTR, styleMap.get(BORDER_ATTR));
				}




			}
			
		}

		/**
		 * Takes a CSS / html style string and converts it to a Map
		 * @param style
		 * @return a Map keyed by style attribute name
		 */
		protected Map<String,String> parseStyles(String style) {
			Map<String,String> stylesMap = new TreeMap<>();
			for (String current : style.trim().split(";")) {
				/** 
				 * should start with a string that looks like:  "border: solid 1px black"
				 * after splitting you come up with:
				 * 
				 * p1:  "border"
				 * p2:  "solid 1px black"
				 */
				String[] parts = current.trim().split(":");
				if (parts.length > 0 && parts[0].trim().length() > 0) {
					String name = parts[0].trim();
					if (parts.length >=2) {
						String value = parts[1].trim();
						stylesMap.put(name, value);
					}
				}
			}
			
			return stylesMap;
		}

		/**
		 * Get the value of the attribute
		 * @param name
		 * @return returns null if not set
		 */
		public String getAttribute(String name) {
			return this.attrs.get(name);
		}

		/**
		 * Does this tag have the named attribute?
		 * @param name
		 * @return
		 */
		public boolean hasAttribute(String name) {
			if (name == null) { 
				throw new IllegalArgumentException("name is a required parameter"); 
				}
			return attrs.containsKey(name);
		}

		public void addAttribute(String name, String value) {
			if (isFrozen) {
				throw new IllegalStateException("attempted to add attributes to a tag that has already been frozen!");
			}
			attrs.put(name, value);
		}
				
		
		/**
		 * Safely inject textContent into the body of a tag.
		 * Will be HTML encoded to ensure it doesn't allow injection of harmful characters
		 * 
		 * @param textContent text to be injected.
		 */
		public void content(String textContent ) {
			
			isFrozen=true; // Prevent attributes from being added and orphaned

			if (textContent != null) {
			   html.append(textContent);
			}
			
			return;
		}
		
		/**
		 * 
		 */
		protected final void writeStartOfOpeningTagWithoutClosing() {
			checkForRequiredAttributes();
			html.append("<").append(tagName);
			if (cssClass != null && cssClass != CssClass.EMPTY) {
				html.append(SPACE).append("class").append(EQUALSIGN).append(QUOTECHAR).append(cssClass.getName()).append(QUOTECHAR);
			}
			
			for(Entry<String,String> thisAttr : attrs.entrySet()) {
				html.append(SPACE).append(thisAttr.getKey()).append(EQUALSIGN)
				.append(QUOTECHAR).append(thisAttr.getValue()).append(QUOTECHAR);
			}
		}


		/**
		 * Ensure that all required Attributes are present
		 * Throw a runtime exception if not
		 * @throws IllegalStateException if required attributes are missing
		 */
		private void checkForRequiredAttributes() {
			if (requiredAttrs() == null && strictMode) {
				throw new IllegalStateException("No Required Attributes have defined for Tag: " + this.tagName);
			}
			
			String missing="";
			for (String current : requiredAttrs()) {
				if (!hasAttribute(current)) {
					if (!missing.isEmpty()) {
						missing +=",";
						}
					missing += current;
				}
			}
			if (!missing.isEmpty()) {
				String msg = "Required Attributes: " + missing + " from Tag: " + this.tagName + " class=" + this.cssClass.name;
				if (strictMode) {
				   throw new IllegalStateException(msg);
				} else {
					_logger.warning(msg);
				}
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			final int maxLen = 50;
			StringBuilder builder = new StringBuilder();
			builder.append("BaseHtmlTag [");
			if (tagName != null) {
				builder.append("tagName=");
				builder.append(tagName);
				builder.append(", ");
			}
			builder.append("tagId=");
			builder.append(tagId);
			builder.append(", ");
			if (attrs != null) {
				builder.append("attrs=");
				builder.append(toString(attrs.entrySet(), maxLen));
				builder.append(", ");
			}
			builder.append("isFrozen=");
			builder.append(isFrozen);
			builder.append(", ");
			if (cssClass != null) {
				builder.append("cssClass=");
				builder.append(cssClass);
			}
			builder.append("]");
			return builder.toString();
		}

		private String toString(Collection<?> collection, int maxLen) {
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			int i = 0;
			for (Iterator<?> iterator = collection.iterator(); iterator
					.hasNext() && i < maxLen; i++) {
				if (i > 0)
					builder.append(", ");
				builder.append(iterator.next());
			}
			builder.append("]");
			return builder.toString();
		}

		public String getTagName() {
			return this.tagName;
		}
	}
	
	/**
	 * For CssStyle inlining  or inclusion
	 * @author bobby.white
	 *
	 */
	public class StyleTag extends BinaryHtmlTag {

		public StyleTag() {
			super("style", CssClass.EMPTY);
		}

		@Override
		protected Set<String> requiredAttrs() {
			Set<String> required = new TreeSet<>();
			required.add("type");
			return required;
		}

		@Override
		public String[] validParents() {
			String[] parents = {"","body"};
			return parents;
		}
		
	}
	
	public abstract class BinaryHtmlTag extends BaseHtmlTag {
		private boolean hasBeenOpened=false; 
		private boolean hasBeenClosed=false;

		public BinaryHtmlTag(String tagName, CssClass cssClass) {
			super(tagName,cssClass);
			hasBeenOpened=false; 
			hasBeenClosed=false;

		}

		/**
		 * emit the opening tag
		 */
		public BinaryHtmlTag open() {
			if (hasBeenOpened) {
				throw new IllegalStateException("attempted to open a tag that has already been opened!");
			}
			
			enforceContainmentRules();

			writeStartOfOpeningTagWithoutClosing();
			html.append(">"); // Manually add the closing

			
			this.hasBeenOpened=true;
			this.hasBeenClosed=false;
			
			tagStack.push(this);  // housekeeping to avoid unmatched tags
			
			return this;

		}
		
		/**
		 * A safeguard to ensure that this tag can be contained by it's parent
		 */
		private void enforceContainmentRules() {
			String parentTag = (!tagStack.isEmpty()) ? tagStack.peek().getTagName() : "";
			
			try {
					if (validParents().length > 0) {
						boolean found = false;
						for(String current : validParents()) {
							if (parentTag.equals(current)) {
								found = true;
							}
						}
						if (!found)
						   throw new IllegalStateException(getTagName() + " tag must be contained by " + " found parent="+parentTag);
	
					}
				} catch (IllegalStateException e) {
				debugTagStack(e.getStackTrace()[3].toString());
				if (strictMode) {
				   throw e;
				} else {
					_logger.warning(e.getMessage());
				}
			}
			
		}

		/**
		 * Emit the closing tag
		 */
		public void close() {
			if (!hasBeenOpened) {
				throw new IllegalStateException("attempted to close a tag that has not been opened! name=" + this.tagName);
			}
			
			if (hasBeenClosed) {
				throw new IllegalStateException("attempted to close a tag that has already been closed! name=" + this.tagName);
			}
			
			BaseHtmlTag topOfStack = tagStack.pop();
			
			if (!this.equals(topOfStack)) {
				throw new IllegalStateException("Unbalanced close tag! Top=>"+topOfStack.toString() + " this=>"+this.toString());
			}

			//NOSONAR html.append("<!-- closing id=").append(attrs.get("id")).append("-->");
			html.append("</").append(tagName).append(">");
			this.hasBeenClosed=true;
			this.hasBeenOpened=true; // prevent re-use of tag instances!

		}

		
	}
	/**
	 * 
	 * @author bobby.white
	 *
	 */
	public class HtmlTable extends BinaryHtmlTag {
		String label;
		public HtmlTable(CssClass cssClass, String label) {
			super(TABLE_TAG, cssClass);
			this.label=label;
		}

		public HtmlTable(CssClass cssClass) {
			super(TABLE_TAG, cssClass);
		}

		@Override
		public BinaryHtmlTag open() {
			//NOSONAR ("begin of table("+label+")");
			html.append(NEWLINE);
			return super.open();
		}

		@Override
		public void close() {
			html.append(NEWLINE);
			super.close();
			//NOSONAR addComment("end of table("+label+")");
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("HtmlTable [");
			if (label != null) {
				builder.append("label=");
				builder.append(label);
			}
			builder.append(" ").append(super.toString());
			builder.append("]");
			return builder.toString();
		}

		@Override
		protected Set<String> requiredAttrs() {
			Set<String> required = new TreeSet<>();
			required.add("border");
			return required;
		}

		@Override
		public String[] validParents() {
			String[] parents = {"td","body"};
			return parents;
		}
		
	}
	
	public class TableRowTag extends BinaryHtmlTag {
		
		public TableRowTag(CssClass cssClass) {
			super("tr", cssClass);
		}

		@Override
		public BinaryHtmlTag open() {
			html.append(NEWLINE);
			return super.open();
		}

		@Override
		public void close() {
			super.close();
			html.append(NEWLINE);
		}
		
		@Override
		protected Set<String> requiredAttrs() {
			Set<String> required = new TreeSet<>();
			required.add(WIDTH_ATTR);
			return required;
		}

		@Override
		public String[] validParents() {
			String[] parents = {TABLE_TAG};
			return parents;
		}

		
	}
	
	public class TableCellTag extends BinaryHtmlTag {

		public TableCellTag(CssClass cssClass) {
			super("td",cssClass);
		}
		
		@Override
		protected Set<String> requiredAttrs() {
			Set<String> required = new TreeSet<>();
			required.add(WIDTH_ATTR);
			return required;
		}

		@Override
		public String[] validParents() {
			String[] parents = {"tr"} ;
			return parents;
		}

		
	}

	
	public class LinkTag extends BinaryHtmlTag {
		private static final String HREF_ATTR = "href";

		public LinkTag(String url, CssClass cssClass) {
			super("a", cssClass); // Html Anchor tag
			this.addAttribute(HREF_ATTR, url);
			this.addAttribute(TARGET_ATTR,TARGET_WINDOW); // Can we get this link to reuse a window?

		}
		
		@Override
		protected Set<String> requiredAttrs() {
			Set<String> required = new TreeSet<>();
			required.add(HREF_ATTR);
			return required;
		}
	}
	
	public class IdLinkTag extends LinkTag {

		private final String label;

		public IdLinkTag(String id,String label, CssClass cssClass) {
			super(getUrlFromId(id), cssClass);
			this.label=label;
		}

		@Override
		public BinaryHtmlTag open() {
			super.open();
			super.content(label);
			return this;
		}
		
		@Override
		protected Set<String> requiredAttrs() {
			return new TreeSet<>();
			}

	}
	
	public class Span extends BinaryHtmlTag {

		public Span(CssClass cssClass) {
			super("span",cssClass);
		}
		
		@Override
		protected Set<String> requiredAttrs() {
			return new TreeSet<>();
		}

		@Override
		public String[] validParents() {
			String[] parents = {"td","a","body"};
			return parents;
		}


	}
	
	/**
	 * Body Tag
	 * @author bobby.white
	 *
	 */
	public class BodyTag extends BinaryHtmlTag {
		public BodyTag() {
		   super("body",CssClass.EMPTY);
		}
		@Override
		protected Set<String> requiredAttrs() {
			return new TreeSet<>();
		}
		@Override
		public String[] validParents() {
			String[] parents =  { "", "html"};
			return parents;
		}

	}


	public abstract class UnaryHtmlTag extends BaseHtmlTag {

		public UnaryHtmlTag(String tagName, CssClass cssClass) {
			super(tagName, cssClass);
		}
		
		/**
		 * Emit this tag in one atomic action
		 */
		public void inject() {
			this.writeStartOfOpeningTagWithoutClosing();
			html.append("/>");	
		}
		
	}
	public class ImgTag extends UnaryHtmlTag {

		public ImgTag(String imgUrl, String alt, CssClass cssClass) {
			super("img", cssClass);
			this.addAttribute("src", imgUrl);
			this.addAttribute("alt", alt);
			this.addAttribute(TITLE_ATTR,alt); // Hover text
			this.addAttribute(TARGET_ATTR,TARGET_WINDOW); // Can we get this link to reuse a window?

		}

		public ImgTag(Motif motif,String alt, CssClass motifClass) {
			super("img", motifClass);
			
			if (motif == null) {
				throw new IllegalArgumentException("Motif must not be null!");
			}
			
			String url = motif.getLargeIconUrl();
			if (url == null) {
				url = motif.getMediumIconUrl();
				if (url== null) {
					url = motif.getSmallIconUrl();
				}
			}
			if (url != null && !url.startsWith("http")) {
				url = getInstanceUrl()  + url;
			}

			this.addAttribute("src", url);
			this.addAttribute("alt", alt);
			this.addAttribute(TITLE_ATTR,alt); // Hover text

		}
		
		@Override
		protected Set<String> requiredAttrs() {
			Set<String> required = new TreeSet<>();
			required.add("src");
			required.add("alt");
			required.add(TITLE_ATTR);
			required.add(WIDTH_ATTR);
			required.add(HEIGHT_ATTR);
			return required;
		}


	}
	
	/**
	 * Combination Anchor and Image
	 * 
	 * @author bobby.white
	 *
	 */
	public class AnchorImgTag extends BinaryHtmlTag {
		private final ImgTag imgTag;
		/**
		 * 
		 * @param imgUrl  The image source
		 * @param label   The Alternate Label used by text viewers
		 * @param idUrl   Anchor url
		 * @param anchorClass   Css Classname
		 * @param pictureClass 
		 */
		public AnchorImgTag(String imgUrl,String label, String idUrl, CssClass anchorClass, CssClass pictureClass) {
			super("a", anchorClass);
			this.addAttribute("href", idUrl);
			this.addAttribute(TARGET_ATTR,TARGET_WINDOW); // Can we get this link to reuse a window?
			imgTag = new ImgTag(imgUrl,label,pictureClass);
		}
		@Override
		/**
		 * Open handles everything
		 */
		public BinaryHtmlTag open() {
			super.open();
			imgTag.inject();
			return this;
		}
		
		@Override
		protected Set<String> requiredAttrs() {
			Set<String> required = new TreeSet<>();
			required.add("href");
			return required;
		}
		
		@Override
		public String[] validParents() {
			return new String[0];
		}
		
	}
	
	public enum CssRule {
		BODY("body","color: #222; font-size: 12px; font-family: Arial, Helvetica, sans-serif; line-height: 15px;background-color:#F5F5F5"),
		ANCHOR("a","color: #015ba7; text-decoration: none; font-family: Arial, Helvetica, sans-serif;"),
		ANCHOR_HOVER("a:hover","text-decoration: underline;"),
		IMG("img","border:0 none;"),
		TABLE(TABLE_TAG,"border-collapse:collapse;border:none;margin:0px;padding:0px;");
		
		protected final String selector;
		protected final String style;
		private CssRule(String selector, String style) {
			this.selector = selector;
			this.style = style;
		}
		public String getSelector() {
			return selector;
		}
		public String getStyle() {
			return style;
		}
	}
	
	
	public enum CssClass {
		
		EMPTY(""),
		SALUTATION_TABLE("salutationTable",L0_WIDTH_STYLE_SETTING + "padding:0px;border:none;margin-bottom:30px;margin-left:15px;margin-right:15px;"),
		SALUTATION_ROW("salutationRow","width:100%;"),
		SALUTATION_TEXT("salutationText","font-weight:bold; font-size:14px;"),
		
		DIGEST_OUTER_TABLE ("digestOuterTable",	"padding:0px;border:none; background-color:white;margin-top:10px;margin-bottom:30px;margin-left:15px;margin-right:15px;"
		+ L0_WIDTH_STYLE_SETTING),
		SECTION_HEADER_OUTER_ROW ("sectionHeaderOuterRow","padding:0px;border:none;width:100%;"),
		SECTION_HEADER_CELL ("sectionHeaderCell","padding:0px;border:none;width:100%;"),
		SECTION_HEADER_TABLE ("sectionHeaderTable","padding:0px;border:none;width:100%;margin:0px;padding-top:0px;margin-top:20px;border-top:1px solid #e8e8e8;"),
		SECTION_HEADER_INNER ("sectionHeaderInnerRow","padding:0px;border:none;width:100%;margin:0px;margin-top:20px;margin-bottom:20px;"),
		
		HEADER_MOTIF("headerMotifCell","width:64px;height:64px;"),
		HEADER_LABEL("headerLabel","font-weight:normal; font-size: 17pt; font-family:Arial, Helvetica, sans-serif;"), 
		HEADER_LABEL_CELL("headerLabelCell","width:100%;text-align:left;vertical-align:top;padding-top:20px;padding-bottom:10px;border:none;"), 
		SECTION_BODY_OUTER_ROW ("sectionBodyOuterRow","padding:0px;border:none;width:100%;"),
		SECTION_BODY("sectionBodyTable",L1_WIDTH_STYLE_SETTING + "padding:0px;border:none;"),
		SUBSECTION_MASTER("subSectionMasterTable",L1_WIDTH_STYLE_SETTING + "padding:0px;border-collapse:collapse;border:solid 1px #e8e8e8;margin-top:20px;"), 
		SUBSECTION_HEADER_OUTER_ROW("subSectionHeaderOuterRow",L1_WIDTH_STYLE_SETTING + "padding:0px;border:none;"),
		SUBSECTION_HEADERTABLE("subSectionHeaderTable","width:100%;padding:0px;border:none;"), 
	    NAME_INNER_TABLE("nameInnerTable","width:100%;padding:0px;border:none;"),
	    NAME_INNER_ROW("nameInnerRow","width:100%;padding:0px;border:none;"),
		SUBSECTION_HEADER_WRAPPER_CELL("subsectionHeaderCell","width:100%;padding:0px;background-color: #f6f7f8;border:none;"),
		SUBSECTION_LABEL("subSectionLabel","color: black; font-weight:normal; font-size: 15pt; font-family:Arial, Helvetica, sans-serif;"), 
		PARENT_ENTITY_LINK("parentEntityLink","padding:0px;border:none;padding-right:5px;text-decoration:none;color: #015ba7; font-weight:bold; font-size: 16px;"),
		SUBSECTION_MOTIF("subSectionMotifCell",	"padding:0px;border:none;width:16px;height:16px;"),
		SUBSECTION_PHOTO_CELL("subSectionPhotoCell","padding:0px;border:none;width:45px;height:45px;text-align:right;vertical-align:center;"),
		SUBSECTION_PHOTO_ANCHOR("subSectionPhotoAnchor","padding:0px;border:none;"),
		SUBSECTION_PHOTO("subSectionPhoto","width:30px;height:30px;padding:0px;border:solid 1px #e8e8e8;margin-right:5px;"),
		SUBSECTION_HEADER_DESCRIPTION("subsectionHeaderDescription",L3_WIDTH_STYLE_SETTING + "padding:0px;border:none;"),
		FOLLOWING_CELL("followingCell","padding:0px;border:none;align:right; white-space:nowrap;"),
		FOLLOWING_TEXT("followingText","font-weight: bold;"),
		SUBSECTION_DETAIL_TABLE("subSectionDetailTable","width:100%;border:none;white-space:normal;"),
		SUBSECTION_DETAIL_INNER_ROW("subSectionDetailInnerRow","width:100%;padding:0px;border:none;"),
		ENTITY_LINK("entityLink","padding:0px;border:none;padding-right:5px;text-decoration:none;color: #015ba7;"),
		ACTOR_CELL("actorCell","width:auto;text-align:left;vertical-align:middle;padding:0px;border:none;"),
		PREAMBLE_TABLE("preambleTable","width:100%;padding:0px;border:none;"),
		PREAMBLE_ROW("preambleRow","width:100%;border:none;padding:0px;"),
		
		RECORD_CONTEXT_INFO_TABLE("recordContextInfoTable","width:100%;padding:0px;border:none;"),
		RECORD_CONTEXT_INFO_INNER_ROW("recordContextInfoInnerRow","width:100%;padding:0px;border:none;"),
		RECORD_CONTEXT_NAME_CELL("recordFieldNameCell","width:20%;text-align:left;vertical-align:middle;border:none;"),
		RECORD_CONTEXT_VALUE_CELL("recordFieldValueCell","width:80%;text-align:left;vertical-align:middle;border:none;"),
		RECORD_CONTEXT_VALUE("recordContextValue","font-style:normal;font-weight:normal;font-size:13px;"), 
		RECORD_CONTEXT_FIELDNAME("recordContextFieldname","font-style:normal;font-weight:bold;font-size:12px;"),

		DETAIL_OUTER_ROW("detailOuterRow",L1_WIDTH_STYLE_SETTING),
		FEEDITEM_DETAIL_MASTER("feedItemDetailMasterTable",L1_WIDTH_STYLE_SETTING  + "padding:0px;border:solid 1px #e8e8e8;border-collapse:collapse;"),
		FEEDITEM_DETAIL("feedItemDetailTable","width:100%;padding:0px;border:none;margin-top:10px;"),
		PROFILE_CELL("profilePictureCell","text-align:left;vertical-align:top;border:none;padding:0px;width:auto;height:auto;"),
		PROFILE_PICTURE_ANCHOR("profilePictureAnchor","padding:0px;border:none;"), 
		PROFILE_PICTURE("profilePicture","width:45px;height:45px;border:1px solid #e8e8e8;padding:0px;margin-right:5px;"), 
		BODY_TEXT_CELL("bodyTextCell","width:100%;text-align:left;vertical-align:top;word-wrap:break-word;border:none;"), 
		
		/** Used in FeedBody **/
		FEEDITEM_BODY_TABLE("feedItemBodyTable","width:100%;padding:0px;border:none;"), // Gray Border WAS here
		FEEDITEM_BODY_INNER_ROW("feedItemBodyInnerRow","width:100%;padding:0px;border:none;"),
		/** Used by Announcement Banner **/
		ANNOUNCEMENT_TABLE("announcementTable","width:100%;padding:0px;border:solid 1px black;border-radius:10px;border-collapse:separate;"),
		ANNOUNCEMENT_ROW("announcementRow","width:100%;padding:0px;border:none;"),
		ANNOUNCEMENT_CELL("announcementCell","padding:0px;padding-left:5px;padding-right:5px;border:none;width:16px;height:16px;"),
		ANNOUNCEMENT_MOTIF("announcementMotif","padding:0px;border:none;width:16px;height:16px;"),
		
		/* 3 Distinct Variations on @mention*/
		MENTION("mentionLink","text-decoration:none; color: #015ba7;font-weight: bold;"),
		MENTION_ME("mentionedMeLink","text-decoration:none; color: #015ba7; background-color: yellow; font-weight: bold;"),
		BANG_MENTION("bangMention","text-decoration:none; color: #7d7d84;font-weight: bold;"),
		
		URL_LINK("urlLink","text-decoration:none; color: #015ba7; white-space:normal; max-width:200px;"), 
		HASHTAG("hashtag","padding:0px;border:none;text-decoration:none; color: #015ba7;"), 
		QUESTION_TITLE("questionTitleText","font-size:16px;font-color:black;font-weight:bold;"),

		/** used in capabilities **/
		COMMENT_OUTER_ROW("commentCapabilityOuterRow","width:100%;padding:0px;border:none;"),
		COMMENT_CAPABILITY_ROW("commentCapabilityRow","width:100%;padding:0px;border:none;" + COMMENT_AND_LIKE_COLOR), 
		COMMENT_CAPABILITY_CELL("commentCapabilityCell","width:100%;text-align:left;vertical-align:top;padding:0px;border:none;"), 
		COMMENT_CAPABILITY_TABLE("commentCapabilityTable",L2_WIDTH_STYLE_SETTING +"padding:0px;border:none;"),
		COMMENT_PROFILE_PICTURE_CELL("commentProfilePictureCell","padding:0px;border:none;text-align:left;vertical-align:top;width:auto;"),
		COMMENT_PROFILE_PICTURE_ANCHOR("commentProfilePictureAnchor","padding:0px;border:none;"),
		COMMENT_PROFILE_PICTURE("commentProfilePicture","width:30px;height:30px;border:solid 1px #e8e8e8;padding:0px;margin-right:5px;"), 

		COMMENT_INNER_TABLE("innerTable","width:100%;padding:0px;border:none;"),
		COMMENT_INNER_ROW("commentInnerRow","width:100%;padding:0px;border:none;"),
		
		LIKE_CAPABILITY_OUTER_ROW("likeCapabilityOuterRow","width:100%;padding:0px;border:none;"),
		LIKE_CAPABILITY_CELL("likeCapabilityCell","width:100%;padding:0px;border:none;padding-bottom:5px;" + COMMENT_AND_LIKE_COLOR),
		LIKE_INNER_TABLE("likeInnerTable",L2_WIDTH_STYLE_SETTING + BORDER_NONE),
		LIKE_INNER_ROW("likeInnerRow","width:100%;padding:0px;border:none;"),
		LIKE_MOTIF_CELL("likeMotifCell","padding:0px;border:none;width:16px;height:16px;"), 
		LIKE_MOTIF("likeMotif","padding:0px;border:none;width:16px;height:16px;"), 
		
		TOPICS_CAPABILITY_OUTER_ROW("topicsCapabilityOuterRow","width:100%;padding:0px;border:none;"),
		TOPICS_CAPABILITY_CELL("topicsCapabilityCell","width:100%;padding:0px;border:none;background-color:white;"),
		TOPICS_INNER_TABLE("topicsInnerTable",L2_WIDTH_STYLE_SETTING + BORDER_NONE),
		TOPICS_INNER_ROW("topicsInnerRow","width:100%;padding:0px;border:none;"), 
		TOPICS_LABEL_CELL("topicsLabelCell","padding:0px;border:none;text-align:left;vertical-align:center;"),
		TOPICS_TAGS_CELL("topicTagsCell","padding:0px;border:none;text-align:left;vertical-align:center;left-padding:15px;width:100%;"),
		TOPICS_LABEL("topicsLabelText","font-weight:bold; font-size:14px;"),
		TOPIC_LINK_TAG("topicLinkTag","display: inline-block; white-space: nowrap; overflow: visible;border: 1px solid #e4e7e9; padding: 4px 8px; min-width: 36px; border-radius: 0; margin: 2px 2px 2px 0; text-decoration: none; text-align: center; font-size: 11px; font-weight: 600; white-space: nowrap; line-height: 1.4em; display: inline-block; color: #475f67; background: #f9f9f9;"),

		
		LINK_CAPABILITY_OUTER_ROW("linkCapabilityOuterRow","width:100%;padding:0px;border:none;"),
		LINK_CAPABILITY_TABLE("linkCapabilityTable","border: 1px solid lightgray;"+L2_WIDTH_STYLE_SETTING),
		LINK_INNER_ROW("linkInnerRow","width:100%;padding:0px;border:none;"),
		LINK_ICON("linkIcon","width:16px;height:16px;"),


		FEEDITEM_INNER_DETAIL_ROW("feedItemInnerDetailRow","width:100%;padding:0px;border:none;"), 
		FEEDITEM_INNER_DETAIL_TABLE("feedItemInnerDetailTable","width:100%;padding:0px;border:none;"), 

		RECOMMENDATION_CAPABILITY_OUTER_ROW("recommendationCapabilityRow","width:100%;padding:0px;border:none;"),
		RECOMMENDATION_CAPABILITY_CELL("recommendationCapabilityCell","width:100%;text-align:left;vertical-align:top;padding:0px;border: 1px solid #e8e8e8; background: lightgray;"), 
		//RECOMMENDATION_CAPABILITY("recommendationCapabilityTable","padding:0px;border: 1px solid #e8e8e8; background: lightgray;"),
		RECOMMENDATION_INNER_TABLE("recommendationInnerTable","padding:0px;border:none;table-layout:auto;width:100%;"),
		REC_INNER_ROW("recInnerRow","width:100%;padding:0px;border:none;"),
		RECOMMENDATION_MOTIF("recommendationMotifCell","padding:0px;border:none;width:64px;height:64px;"),
		RECOMMENDATION_LABEL("recommendationLabel","font-weight:bold; padding:10px"),
		MINI_DETAIL_TABLE("miniDetailTable","width:100%;padding:0px;border:none;"),
		MINI_DETAIL_ROW("miniDetailRow","width:100%;padding:0px;border:none;"),
		MINI_DETAIL_CELL("miniDetailCell","padding:0px;border:none;color:green;width:100%;border:none;"),

		CONTENT_CAPABILITY_OUTER_ROW("contentCapabilityOuterRow","width:100%;padding:0px;border:none;"),
		CONTENT_CAPABILITY("contentCapabilityTable","border: 1px solid lightgray;"+L2_WIDTH_STYLE_SETTING),
		CONTENT_INNER_ROW("contentInnerRow","width:100%;padding:0px;border:none;"),
		CONTENT_TITLE_CELL("contentTitleCell","width:100%;padding-top:5px;padding-right:5px;text-align:left;vertical-align:middle;border:none;"),
		CONTENT_ICON_CELL("contentIconCell","padding:0px;border:none;width:32px;height:32px;"),
		CONTENT_ICON("contentIcon","padding:0px;border:none;width:32px;height:32px;margin-right:5px;"),
		CONTENT_THUMBNAIL_CELL("contentThumbnailCell","padding:5px;border:none;width:auto;height:auto;"), // try not to force width
		CONTENT_THUMBNAIL("contentThumbnail","padding:0px;border:none;width:auto;height:auto;"),

		DOWNLOAD_ICON("downloadIcon","padding:0px;border:none;width:16px;height:16px;"),

		FTC_OUTER("fieldTrackedChangeOuterRow","width:100%;padding:0px;border:none;"), 
		FTC_CAPABILITY_TABLE("fieldTrackedChangeCapabilityTable","width:100%;border: 1px solid light gray;"),
		FTC_INNER_TABLE("fieldTrackedChangeInnerTable",L2_WIDTH_STYLE_SETTING + "border:none;margin-top:5px;margin-bottom:5px;"),
		FTC_DETAIL_ROW("fieldTrackedChangeDetailRow","width:100%;padding:0px;border:none;"),
		FTC_INNER_ROW("fieldTrackedChangeInnerRow","width:100%;padding:0px;border:none;"),
		FTC_NORMAL_TEXT("fieldTrackedChangeNormalText","font-style:normal;font-weight:normal;font-size:13px;"), 
		FTC_TEXT("fieldTrackedChangedText","font-style:italic;font-weight:normal;font-size:13px;"), 
		FTC_FIELDNAME("fieldtrackedChangeFieldName","font-style:normal;font-weight:bold;font-size:12px;"),
		FTC_FIELDNAME_CELL("ftcFieldNameCell","width:20%;text-align:left;vertical-align:top;border:none;"),
		FTC_LABEL_CELL("ftcFromToLabelCell","width:5%;text-align:left;vertical-align:middle;border:none;padding-left:2px;padding-right:2px;"),
		FTC_ORIGINAL_VALUE_CELL("ftcOriginalValueCell","width:35%;text-align:left;vertical-align:top;border:none;"),
		FTC_NEW_VALUE_CELL("ftcNewValueCell","width:35%;text-align:left;vertical-align:top;border:none;"),
		
		CANVAS_CAPABILITY_OUTER_ROW("canvasCapabilityOuterRow","width:100%;padding:0px;border:none;"),
		CANVAS_CAPABILITY_CELL("canvasCapabilityCell","width:100%;padding:0px;border:none;background-color:white;"),
		CANVAS_INNER_TABLE("canvasInnerTable",L2_WIDTH_STYLE_SETTING + "border:1px solid lightgray;"),
		CANVAS_INNER_ROW("canvasInnerRow","width:100%;padding:0px;border:none;"), 
		CANVAS_ICON("canvasIcon","width:45px;height:45px;border:none;"),
		CANVAS_ICON_CELL("canvasIconCell","width:45px;height:45px;text-align:left;vertical-align:top;"),
		CANVAS_TITLE_TEXT("canvasTitleText","font-weight:bold;font-size:14px;font-color:black;"),
		CANVAS_DESC_TEXT("canvasDescText","font-weight:normal;font-size:12px;font-color:black;"),
		
		ELINK_CAPABILITY_OUTER_ROW("enhancedLinkCapabilityOuterRow","width:100%;padding:0px;border:none;"),
		ELINK_CAPABILITY_CELL("enhancedLinkCapabilityCell","width:100%;padding:0px;border:none;background-color:white;"),
		ELINK_INNER_TABLE("enhancedLinkInnerTable",L2_WIDTH_STYLE_SETTING + "border:1px solid lightgray;"),
		ELINK_INNER_ROW("enhancedLinkInnerRow","width:100%;padding:0px;border:none;"), 
		ELINK_ICON("enhancedLinkIcon",BORDER_NONE),
		ELINK_ICON_CELL("enhancedLinkIconCell","text-align:left;vertical-align:top;border:none;padding:5px;"),
		ELINK_TITLE_TEXT("enhancedLinkTitleText","font-weight:bold;font-size:14px;font-color:black;"),
		ELINK_DESC_TEXT("enhancedLinkDescText","font-weight:normal;font-size:12px;font-color:black;"),

		
		METADATALINE_OUTER_ROW("metadataLineOuterRow","width:100%;padding:0px;border:none;"),
		METADATA_CAPABILITY("metadataCapabilityTable","width:100%;padding:0px;border:none;"),
		METADATA_INNER_ROW("metadataInnerRow","width:100%;padding:0px;border:none;"),

		TIMESTAMP_CELL("timestampCell","padding:0px;border:none;text-align:left;vertical-align:top;width:100%;padding-top:5px;padding-bottom:5px;"),
		TIMESTAMP_LINK("timestampLink","text-decoration:none;white-space:nowrap;"), 
		COMMENT_ACTION_CELL("commentActionCell","padding:0px;border:none;padding-right:5px;text-align:right;vertical-align:top;width:100%;padding-top:5px;padding-bottom:5px;"),
		COMMENT_ACTION_LINK("commentActionLink","text-decoration:none;white-space:nowrap;"),
		COMMENTS_COUNT_CELL("commentsCountCell","padding:0px;border:none;text-align:left;vertical-align:top;width:100%;"),
		
		MORE_BUTTON("moreButtonCell","text-decoration:none;white-space:nowrap;"),
        FOOTER_TEXT("footerText","font-size:14px; font-style:italics; font-weight:normal;"),
		FOOTER_TABLE("footerTable",L0_WIDTH_STYLE_SETTING + "padding:0px;border:none;margin-left:15px;margin-right:15px;"),
		FOOTER_ROW("footerRow","width:100%"),
		FOOTER_CELL("footerCell","padding:0px;border:none;text-align:left;vertical-align:top;width:100%;"),
		
		PLAIN_WRAPPER_CELL("wrapperCell","padding:0px;border:none;text-align:left;vertical-align:middle;width:100%")
		;


		
		// Members
		private final String name;
		private final String style;
		
		/**
		 * Private Constructor
		 * @param name
		 */
		CssClass(String name, String defaultStyle) {
			if (allCssClasses.contains(name)) {
				throw new IllegalArgumentException("CssClass internal name is already in use! " + name); 
			} else {
			   allCssClasses.add(name);
			}
			this.name = name;
			this.style = defaultStyle;
		}
		CssClass(String name) {
			if (allCssClasses.contains(name)) {
				throw new IllegalArgumentException("CssClass internal name is already in use! " + name); 
			} else {
			   allCssClasses.add(name);
			}
			this.name = name;
			this.style = null;
		}

		public String getName() {
			return name;
		}
		public String getStyle() {
			return style;
		}
		
	}
}
