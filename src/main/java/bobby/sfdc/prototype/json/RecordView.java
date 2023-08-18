package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordView.htm
 * 
 * @author bobby.white
 *
 */
public class RecordView {
  String id;
  Motif motif;
  Reference mySubscription;
  String name;
  String recordViewUrl;
  String type;
  String url;
  ListViewDefinition[] relatedListDefinitions;
  RecordViewSection[] sections;
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("RecordView [id=");
	builder.append(id);
	builder.append(", motif=");
	builder.append(motif);
	builder.append(", mySubscription=");
	builder.append(mySubscription);
	builder.append(", name=");
	builder.append(name);
	builder.append(", recordViewUrl=");
	builder.append(recordViewUrl);
	builder.append(", type=");
	builder.append(type);
	builder.append(", url=");
	builder.append(url);
	builder.append(", relatedListDefinitions=");
	builder.append(relatedListDefinitions != null ? Arrays.asList(
			relatedListDefinitions).subList(0,
			Math.min(relatedListDefinitions.length, maxLen)) : null);
	builder.append(", sections=");
	builder.append(sections != null ? Arrays.asList(sections).subList(0,
			Math.min(sections.length, maxLen)) : null);
	builder.append("]");
	return builder.toString();
}
}
