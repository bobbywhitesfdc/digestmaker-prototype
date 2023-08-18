package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_comment.htm#connect_responses_comment
 * @author bobby.white
 *
 */
public class Comment {
   /**
    * Intentionally omitted these fields as advised in documentation "after version 32.0"
    * - attachment
    * - feedItem
    * - isDeletable
    */
	FeedItemBody body;
	CommentCapabilities capabilities;
	ClientInfo clientInfo;
	String createdDate;
	Reference feedElement;
	Reference feedItem;
	String id;
	boolean isDeleteRestricted;
	LikePage likes;
	LikeMessageBody likesMessage;
	Reference myLike;
	Reference parent;
	String relativeCreatedDate;
	String type;
	String url;
	UserSummary user;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [");
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
		if (clientInfo != null) {
			builder.append("clientInfo=");
			builder.append(clientInfo);
			builder.append(", ");
		}
		if (createdDate != null) {
			builder.append("createdDate=");
			builder.append(createdDate);
			builder.append(", ");
		}
		if (feedElement != null) {
			builder.append("feedElement=");
			builder.append(feedElement);
			builder.append(", ");
		}
		if (feedItem != null) {
			builder.append("feedItem=");
			builder.append(feedItem);
			builder.append(", ");
		}
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		builder.append("isDeleteRestricted=");
		builder.append(isDeleteRestricted);
		builder.append(", ");
		if (likes != null) {
			builder.append("likes=");
			builder.append(likes);
			builder.append(", ");
		}
		if (likesMessage != null) {
			builder.append("likesMessage=");
			builder.append(likesMessage);
			builder.append(", ");
		}
		if (myLike != null) {
			builder.append("myLike=");
			builder.append(myLike);
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
		if (type != null) {
			builder.append("type=");
			builder.append(type);
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
			builder.append(", ");
		}
		if (user != null) {
			builder.append("user=");
			builder.append(user);
		}
		builder.append("]");
		return builder.toString();
	}
	public FeedItemBody getBody() {
		return body;
	}
	public CommentCapabilities getCapabilities() {
		return capabilities;
	}
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public Reference getFeedElement() {
		return feedElement;
	}
	public Reference getFeedItem() {
		return feedItem;
	}
	public String getId() {
		return id;
	}
	public boolean isDeleteRestricted() {
		return isDeleteRestricted;
	}
	public LikePage getLikes() {
		return likes;
	}
	public LikeMessageBody getLikesMessage() {
		return likesMessage;
	}
	public Reference getMyLike() {
		return myLike;
	}
	public Reference getParent() {
		return parent;
	}
	public String getRelativeCreatedDate() {
		return relativeCreatedDate;
	}
	public String getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public UserSummary getUser() {
		return user;
	}
	
}
