package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_like.htm
 * 
 * @author bobby.white
 *
 */
public class Like {
	   String id;
	   Reference likedItem;
	   String url;
	   UserSummary user;

   @Override
	public String toString() {
		return "Like [id=" + id + ", likedItem=" + likedItem + ", url=" + url
				+ ", user=" + user + "]";
	}
public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Reference getLikedItem() {
		return likedItem;
	}
	public void setLikedItem(Reference likedItem) {
		this.likedItem = likedItem;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public UserSummary getUser() {
		return user;
	}
	public void setUser(UserSummary user) {
		this.user = user;
	}
}
