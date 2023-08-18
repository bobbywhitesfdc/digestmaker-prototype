package bobby.sfdc.prototype.json;

import java.util.Arrays;

/** 
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_like_page.htm
 * @author bobby.white
 *
 */

public class LikePage {
   String currentPageUrl;
   Like[] items;
   Like[] likes;
   Reference myLike;
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("LikePage [");
	if (currentPageUrl != null) {
		builder.append("currentPageUrl=");
		builder.append(currentPageUrl);
		builder.append(", ");
	}
	if (items != null) {
		builder.append("items=");
		builder.append(Arrays.asList(items).subList(0,
				Math.min(items.length, maxLen)));
		builder.append(", ");
	}
	if (likes != null) {
		builder.append("likes=");
		builder.append(Arrays.asList(likes).subList(0,
				Math.min(likes.length, maxLen)));
		builder.append(", ");
	}
	if (myLike != null) {
		builder.append("myLike=");
		builder.append(myLike);
	}
	builder.append("]");
	return builder.toString();
}
public String getCurrentPageUrl() {
	return currentPageUrl;
}
public Like[] getItems() {
	return items;
}
public Like[] getLikes() {
	return likes;
}
public Reference getMyLike() {
	return myLike;
}
}
