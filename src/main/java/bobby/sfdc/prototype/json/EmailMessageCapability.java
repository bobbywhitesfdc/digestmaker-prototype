package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_email_message_capability.htm#connect_responses_email_message_capability
 * @author bobby.white
 *
 */
public class EmailMessageCapability {
   String direction;
   String emailMessageId;
   String subject;
   String textBody;
   EmailAddress[] toAddresses;
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("EmailMessageCapability [direction=");
	builder.append(direction);
	builder.append(", emailMessageId=");
	builder.append(emailMessageId);
	builder.append(", subject=");
	builder.append(subject);
	builder.append(", textBody=");
	builder.append(textBody);
	builder.append(", toAddresses=");
	builder.append(toAddresses != null ? Arrays.asList(toAddresses).subList(0,
			Math.min(toAddresses.length, maxLen)) : null);
	builder.append("]");
	return builder.toString();
}
}
