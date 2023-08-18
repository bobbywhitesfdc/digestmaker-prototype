package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_record_snapshot_capability.htm#connect_responses_record_snapshot_capability
 * 
 * @author bobby.white
 *
 */
public class RecordSnapshotCapability {
   RecordView recordView;

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("RecordSnapshotCapability [recordView=");
	builder.append(recordView);
	builder.append("]");
	return builder.toString();
}
}
