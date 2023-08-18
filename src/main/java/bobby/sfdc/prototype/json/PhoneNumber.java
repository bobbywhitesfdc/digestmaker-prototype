package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_phone_number.htm
 * @author bobby.white
 *
 */
public class PhoneNumber {
	String label;
	String phoneNumber;
	String phoneType;
	public String getLabel() {
		return label;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getPhoneType() {
		return phoneType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PhoneNumber [");
		if (label != null) {
			builder.append("label=");
			builder.append(label);
			builder.append(", ");
		}
		if (phoneNumber != null) {
			builder.append("phoneNumber=");
			builder.append(phoneNumber);
			builder.append(", ");
		}
		if (phoneType != null) {
			builder.append("phoneType=");
			builder.append(phoneType);
		}
		builder.append("]");
		return builder.toString();
	}
}
