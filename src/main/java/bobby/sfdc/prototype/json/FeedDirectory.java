package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_dir.htm
 * 
 * @author bobby.white
 *
 */
public class FeedDirectory {
   Favorite[] favorites;
   FeedDirectoryItem[] feeds;
/**
 * @return the favorites
 */
public Favorite[] getFavorites() {
	return favorites;
}
/**
 * @return the feeds
 */
public FeedDirectoryItem[] getFeeds() {
	return feeds;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("FeedDirectory [");
	if (favorites != null) {
		builder.append("favorites=");
		builder.append(Arrays.asList(favorites).subList(0,
				Math.min(favorites.length, maxLen)));
		builder.append(", ");
	}
	if (feeds != null) {
		builder.append("feeds=");
		builder.append(Arrays.asList(feeds).subList(0,
				Math.min(feeds.length, maxLen)));
	}
	builder.append("]");
	return builder.toString();
}
}
