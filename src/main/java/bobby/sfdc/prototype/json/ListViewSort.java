package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_relatedListSort.htm
 * 
 * @author bobby.white
 *
 */
public class ListViewSort {
   String column;
   String order;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ListViewSort [column=");
	builder.append(column);
	builder.append(", order=");
	builder.append(order);
	builder.append("]");
	return builder.toString();
}
}
