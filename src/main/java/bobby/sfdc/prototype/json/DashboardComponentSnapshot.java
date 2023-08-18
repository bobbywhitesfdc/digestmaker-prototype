package bobby.sfdc.prototype.json;


/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_dashboard_component_snapshot.htm#connect_responses_dashboard_component_snapshot
 * @author bobby.white
 *
 */
public class DashboardComponentSnapshot {
   String componentId;
   String componentName;
   String dashboardBodyText;
   String dashboardId;
   String dashboardName;
   String fullSizeImageUrl;
   String lastRefreshDate;
   String lastRefreshDateDisplayText;
   UserSummary runningUser;
   String thumbnailUrl;
protected String getComponentId() {
	return componentId;
}
protected void setComponentId(String componentId) {
	this.componentId = componentId;
}
protected String getComponentName() {
	return componentName;
}
protected void setComponentName(String componentName) {
	this.componentName = componentName;
}
protected String getDashboardBodyText() {
	return dashboardBodyText;
}
protected void setDashboardBodyText(String dashboardBodyText) {
	this.dashboardBodyText = dashboardBodyText;
}
protected String getDashboardId() {
	return dashboardId;
}
protected void setDashboardId(String dashboardId) {
	this.dashboardId = dashboardId;
}
protected String getDashboardName() {
	return dashboardName;
}
protected void setDashboardName(String dashboardName) {
	this.dashboardName = dashboardName;
}
protected String getFullSizeImageUrl() {
	return fullSizeImageUrl;
}
protected void setFullSizeImageUrl(String fullSizeImageUrl) {
	this.fullSizeImageUrl = fullSizeImageUrl;
}
protected String getLastRefreshDate() {
	return lastRefreshDate;
}
protected void setLastRefreshDate(String lastRefreshDate) {
	this.lastRefreshDate = lastRefreshDate;
}
protected String getLastRefreshDateDisplayText() {
	return lastRefreshDateDisplayText;
}
protected void setLastRefreshDateDisplayText(String lastRefreshDateDisplayText) {
	this.lastRefreshDateDisplayText = lastRefreshDateDisplayText;
}
protected UserSummary getRunningUser() {
	return runningUser;
}
protected void setRunningUser(UserSummary runningUser) {
	this.runningUser = runningUser;
}
protected String getThumbnailUrl() {
	return thumbnailUrl;
}
protected void setThumbnailUrl(String thumbnailUrl) {
	this.thumbnailUrl = thumbnailUrl;
}
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("DashboardComponentSnapshot [componentId=");
	builder.append(componentId);
	builder.append(", componentName=");
	builder.append(componentName);
	builder.append(", dashboardBodyText=");
	builder.append(dashboardBodyText);
	builder.append(", dashboardId=");
	builder.append(dashboardId);
	builder.append(", dashboardName=");
	builder.append(dashboardName);
	builder.append(", fullSizeImageUrl=");
	builder.append(fullSizeImageUrl);
	builder.append(", lastRefreshDate=");
	builder.append(lastRefreshDate);
	builder.append(", lastRefreshDateDisplayText=");
	builder.append(lastRefreshDateDisplayText);
	builder.append(", runningUser=");
	builder.append(runningUser);
	builder.append(", thumbnailUrl=");
	builder.append(thumbnailUrl);
	builder.append("]");
	return builder.toString();
}
}
