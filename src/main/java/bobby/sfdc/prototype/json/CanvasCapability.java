package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_canvas_capability.htm#connect_responses_canvas_capability
 * @author bobby.white
 *
 */
public class CanvasCapability {
   String description;
   String developerName;
   String height;
   ConnectApiIcon icon;
   String namespacePrefix;
   String parameters;
   String thumbnailUrl;
   String title;
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("CanvasCapability [");
	if (description != null) {
		builder.append("description=");
		builder.append(description);
		builder.append(", ");
	}
	if (developerName != null) {
		builder.append("developerName=");
		builder.append(developerName);
		builder.append(", ");
	}
	if (height != null) {
		builder.append("height=");
		builder.append(height);
		builder.append(", ");
	}
	if (icon != null) {
		builder.append("icon=");
		builder.append(icon);
		builder.append(", ");
	}
	if (namespacePrefix != null) {
		builder.append("namespacePrefix=");
		builder.append(namespacePrefix);
		builder.append(", ");
	}
	if (parameters != null) {
		builder.append("parameters=");
		builder.append(parameters);
		builder.append(", ");
	}
	if (thumbnailUrl != null) {
		builder.append("thumbnailUrl=");
		builder.append(thumbnailUrl);
		builder.append(", ");
	}
	if (title != null) {
		builder.append("title=");
		builder.append(title);
	}
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
 * @return the developerName
 */
public String getDeveloperName() {
	return developerName;
}
/**
 * @return the height
 */
public String getHeight() {
	return height;
}
/**
 * @return the icon
 */
public ConnectApiIcon getIcon() {
	return icon;
}
/**
 * @return the namespacePrefix
 */
public String getNamespacePrefix() {
	return namespacePrefix;
}
/**
 * @return the parameters
 */
public String getParameters() {
	return parameters;
}
/**
 * @return the thumbnailUrl
 */
public String getThumbnailUrl() {
	return thumbnailUrl;
}
/**
 * @return the title
 */
public String getTitle() {
	return title;
}
}
