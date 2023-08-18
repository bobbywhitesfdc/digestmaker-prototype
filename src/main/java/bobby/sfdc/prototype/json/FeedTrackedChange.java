package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_tracked_change.htm
 * @author bobby.white
 *
 */
public class FeedTrackedChange {
   String fieldName;
   String newValue;
   String oldValue;
public String getFieldName() {
	return fieldName;
}
public String getNewValue() {
	return newValue;
}

public String getOldValue() {
	return oldValue;
}

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("FeedTrackedChange [fieldName=");
	builder.append(fieldName);
	builder.append(", newValue=");
	builder.append(newValue);
	builder.append(", oldValue=");
	builder.append(oldValue);
	builder.append("]");
	return builder.toString();
}
}
