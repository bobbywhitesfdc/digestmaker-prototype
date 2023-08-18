package bobby.sfdc.prototype;

import bobby.sfdc.prototype.json.BaseRecommendation;
import bobby.sfdc.prototype.json.ChatterLikesCapability;
import bobby.sfdc.prototype.json.Comment;
import bobby.sfdc.prototype.json.ContentCapability;
import bobby.sfdc.prototype.json.FeedElementCapabilities;
import bobby.sfdc.prototype.json.FeedTrackedChange;
import bobby.sfdc.prototype.json.LinkCapability;
import bobby.sfdc.prototype.json.TrackedChangesCapability;
import bobby.sfdc.prototype.json.UserProfile;

public class XMLDigestRenderer extends BaseDigestRenderer implements IRenderingEngine {
	protected static final char QUOTE_CHAR = '\"';
	
	protected final StringBuffer xml;
	private boolean _commentsEnabled=true;
	
	public XMLDigestRenderer(String instanceUrl, UserProfile profile, StringBuffer xml) {
		super(instanceUrl,profile);
		this.xml=xml;
	}

	@Override
	public void addComment(String comment) {
		if (_commentsEnabled) {
		     xml.append("\n<!--").append(comment).append("-->\n");
		}
	}

	@Override
	public void renderBeginDigestFeedContent() {
		xml.append("<DIGEST>\n");
	}

	@Override
	public void renderEndDigestFeedContent() {
		// Render
		xml.append("\n</DIGEST>\n");
	}

	@Override
	public void renderBeginSection() {
		// Start the new Section
		xml.append("<SECTION");
		addXMLProperty(xml,"type",item.getParent()!=null ? item.getParent().getType() : null);
		addXMLProperty(xml,"label",getSectionLabel());
		xml.append(" >\n");
	}

	@Override
	public void renderEndOfSection() {
		xml.append("\n\t</SECTION>\n");
	}

	@Override
	public void renderBeginSubSection() {
		// Begin new sub-section
		xml.append("\t<SUBSECTION");
		addXMLProperty(xml,"name",item.getParent().getName());
		addXMLProperty(xml,"id",item.getParent().getId());
		addXMLProperty(xml,"displayName",item.getParent().getDisplayName());
		addXMLProperty(xml,"description",item.getParent().getDescription());
		addXMLProperty(xml,"photo_url",item.getParent().getPhoto()!=null ? item.getParent().getPhoto().getFullEmailPhotoUrl() : null);

		xml.append(" >\n");
	}

	@Override
	public void renderEndOfSubSection() {
		xml.append("\n\t</SUBSECTION>\n");
	}

	@Override
	public void renderDetail() {
		// Begin a new Detail Line
		xml.append("\t\t<DETAIL ");
		addXMLProperty(xml,"type",item.getType());
		addXMLProperty(xml,"id",item.getId());
		addXMLProperty(xml,"modified_date",item.getModifiedDate());
		addXMLProperty(xml,"photo_url",item.getPhotoUrl());

		xml.append(" >");
		if (item.getBody() != null 
				&& item.getBody().getText()!=null
				&& item.getBody().getText().compareTo("null")!=0) {
		   xml.append(item.getBody().getText());
		} else if (item.getHeader()!=null && item.getHeader().getText()!=null) {
		   xml.append(item.getHeader().getText());	
		}
		
		if (item.getType()!=null && item.getType().compareTo("CollaborationGroupCreated")==0) {
			System.out.println(item.toString());
		}
		if (item.getCapabilities() != null) {
			renderCapabilities(item.getCapabilities());
		}
		xml.append("</DETAIL>\n");
		
	}
	
	private void renderCapabilities(FeedElementCapabilities capabilities) {
		
		xml.append("\n\t\t\t<CAPABILITIES>");
		addComment("Rendering Capabilities");
		
		//xml.append(capabilities.toString());
		
		//File
		if (capabilities.getContent() != null && capabilities.getContent().getTitle()!=null) {
			ContentCapability content = capabilities.getContent();
			xml.append("\n<CONTENT");
			addXMLProperty(xml,"title",content.getTitle());
			addXMLProperty(xml,"description",content.getDescription());
			addXMLProperty(xml,"mime-type",content.getMimeType());			
			addXMLProperty(xml,"url",content.getDownloadUrl());			
			xml.append(" >");
			xml.append("\n</CONTENT>");	
		}
		//Link
		if (capabilities.getLink() !=null && capabilities.getLink().getUrl() !=null) {
			LinkCapability link = capabilities.getLink();
			xml.append("\n<LINK");
			addXMLProperty(xml,"url",link.getUrl());
			addXMLProperty(xml,"name",link.getUrlName());
			xml.append(" >");
			xml.append("\n</LINK>");
		}

		//Likes
		if (capabilities.getChatterLikes()!=null 
				&& capabilities.getChatterLikes().getLikesMessage()!=null) {
			ChatterLikesCapability likes = capabilities.getChatterLikes();
			int likeCount = (likes.getLike() != null && likes.getLike().getItems() != null) ? likes.getLike().getItems().length : 0;
			xml.append("\n<LIKES");
			addXMLProperty(xml,"isLikedByCurrentUser",likes.isLikedByCurrentUser()+"");
			addXMLProperty(xml,"like-count",likeCount+"");
			xml.append(" >");
			xml.append(likes.getLikesMessage().getText());
			xml.append("\n</LIKES>");	
		}

		//Comments
		if (capabilities.getComments() != null
				&& capabilities.getComments().getPage()!=null
				&& capabilities.getComments().getPage().getItems() !=null
				&& capabilities.getComments().getPage().getItems().length > 0) {
			Comment[] comments = capabilities.getComments().getPage().getItems();
			xml.append("\n<COMMENTS ");
			addXMLProperty(xml, "count", ""+comments.length);
			xml.append(" >");
			
			for (Comment comment : comments) {
				xml.append("\n<COMMENT>");
				xml.append(comment.getBody().getText());
				xml.append("\n</COMMENT>");
			}
			
			xml.append("\n</COMMENTS>");	
		}
		
		//Recommendations
		if (capabilities.getRecommendations()!=null 
				&& capabilities.getRecommendations().getItems()!=null 
				&& capabilities.getRecommendations().getItems().length > 0) {
			BaseRecommendation[] recommendations = capabilities.getRecommendations().getItems();
			xml.append("\n<RECOMMENDATIONS>");
			
			for (BaseRecommendation recommendation : recommendations) {
				xml.append("\n<RECOMMENDATION>");
				addXMLProperty(xml,"type",recommendation.getRecommendationType());
				addXMLProperty(xml,"displayLabel",recommendation.getDisplayLabel());
				xml.append(" >");
				xml.append("\n</RECOMMENDATION>");
			}
			
			xml.append("\n</RECOMMENDATIONS>");	

		}
		
		// TrackedChanges
		if (capabilities.getTrackedChanges() !=null) {
			TrackedChangesCapability changes = capabilities.getTrackedChanges();
			xml.append("\n<TRACKEDCHANGES>");
			
			for (FeedTrackedChange change : changes.getChanges()) {
				xml.append("\n<TRACKEDCHANGE>");
				addXMLProperty(xml,"field",change.getFieldName());
				addXMLProperty(xml,"old-value",change.getOldValue());
				addXMLProperty(xml,"new-value",change.getNewValue());
				xml.append(" >");
				xml.append("\n</TRACKEDCHANGE>");
			}
			
			xml.append("\n</TRACKEDCHANGES>");	
			
		}

		
		xml.append("\n\t\t\t</CAPABILITIES>\n");
	}

	
	private void addXMLProperty(StringBuffer buff, String name, String value) {
		if (value != null) {
		   buff.append(" ").append(name).append('=').append(QUOTE_CHAR).append(value).append(QUOTE_CHAR);
		}
	}

	@Override
	public void renderBeginDocument() {
		xml.append("<USERPROFILE>\n");
		xml.append("</USERPROFILE>\n");
	}

	@Override
	public void renderEndDocument() {
		xml.append("<FOOTER>\n").append("</FOOTER>\n");
	}



}
