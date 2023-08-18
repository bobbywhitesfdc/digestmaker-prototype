package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * UNION of several types:
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordField.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordFieldBlank.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordFieldCompound.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordFieldCurrency.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordFieldDate.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordFieldPercent.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordFieldPicklist.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordFieldReference.htm
 * 
 * @author bobby.white
 *
 */
public class RecordFieldBase {
	// Least common denominator, RecordField
   String label;
   String text;
   String type;
   // Date field defines these
   String dateValue;
   // Percentage defines the Value as a Double
   Double value;
   // Reference field defines the value as a reference type
   Reference reference;
   
   // Compound defines nested fields
   RecordFieldBase[] fields;

public String getLabel() {
	return label;
}

public String getText() {
	return text;
}

public String getType() {
	return type;
}

public String getDateValue() {
	return dateValue;
}

public Double getValue() {
	return value;
}

public Reference getReference() {
	return reference;
}

public RecordFieldBase[] getFields() {
	return fields;
}

@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("RecordFieldBase [");
	if (label != null) {
		builder.append("label=");
		builder.append(label);
		builder.append(", ");
	}
	if (text != null) {
		builder.append("text=");
		builder.append(text);
		builder.append(", ");
	}
	if (type != null) {
		builder.append("type=");
		builder.append(type);
		builder.append(", ");
	}
	if (dateValue != null) {
		builder.append("dateValue=");
		builder.append(dateValue);
		builder.append(", ");
	}
	if (value != null) {
		builder.append("value=");
		builder.append(value);
		builder.append(", ");
	}
	if (reference != null) {
		builder.append("reference=");
		builder.append(reference);
		builder.append(", ");
	}
	if (fields != null) {
		builder.append("fields=");
		builder.append(Arrays.asList(fields).subList(0,
				Math.min(fields.length, maxLen)));
	}
	builder.append("]");
	return builder.toString();
}
   
}
