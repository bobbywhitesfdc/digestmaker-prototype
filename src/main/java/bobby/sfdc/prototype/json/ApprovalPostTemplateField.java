package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_approval_post.htm
 * @author bobby.white
 *
 */
public class ApprovalPostTemplateField {
  String displayName;
  String displayValue;
  Reference record;
protected String getDisplayName() {
	return displayName;
}
protected void setDisplayName(String displayName) {
	this.displayName = displayName;
}
protected String getDisplayValue() {
	return displayValue;
}
protected void setDisplayValue(String displayValue) {
	this.displayValue = displayValue;
}
protected Reference getRecord() {
	return record;
}
protected void setRecord(Reference record) {
	this.record = record;
}
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ApprovalPostTemplateField [displayName=");
	builder.append(displayName);
	builder.append(", displayValue=");
	builder.append(displayValue);
	builder.append(", record=");
	builder.append(record);
	builder.append("]");
	return builder.toString();
}
}
