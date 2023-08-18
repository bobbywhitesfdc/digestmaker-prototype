package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_resources_users_UserProfileInformation.htm
 * Resource: /connect/communities/{communityId}/chatter/users/{userId}
 * Resource: /chatter/users/{userId}
 * @author bobby.white
 *
 */
public class UserProfile {
	//UserCapabilities capabilities;
	//UserProfileTabs tabs;
	String url;
	String id;
	UserSummary userDetail;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserProfile [");
		if (url != null) {
			builder.append("url=");
			builder.append(url);
			builder.append(", ");
		}
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (userDetail != null) {
			builder.append("userDetail=");
			builder.append(userDetail);
		}
		builder.append("]");
		return builder.toString();
	}
	public String getUrl() {
		return url;
	}
	public String getId() {
		return id;
	}
	public UserSummary getUserDetail() {
		return userDetail;
	}
	public void setSummary(UserSummary summary) {
		this.userDetail = summary;	
	}
	public boolean isInternal() {
		return this.userDetail != null ? !(this.userDetail.getUserType().compareTo("Portal")==0) : false;
	}

}
