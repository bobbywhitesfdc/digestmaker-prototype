package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_icon.htm#connect_responses_icon
 * @author bobby.white
 *
 */
public class Icon {
  int height;
  int width;
  String url;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Icon [height=");
	builder.append(height);
	builder.append(", width=");
	builder.append(width);
	builder.append(", url=");
	builder.append(url);
	builder.append("]");
	return builder.toString();
}
public String getUrl() {
	return url;
}
/**
 * @return the height
 */
public int getHeight() {
	return height;
}
/**
 * @return the width
 */
public int getWidth() {
	return width;
}
}
