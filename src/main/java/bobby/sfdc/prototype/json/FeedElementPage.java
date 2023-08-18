package bobby.sfdc.prototype.json;


/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_element_page.htm
 * 
 * @author bobby.white
 *
 */
public class FeedElementPage {
	String currentPageUrl;
	String isModifiedToken;
	String isModifiedUrl;
	String nextPageToken;
	String nextPageUrl;
	String updatesToken;
	String updatesUrl;
	BaseFeedElement[] elements; // This needs to be the Parent of (FeedItem, GenericFeedElement,?)

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeedElementPage [currentPageUrl=");
		builder.append(currentPageUrl);
		builder.append(", isModifiedToken=");
		builder.append(isModifiedToken);
		builder.append(", isModifiedUrl=");
		builder.append(isModifiedUrl);
		builder.append(", nextPageToken=");
		builder.append(nextPageToken);
		builder.append(", nextPageUrl=");
		builder.append(nextPageUrl);
		builder.append(", updatesToken=");
		builder.append(updatesToken);
		builder.append(", updatesUrl=");
		builder.append(updatesUrl);
		builder.append(", elements=");
		
		for(BaseFeedElement e : elements) {
			builder.append(e.toString());
			builder.append('\n');
		}
		builder.append("]");
		return builder.toString();
	}

	protected String getCurrentPageUrl() {
		return currentPageUrl;
	}

	protected void setCurrentPageUrl(String currentPageUrl) {
		this.currentPageUrl = currentPageUrl;
	}

	protected String getIsModifiedToken() {
		return isModifiedToken;
	}

	protected void setIsModifiedToken(String isModifiedToken) {
		this.isModifiedToken = isModifiedToken;
	}

	protected String getIsModifiedUrl() {
		return isModifiedUrl;
	}

	protected void setIsModifiedUrl(String isModifiedUrl) {
		this.isModifiedUrl = isModifiedUrl;
	}

	protected String getNextPageToken() {
		return nextPageToken;
	}

	protected void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	protected String getNextPageUrl() {
		return nextPageUrl;
	}

	protected void setNextPageUrl(String nextPageUrl) {
		this.nextPageUrl = nextPageUrl;
	}

	protected String getUpdatesToken() {
		return updatesToken;
	}

	protected void setUpdatesToken(String updatesToken) {
		this.updatesToken = updatesToken;
	}

	protected String getUpdatesUrl() {
		return updatesUrl;
	}

	protected void setUpdatesUrl(String updatesUrl) {
		this.updatesUrl = updatesUrl;
	}

	public BaseFeedElement[] getElements() {
		return elements;
	}

	public void setElements(BaseFeedElement[] elements) {
		this.elements = elements;
	}
}
