package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_explanation_summary.htm
 * 
 * @author bobby.white
 *
 */
public class ExplanationSummary {
    String detailsUrl;
    String summary;
    String type;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExplanationSummary [detailsUrl=");
		builder.append(detailsUrl);
		builder.append(", summary=");
		builder.append(summary);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}
	public String getSummary() {
		return summary;
	}
	public String getDetailsUrl() {
		return detailsUrl;
	}
	public String getType() {
		return type;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
