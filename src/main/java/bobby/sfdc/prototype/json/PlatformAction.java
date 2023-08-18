package bobby.sfdc.prototype.json;


/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_action_link.htm
 * @author bobby.white
 *
 */
public class PlatformAction {
   String actionUrl;
   String apiName;
   String confirmationMessage;
   UserSummary executingUser;
   boolean groupDefault;
   String iconUrl;
   String id;
   String label;
   String modifiedDate;
   Reference platformActionGroup;
   String status;
   String subtype;
   String type;
   String url;
protected String getActionUrl() {
	return actionUrl;
}
protected void setActionUrl(String actionUrl) {
	this.actionUrl = actionUrl;
}
protected String getApiName() {
	return apiName;
}
protected void setApiName(String apiName) {
	this.apiName = apiName;
}
protected String getConfirmationMessage() {
	return confirmationMessage;
}
protected void setConfirmationMessage(String confirmationMessage) {
	this.confirmationMessage = confirmationMessage;
}
protected UserSummary getExecutingUser() {
	return executingUser;
}
protected void setExecutingUser(UserSummary executingUser) {
	this.executingUser = executingUser;
}
protected boolean isGroupDefault() {
	return groupDefault;
}
protected void setGroupDefault(boolean groupDefault) {
	this.groupDefault = groupDefault;
}
protected String getIconUrl() {
	return iconUrl;
}
protected void setIconUrl(String iconUrl) {
	this.iconUrl = iconUrl;
}
protected String getId() {
	return id;
}
protected void setId(String id) {
	this.id = id;
}
protected String getLabel() {
	return label;
}
protected void setLabel(String label) {
	this.label = label;
}
protected String getModifiedDate() {
	return modifiedDate;
}
protected void setModifiedDate(String modifiedDate) {
	this.modifiedDate = modifiedDate;
}
protected Reference getPlatformActionGroup() {
	return platformActionGroup;
}
protected void setPlatformActionGroup(Reference platformActionGroup) {
	this.platformActionGroup = platformActionGroup;
}
protected String getStatus() {
	return status;
}
protected void setStatus(String status) {
	this.status = status;
}
protected String getSubtype() {
	return subtype;
}
protected void setSubtype(String subtype) {
	this.subtype = subtype;
}
protected String getType() {
	return type;
}
protected void setType(String type) {
	this.type = type;
}
protected String getUrl() {
	return url;
}
protected void setUrl(String url) {
	this.url = url;
}
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("PlatformAction [actionUrl=");
	builder.append(actionUrl);
	builder.append(", apiName=");
	builder.append(apiName);
	builder.append(", confirmationMessage=");
	builder.append(confirmationMessage);
	builder.append(", executingUser=");
	builder.append(executingUser);
	builder.append(", groupDefault=");
	builder.append(groupDefault);
	builder.append(", iconUrl=");
	builder.append(iconUrl);
	builder.append(", id=");
	builder.append(id);
	builder.append(", label=");
	builder.append(label);
	builder.append(", modifiedDate=");
	builder.append(modifiedDate);
	builder.append(", platformActionGroup=");
	builder.append(platformActionGroup);
	builder.append(", status=");
	builder.append(status);
	builder.append(", subtype=");
	builder.append(subtype);
	builder.append(", type=");
	builder.append(type);
	builder.append(", url=");
	builder.append(url);
	builder.append("]");
	return builder.toString();
}
}
