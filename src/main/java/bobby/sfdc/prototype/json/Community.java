package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_community.htm
 * 
 * @author bobby.white
 *
 */
public class Community {
	String id;
	String name;
	String description;
	boolean allowChatter​AccessWithoutLogin;
	boolean allowMembers​ToFlag;
	boolean invitations​Enabled;
	boolean knowledgeable​Enabled;
	boolean nicknameDisplay​Enabled;
	boolean privateMessages​Enabled;
	boolean reputationEnabled;
	boolean sendWelcome​Email;
	String siteUrl;
	String status;
	String urlPathPrefix;
	String url;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the allowChatter​AccessWithoutLogin
	 */
	public boolean isAllowChatter​AccessWithoutLogin() {
		return allowChatter​AccessWithoutLogin;
	}
	/**
	 * @return the allowMembers​ToFlag
	 */
	public boolean isAllowMembers​ToFlag() {
		return allowMembers​ToFlag;
	}
	/**
	 * @return the invitations​Enabled
	 */
	public boolean isInvitations​Enabled() {
		return invitations​Enabled;
	}
	/**
	 * @return the knowledgeable​Enabled
	 */
	public boolean isKnowledgeable​Enabled() {
		return knowledgeable​Enabled;
	}
	/**
	 * @return the nicknameDisplay​Enabled
	 */
	public boolean isNicknameDisplay​Enabled() {
		return nicknameDisplay​Enabled;
	}
	/**
	 * @return the privateMessages​Enabled
	 */
	public boolean isPrivateMessages​Enabled() {
		return privateMessages​Enabled;
	}
	/**
	 * @return the reputationEnabled
	 */
	public boolean isReputationEnabled() {
		return reputationEnabled;
	}
	/**
	 * @return the sendWelcome​Email
	 */
	public boolean isSendWelcome​Email() {
		return sendWelcome​Email;
	}
	/**
	 * @return the siteUrl
	 */
	public String getSiteUrl() {
		return siteUrl;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Derived getter that looks at the Status and determines safely if it's live or not
	 */
	public boolean isActive() {
		return status != null && status.compareTo("Live")==0;
	}
	/**
	 * @return the urlPathPrefix
	 */
	public String getUrlPathPrefix() {
		return urlPathPrefix;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Community [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (description != null) {
			builder.append("description=");
			builder.append(description);
			builder.append(", ");
		}
		if (siteUrl != null) {
			builder.append("siteUrl=");
			builder.append(siteUrl);
			builder.append(", ");
		}
		if (urlPathPrefix != null) {
			builder.append("urlPathPrefix=");
			builder.append(urlPathPrefix);
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
		}
		builder.append("]");
		return builder.toString();
	}
}
