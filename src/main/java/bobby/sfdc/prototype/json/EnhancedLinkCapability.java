package bobby.sfdc.prototype.json;
/**
 * Used by Rypple (Work.com Thanks posts)
 * 
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_enhanced_link_capability.htm#connect_responses_enhanced_link_capability
 * @author bobby.white
 *
 */
public class EnhancedLinkCapability {
 String description;
 Icon icon;
 String linkRecordId;
 String linkUrl;
 String title;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("EnhancedLinkCapability [description=");
	builder.append(description);
	builder.append(", icon=");
	builder.append(icon);
	builder.append(", linkRecordId=");
	builder.append(linkRecordId);
	builder.append(", linkUrl=");
	builder.append(linkUrl);
	builder.append(", title=");
	builder.append(title);
	builder.append("]");
	return builder.toString();
}
/**
 * @return the description
 */
public String getDescription() {
	return description;
}
/**
 * @return the icon
 */
public Icon getIcon() {
	return icon;
}
/**
 * @return the linkRecordId
 */
public String getLinkRecordId() {
	return linkRecordId;
}
/**
 * @return the linkUrl
 */
public String getLinkUrl() {
	return linkUrl;
}
/**
 * @return the title
 */
public String getTitle() {
	return title;
}
}
