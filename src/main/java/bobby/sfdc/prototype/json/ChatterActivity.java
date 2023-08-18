package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_activity.htm
 * 
 * 
 * **/

public class ChatterActivity {
	int commentCount;
	int commentReceivedCount;
	int likeReceivedCount;
	int postCount;
	public int getCommentCount() {
		return commentCount;
	}
	public int getCommentReceivedCount() {
		return commentReceivedCount;
	}
	public int getLikeReceivedCount() {
		return likeReceivedCount;
	}
	public int getPostCount() {
		return postCount;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChatterActivity [commentCount=");
		builder.append(commentCount);
		builder.append(", commentReceivedCount=");
		builder.append(commentReceivedCount);
		builder.append(", likeReceivedCount=");
		builder.append(likeReceivedCount);
		builder.append(", postCount=");
		builder.append(postCount);
		builder.append("]");
		return builder.toString();
	}
}
