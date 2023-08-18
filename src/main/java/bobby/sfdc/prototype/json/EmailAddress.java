package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_emailAddress.htm#connect_responses_emailAddress
 * @author bobby.white
 *
 */
public class EmailAddress {
    String displayName;
    String emailAddress;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailAddress [displayName=");
		builder.append(displayName);
		builder.append(", emailAddress=");
		builder.append(emailAddress);
		builder.append("]");
		return builder.toString();
	}
}
