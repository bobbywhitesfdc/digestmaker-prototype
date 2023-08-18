package bobby.sfdc.prototype.json;

import java.util.Set;
import java.util.TreeSet;

/**
 * Union of FeedItem, GenericFeedElement and other classes that can be contained by the feed
 * 
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_element_generic.htm#connect_responses_feed_element_generic
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_item.htm
 * @author bobby.white
 *
 */
public class BaseFeedElement  {
	UserSummary actor;
	FeedItemBody body;
	FeedElementCapabilities capabilities;
	FeedItemBody header;
	String createdDate;
	String feedElementType;
	String id;
	String modifiedDate;
	BaseSummary parent;
	/** parent is one of File Detail, File Summary, Group, Group Detail
	 * , Record Summary, Record View, User Detail, User Summary **/
	String relativeCreatedDate;
	String url;
	//FeedItemAttachment attachment; Obsolete as of v32.0
	boolean canShare;
	ClientInfo clientInfo;
	//CommentPage comments;  Obsolete as of v32.0
	//Reference currentUserLike; Obsolete as of v24.0
	boolean event;
	// boolean isBookmarkedByCurrentUser; Obsolete as of v32.0
	boolean isDeleteRestricted;
	//boolean isLikedByCurrentUser;  Obsolete as of v32.0
	//LikePage likes; Obsolete as of v32.0
	//LikeMessageBody likesMessage; Obsolete as of v32.0
	//ModerationFlags moderationFlags; Obsolete as of v32.0
	//Reference myLike;  Obsolete as of v32.0
	Reference originalFeedItem;
	String photoUrl;
	FeedItemPreambleMessageBody preamble;
	String relatedCreatedDate;
	//FeedItemTopics topics;  Obs as of v32.0
	String type;
	String visibility;
	public FeedItemBody getBody() {
		return body;
	}
	public void setBody(FeedItemBody body) {
		this.body = body;
	}
	public FeedElementCapabilities getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(FeedElementCapabilities capabilities) {
		this.capabilities = capabilities;
	}
	public FeedItemBody getHeader() {
		return header;
	}
	public void setHeader(FeedItemBody header) {
		this.header = header;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getFeedElementType() {
		return feedElementType;
	}
	public void setFeedElementType(String feedElementType) {
		this.feedElementType = feedElementType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public BaseSummary getParent() {
		return parent;
	}
	public void setParent(BaseSummary parent) {
		this.parent = parent;
	}
	public String getRelativeCreatedDate() {
		return relativeCreatedDate;
	}
	public void setRelativeCreatedDate(String relativeCreatedDate) {
		this.relativeCreatedDate = relativeCreatedDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isCanShare() {
		return canShare;
	}
	public void setCanShare(boolean canShare) {
		this.canShare = canShare;
	}
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	public boolean isEvent() {
		return event;
	}
	public void setEvent(boolean event) {
		this.event = event;
	}
	public boolean isDeleteRestricted() {
		return isDeleteRestricted;
	}
	public void setDeleteRestricted(boolean isDeleteRestricted) {
		this.isDeleteRestricted = isDeleteRestricted;
	}
	public Reference getOriginalFeedItem() {
		return originalFeedItem;
	}
	public void setOriginalFeedItem(Reference originalFeedItem) {
		this.originalFeedItem = originalFeedItem;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public FeedItemPreambleMessageBody getPreamble() {
		return preamble;
	}
	public void setPreamble(FeedItemPreambleMessageBody preamble) {
		this.preamble = preamble;
	}
	public String getRelatedCreatedDate() {
		return relatedCreatedDate;
	}
	public void setRelatedCreatedDate(String relatedCreatedDate) {
		this.relatedCreatedDate = relatedCreatedDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVisibility() {
		return visibility;
	}
	
	/**
	 * A derived method for convenience.  Is this a Recommendation?
	 * @return true if it is.
	 */
	public boolean isRecommendation() {
	   return this.getType()==null;	
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseFeedElement [");
		
		if (actor != null) {
			builder.append("actor=");
			builder.append(actor);
			builder.append(", ");
		}
		if (body != null) {
			builder.append("body=");
			builder.append(body);
			builder.append(", ");
		}
		if (capabilities != null) {
			builder.append("capabilities=");
			builder.append(capabilities);
			builder.append(", ");
		}
		if (header != null) {
			builder.append("header=");
			builder.append(header);
			builder.append(", ");
		}
		if (createdDate != null) {
			builder.append("createdDate=");
			builder.append(createdDate);
			builder.append(", ");
		}
		if (feedElementType != null) {
			builder.append("feedElementType=");
			builder.append(feedElementType);
			builder.append(", ");
		}
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (modifiedDate != null) {
			builder.append("modifiedDate=");
			builder.append(modifiedDate);
			builder.append(", ");
		}
		if (parent != null) {
			builder.append("parent=");
			builder.append(parent);
			builder.append(", ");
		}
		if (relativeCreatedDate != null) {
			builder.append("relativeCreatedDate=");
			builder.append(relativeCreatedDate);
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
			builder.append(", ");
		}
		builder.append("canShare=");
		builder.append(canShare);
		builder.append(", ");
		if (clientInfo != null) {
			builder.append("clientInfo=");
			builder.append(clientInfo);
			builder.append(", ");
		}
		builder.append("event=");
		builder.append(event);
		builder.append(", isDeleteRestricted=");
		builder.append(isDeleteRestricted);
		builder.append(", ");
		if (originalFeedItem != null) {
			builder.append("originalFeedItem=");
			builder.append(originalFeedItem);
			builder.append(", ");
		}
		if (photoUrl != null) {
			builder.append("photoUrl=");
			builder.append(photoUrl);
			builder.append(", ");
		}
		if (preamble != null) {
			builder.append("preamble=");
			builder.append(preamble);
			builder.append(", ");
		}
		if (relatedCreatedDate != null) {
			builder.append("relatedCreatedDate=");
			builder.append(relatedCreatedDate);
			builder.append(", ");
		}
		if (type != null) {
			builder.append("type=");
			builder.append(type);
			builder.append(", ");
		}
		if (visibility != null) {
			builder.append("visibility=");
			builder.append(visibility);
		}
		builder.append("]");
		return builder.toString();
	}
	public UserSummary getActor() {
		return actor;
	}
	public void setActor(UserSummary actor) {
		this.actor = actor;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	/**
	 * Convenience overload for a single ID
	 * 
	 * @param mentionedUserOrGroup
	 * @return true if this user or group is mentioned
	 */
	public boolean isAtMentioned(String mentionedUserOrGroup) {
		Set<String> ids = new TreeSet<String>();
		ids.add(mentionedUserOrGroup);
		return this.isAtMentioned(ids);
	}
	
	/**
	 * Is one of these  User or Group At Mentioned?
	 * @param mentionedUserOrGroup  user 
	 * @return true if they are mentioned
	 */
	public boolean isAtMentioned(Set<String> mentionedUserOrGroups) {
		for (MessageSegment segment : getBody().getMessageSegments()) {
			if (segment.getType().compareTo("Mention")==0 
					&& mentionedUserOrGroups.contains(segment.getRecord().getId())) {
				return true; // short circuit exit
			}
		}
		if (this.getCapabilities().getComments() != null && this.getCapabilities().getComments().getPage() != null
				&& this.getCapabilities().getComments().getPage().getItems() != null) {
			
			for (Comment comment : getCapabilities().getComments().getPage().getItems()) {
				for (MessageSegment segment : comment.getBody().getMessageSegments()) {
					if (segment.getType().compareTo("Mention")==0 
							&& mentionedUserOrGroups.contains(segment.getRecord().getId())) {
						return true; // short circuit exit
					}
				}
			}
		}

		return false;
	}
	
}
