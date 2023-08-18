package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_moderation_capability.htm
 * 
 * @author bobby.white
 *
 */
public class ModerationCapability {
  ModerationFlags moderationFlags;

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ModerationCapability [moderationFlags=");
	builder.append(moderationFlags);
	builder.append("]");
	return builder.toString();
}
}
