package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_comment_page.htm#connect_responses_comment_page
 * @author bobby.white
 *
 */
public class CommentPage {
   String currentPageUrl;
   Comment[] items;
   String nextPageUrl;
   int total;
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("CommentPage [");
	
	if (currentPageUrl != null) {
		builder.append("currentPAgeUrl=");
		builder.append(currentPageUrl);
		builder.append(", ");
	}
	if (items != null) {
		builder.append("items=");
		builder.append(Arrays.asList(items).subList(0,
				Math.min(items.length, maxLen)));
		builder.append(", ");
	}
	if (nextPageUrl != null) {
		builder.append("nextPageUrl=");
		builder.append(nextPageUrl);
		builder.append(", ");
	}
	builder.append("total=");
	builder.append(total);
	builder.append("]");
	return builder.toString();
}
public String getCurrentPageUrl() {
	return currentPageUrl;
}
public Comment[] getItems() {
	return items;
}
public String getNextPageUrl() {
	return nextPageUrl;
}
public int getTotal() {
	return total;
}
}
