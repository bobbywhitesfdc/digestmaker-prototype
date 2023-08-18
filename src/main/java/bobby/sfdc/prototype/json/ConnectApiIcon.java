package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_icon.htm#connect_responses_icon
 * @author bobby.white
 *
 */
public class ConnectApiIcon {
   int height;
   int width;
   String url;
protected int getHeight() {
	return height;
}
protected void setHeight(int height) {
	this.height = height;
}
protected int getWidth() {
	return width;
}
protected void setWidth(int width) {
	this.width = width;
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
	builder.append("ConnectApiIcon [height=");
	builder.append(height);
	builder.append(", width=");
	builder.append(width);
	builder.append(", url=");
	builder.append(url);
	builder.append("]");
	return builder.toString();
}
}
