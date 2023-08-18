package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordViewSection.htm
 * 
 * @author bobby.white
 *
 */
public class RecordViewSection {
  String columnCount;
  String columnOrder;
  RecordFieldBase[] fields;  // RecordField, RecordField:Blank, RecordField:Compound, RecordField:Currency, RecordField:Date, RecordField:Date, RecordField:Percent, Picklist, Reference,  Reference with Date
  String heading;
  boolean isCollapsible;
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("RecordViewSection [");
	if (columnCount != null) {
		builder.append("columnCount=");
		builder.append(columnCount);
		builder.append(", ");
	}
	if (columnOrder != null) {
		builder.append("columnOrder=");
		builder.append(columnOrder);
		builder.append(", ");
	}
	if (fields != null) {
		builder.append("fields=");
		builder.append(Arrays.asList(fields).subList(0,
				Math.min(fields.length, maxLen)));
		builder.append(", ");
	}
	if (heading != null) {
		builder.append("heading=");
		builder.append(heading);
		builder.append(", ");
	}
	builder.append("isCollapsible=");
	builder.append(isCollapsible);
	builder.append("]");
	return builder.toString();
}
public String getColumnCount() {
	return columnCount;
}
public String getColumnOrder() {
	return columnOrder;
}
public RecordFieldBase[] getFields() {
	return fields;
}
public String getHeading() {
	return heading;
}
public boolean isCollapsible() {
	return isCollapsible;
}
}
