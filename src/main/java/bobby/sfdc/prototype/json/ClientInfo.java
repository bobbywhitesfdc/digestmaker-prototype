package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_client_info.htm
 * 
 * @author bobby.white
 *
 */
public class ClientInfo {
   String applicationName;
   String applicationUrl;
public String getApplicationName() {
	return applicationName;
}
public void setApplicationName(String applicationName) {
	this.applicationName = applicationName;
}
public String getApplicationUrl() {
	return applicationUrl;
}
public void setApplicationUrl(String applicationUrl) {
	this.applicationUrl = applicationUrl;
}
@Override
public String toString() {
	return "ClientInfo [applicationName=" + applicationName
			+ ", applicationUrl=" + applicationUrl + "]";
}
   
}
