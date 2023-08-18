package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_chatter_likes_capability.htm
 * @author bobby.white
 *
 */
public class ChatterLikesCapability {
  boolean isLikedByCurrentUser;
  LikePage like;
  LikeMessageBody likesMessage;
  Reference myLike;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ChatterLikesCapability [isLikedByCurrentUser=");
	builder.append(isLikedByCurrentUser);
	builder.append(", ");
	if (like != null) {
		builder.append("like=");
		builder.append(like);
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
	}
	builder.append("]");
	return builder.toString();
}
public boolean isLikedByCurrentUser() {
	return isLikedByCurrentUser;
}
public LikePage getLike() {
	return like;
}
public LikeMessageBody getLikesMessage() {
	return likesMessage;
}
public Reference getMyLike() {
	return myLike;
}
}
