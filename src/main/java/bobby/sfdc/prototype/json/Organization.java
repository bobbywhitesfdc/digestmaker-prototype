package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_organization.htm
 * 
 * @author bobby.white
 *
 */
public class Organization {
	int accessTimeout;
	String name;
	String orgId;
	//Future fields that might be interesting:  Features,  UserSettings
	/**
	 * @return the accessTimeout
	 */
	public int getAccessTimeout() {
		return accessTimeout;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Organization [accessTimeout=");
		builder.append(accessTimeout);
		builder.append(", ");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (orgId != null) {
			builder.append("orgId=");
			builder.append(orgId);
		}
		builder.append("]");
		return builder.toString();
	}
}
