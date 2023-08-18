package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_relatedListObject.htm
 * 
 * @author bobby.white
 *
 */
public class ListViewObject {
  String fieldId;
  String type;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ListViewObject [fieldId=");
	builder.append(fieldId);
	builder.append(", type=");
	builder.append(type);
	builder.append("]");
	return builder.toString();
}
}
