package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ContentHubRepositoryFeatures.htm
 * @author bobby.white
 *
 */
public class FilesConnectRepositoryFeatures {
  boolean canBrowse;
  boolean canSearch;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("FilesConnectRepositoryFeatures [canBrowse=");
	builder.append(canBrowse);
	builder.append(", canSearch=");
	builder.append(canSearch);
	builder.append("]");
	return builder.toString();
}
}
