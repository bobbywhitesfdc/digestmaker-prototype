package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_influence.htm
 * @author bobby.white
 *
 */
public class ChatterInfluence {
	String percentile;
	int rank;
	public String getPercentile() {
		return percentile;
	}
	public int getRank() {
		return rank;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChatterInfluence [");
		if (percentile != null) {
			builder.append("percentile=");
			builder.append(percentile);
			builder.append(", ");
		}
		builder.append("rank=");
		builder.append(rank);
		builder.append("]");
		return builder.toString();
	}
}
