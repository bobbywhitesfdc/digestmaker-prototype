package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_bookmarks_capability.htm
 * @author bobby.white
 *
 */
public class BookmarksCapability {
   boolean isBookmarkedByCurrentUser;

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("BookmarksCapability [isBookmarkedByCurrentUser=");
	builder.append(isBookmarkedByCurrentUser);
	builder.append("]");
	return builder.toString();
}

/**
 * @return the isBookmarkedByCurrentUser
 */
public boolean isBookmarkedByCurrentUser() {
	return isBookmarkedByCurrentUser;
}
}
