package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * Need to make this the union of both raw capabilities
 * 
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_generic_clumps_capability.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_tracked_change_clumps_capability.htm
 * @author bobby.white
 *
 */
public class GenericBundleCapability {
   String bundleType;
   FeedElementPage page;
   int totalElements;
   FeedTrackedChange[] changes;
protected String getBundleType() {
	return bundleType;
}
protected void setBundleType(String bundleType) {
	this.bundleType = bundleType;
}
protected FeedElementPage getPage() {
	return page;
}
protected void setPage(FeedElementPage page) {
	this.page = page;
}
protected int getTotalElements() {
	return totalElements;
}
protected void setTotalElements(int totalElements) {
	this.totalElements = totalElements;
}
protected FeedTrackedChange[] getChanges() {
	return changes;
}
protected void setChanges(FeedTrackedChange[] changes) {
	this.changes = changes;
}
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("GenericBundleCapability [bundleType=");
	builder.append(bundleType);
	builder.append(", page=");
	builder.append(page);
	builder.append(", totalElements=");
	builder.append(totalElements);
	builder.append(", changes=");
	builder.append(changes != null ? Arrays.asList(changes).subList(0,
			Math.min(changes.length, maxLen)) : null);
	builder.append("]");
	return builder.toString();
}
}
