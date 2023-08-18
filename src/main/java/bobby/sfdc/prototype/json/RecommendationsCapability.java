package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recommendations_capability.htm#connect_responses_recommendations_capability
 * 
 * @author bobby.white
 *
 */
public class RecommendationsCapability {
  BaseRecommendation[] items; // Union of Recommendation and Non-Entity Recommendation

@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("RecommendationsCapability [");
	if (items != null) {
		builder.append("items=");
		builder.append(Arrays.asList(items).subList(0,
				Math.min(items.length, maxLen)));
	}
	builder.append("]");
	return builder.toString();
}

public BaseRecommendation[] getItems() {
	return items;
}
}
