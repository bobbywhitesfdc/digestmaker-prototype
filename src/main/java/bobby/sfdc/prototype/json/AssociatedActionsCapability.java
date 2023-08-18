package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_associated_actions_capability.htm
 *
 * @author bobby.white
 *
 */
public class AssociatedActionsCapability {
  PlatformActionGroup[] platformActionGroups;

@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("AssociatedActionsCapability [platformActionGroups=");
	builder.append(platformActionGroups != null ? Arrays.asList(
			platformActionGroups).subList(0,
			Math.min(platformActionGroups.length, maxLen)) : null);
	builder.append("]");
	return builder.toString();
}
}
