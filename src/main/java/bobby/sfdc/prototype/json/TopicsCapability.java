package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_topics_capability.htm
 * @author bobby.white
 *
 */
public class TopicsCapability {
	boolean canAssignTopics;
	Topic[] items;
	@Override
	public String toString() {
		final int maxLen = 50;
		StringBuilder builder = new StringBuilder();
		builder.append("TopicsCapability [canAssignTopics=");
		builder.append(canAssignTopics);
		builder.append(", items=");
		builder.append(items != null ? Arrays.asList(items).subList(0,
				Math.min(items.length, maxLen)) : null);
		builder.append("]");
		return builder.toString();
	}
	/**
	 * @return the canAssignTopics
	 */
	public boolean isCanAssignTopics() {
		return canAssignTopics;
	}
	/**
	 * @return the items
	 */
	public Topic[] getItems() {
		return items;
	}
}
