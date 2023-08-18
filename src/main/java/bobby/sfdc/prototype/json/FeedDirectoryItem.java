package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_dir_item.htm
 * 
 * @author bobby.white
 *
 */
public class FeedDirectoryItem {
	protected String feedElementsUrl;
	protected String feedItemsUrl;
	protected String feedType;
	protected String feedUrl;
	protected String keyPrefix;
	protected String label;
	
	/**
	 * A copy constructor
	 * @param item
	 */
	public FeedDirectoryItem(FeedDirectoryItem item) {
		this.feedElementsUrl = item.feedElementsUrl;
		this.feedItemsUrl = item.feedItemsUrl;
		this.feedType = item.feedType;
		this.feedUrl = item.feedUrl;
		this.keyPrefix = item.keyPrefix;
		this.label = item.label;
	}
	/**
	 * @return the feedElementsUrl
	 */
	public String getFeedElementsUrl() {
		return feedElementsUrl;
	}
	/**
	 * @return the feedItemsUrl
	 */
	public String getFeedItemsUrl() {
		return feedItemsUrl;
	}
	/**
	 * @return the feedType
	 */
	public String getFeedType() {
		return feedType;
	}
	/**
	 * @return the feedUrl
	 */
	public String getFeedUrl() {
		return feedUrl;
	}
	/**
	 * @return the keyPrefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeedDirectoryItem [");
		if (feedElementsUrl != null) {
			builder.append("feedElementsUrl=");
			builder.append(feedElementsUrl);
			builder.append(", ");
		}
		if (feedItemsUrl != null) {
			builder.append("feedItemsUrl=");
			builder.append(feedItemsUrl);
			builder.append(", ");
		}
		if (feedType != null) {
			builder.append("feedType=");
			builder.append(feedType);
			builder.append(", ");
		}
		if (feedUrl != null) {
			builder.append("feedUrl=");
			builder.append(feedUrl);
			builder.append(", ");
		}
		if (keyPrefix != null) {
			builder.append("keyPrefix=");
			builder.append(keyPrefix);
			builder.append(", ");
		}
		if (label != null) {
			builder.append("label=");
			builder.append(label);
		}
		builder.append("]");
		return builder.toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((feedElementsUrl == null) ? 0 : feedElementsUrl.hashCode());
		result = prime * result
				+ ((feedItemsUrl == null) ? 0 : feedItemsUrl.hashCode());
		result = prime * result
				+ ((feedType == null) ? 0 : feedType.hashCode());
		result = prime * result + ((feedUrl == null) ? 0 : feedUrl.hashCode());
		result = prime * result
				+ ((keyPrefix == null) ? 0 : keyPrefix.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeedDirectoryItem other = (FeedDirectoryItem) obj;
		if (feedElementsUrl == null) {
			if (other.feedElementsUrl != null)
				return false;
		} else if (!feedElementsUrl.equals(other.feedElementsUrl))
			return false;
		if (feedItemsUrl == null) {
			if (other.feedItemsUrl != null)
				return false;
		} else if (!feedItemsUrl.equals(other.feedItemsUrl))
			return false;
		if (feedType == null) {
			if (other.feedType != null)
				return false;
		} else if (!feedType.equals(other.feedType))
			return false;
		if (feedUrl == null) {
			if (other.feedUrl != null)
				return false;
		} else if (!feedUrl.equals(other.feedUrl))
			return false;
		if (keyPrefix == null) {
			if (other.keyPrefix != null)
				return false;
		} else if (!keyPrefix.equals(other.keyPrefix))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
}
