package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_pictures.htm
 * 
 * @author bobby.white
 *
 */
public class Photo {
  String fullEmailPhotoUrl;
  String largePhotoUrl;
  String photoVersionId;
  String smallPhotoUrl;
  String standardEmailPhotoUrl;
  String url;
public String getFullEmailPhotoUrl() {
	return fullEmailPhotoUrl;
}
public void setFullEmailPhotoUrl(String fullEmailPhotoUrl) {
	this.fullEmailPhotoUrl = fullEmailPhotoUrl;
}
public String getLargePhotoUrl() {
	return largePhotoUrl;
}
public void setLargePhotoUrl(String largePhotoUrl) {
	this.largePhotoUrl = largePhotoUrl;
}
public String getPhotoVersionId() {
	return photoVersionId;
}
public void setPhotoVersionId(String photoVersionId) {
	this.photoVersionId = photoVersionId;
}
public String getSmallPhotoUrl() {
	return smallPhotoUrl;
}
public void setSmallPhotoUrl(String smallPhotoUrl) {
	this.smallPhotoUrl = smallPhotoUrl;
}
public String getStandardEmailPhotoUrl() {
	return standardEmailPhotoUrl;
}
public void setStandardEmailPhotoUrl(String standardEmailPhotoUrl) {
	this.standardEmailPhotoUrl = standardEmailPhotoUrl;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
@Override
public String toString() {
	return "Photo [fullEmailPhotoUrl=" + fullEmailPhotoUrl + ", largePhotoUrl="
			+ largePhotoUrl + ", photoVersionId=" + photoVersionId
			+ ", smallPhotoUrl=" + smallPhotoUrl + ", standardEmailPhotoUrl="
			+ standardEmailPhotoUrl + ", url=" + url + "]";
}
  
}
