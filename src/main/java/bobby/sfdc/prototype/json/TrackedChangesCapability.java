package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_tracked_changes_capability.htm#connect_responses_tracked_changes_capability
 * @author bobby.white
 *
 */
public class TrackedChangesCapability {
   FeedTrackedChange[] changes;

@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("TrackedChangesCapability [changes=");
	builder.append(changes != null ? Arrays.asList(changes).subList(0,
			Math.min(changes.length, maxLen)) : null);
	builder.append("]");
	return builder.toString();
}

public FeedTrackedChange[] getChanges() {
	return changes;
}
}
