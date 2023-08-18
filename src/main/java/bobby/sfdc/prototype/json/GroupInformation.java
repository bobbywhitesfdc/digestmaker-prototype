package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_group_information.htm
 * 
 * @author bobby.white
 *
 */

public class GroupInformation {
	String text;
	String title;
	public String getText() {
		return text;
	}
	public String getTitle() {
		return title;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupInformation [");
		if (text != null) {
			builder.append("text=");
			builder.append(text);
			builder.append(", ");
		}
		if (title != null) {
			builder.append("title=");
			builder.append(title);
		}
		builder.append("]");
		return builder.toString();
	}
}
