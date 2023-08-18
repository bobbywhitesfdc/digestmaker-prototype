package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_relatedListColumn.htm
 * 
 * @author bobby.white
 *
 */
public class ListViewColumn {
  String dataType;
  String fieldId;
  String label;
  String name;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ListViewColumn [dataType=");
	builder.append(dataType);
	builder.append(", fieldId=");
	builder.append(fieldId);
	builder.append(", label=");
	builder.append(label);
	builder.append(", name=");
	builder.append(name);
	builder.append("]");
	return builder.toString();
}
}
