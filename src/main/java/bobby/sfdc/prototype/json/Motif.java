package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_motif.htm
 * 
 * @author bobby.white
 *
 */
public class Motif {
    String color;
    String largeIconUrl;
    String mediumIconUrl;
    String smallIconUrl;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getLargeIconUrl() {
		return largeIconUrl;
	}
	public void setLargeIconUrl(String largeIconUrl) {
		this.largeIconUrl = largeIconUrl;
	}
	public String getMediumIconUrl() {
		return mediumIconUrl;
	}
	public void setMediumIconUrl(String mediumIconUrl) {
		this.mediumIconUrl = mediumIconUrl;
	}
	public String getSmallIconUrl() {
		return smallIconUrl;
	}
	public void setSmallIconUrl(String smallIconUrl) {
		this.smallIconUrl = smallIconUrl;
	}
	@Override
	public String toString() {
		return "Motif [color=" + color + ", largeIconUrl=" + largeIconUrl
				+ ", mediumIconUrl=" + mediumIconUrl + ", smallIconUrl="
				+ smallIconUrl + "]";
	}
}
