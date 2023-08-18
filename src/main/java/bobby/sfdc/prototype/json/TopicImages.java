package bobby.sfdc.prototype.json;

/**
 * Defined by
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_topic_images.htm
 * 
 * @author bobby.white
 *
 */
public class TopicImages {
	String coverImageUrl;
	String featuredItemUrl;
	@Override
	public String toString() {
		return "TopicImages [coverImageUrl=" + coverImageUrl
				+ ", featuredItemUrl=" + featuredItemUrl + "]";
	}
	public String getCoverImageUrl() {
		return coverImageUrl;
	}
	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}
	public String getFeaturedItemUrl() {
		return featuredItemUrl;
	}
	public void setFeaturedItemUrl(String featuredItemUrl) {
		this.featuredItemUrl = featuredItemUrl;
	}
}
