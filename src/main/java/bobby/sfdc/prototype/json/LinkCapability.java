package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_link_capability.htm#connect_responses_link_capability
 * 
 * @author bobby.white
 *
 */
public class LinkCapability {
   String url;
   String urlName;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("LinkCapability [");
	if (url != null) {
		builder.append("url=");
		builder.append(url);
		builder.append(", ");
	}
	if (urlName != null) {
		builder.append("urlName=");
		builder.append(urlName);
	}
	builder.append("]");
	return builder.toString();
}
public String getUrl() {
	return url;
}
public String getUrlName() {
	return urlName;
}
}
