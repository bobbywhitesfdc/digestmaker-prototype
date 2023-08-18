package bobby.sfdc.prototype.json;

import java.util.Arrays;


/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_action_link_group.htm
 * @author bobby.white
 *
 */
public class PlatformActionGroup {
    String category;
    String id;
    String modifiedDate;
    PlatformAction[] platformActions;
    String url;
	public String getCategory() {
		return category;
	}
	public String getId() {
		return id;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public PlatformAction[] getPlatformActions() {
		return platformActions;
	}
	public String getUrl() {
		return url;
	}
	@Override
	public String toString() {
		final int maxLen = 50;
		StringBuilder builder = new StringBuilder();
		builder.append("PlatformActionGroup [");
		if (category != null) {
			builder.append("category=");
			builder.append(category);
			builder.append(", ");
		}
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (modifiedDate != null) {
			builder.append("modifiedDate=");
			builder.append(modifiedDate);
			builder.append(", ");
		}
		if (platformActions != null) {
			builder.append("platformActions=");
			builder.append(Arrays.asList(platformActions).subList(0,
					Math.min(platformActions.length, maxLen)));
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
		}
		builder.append("]");
		return builder.toString();
	}
}
