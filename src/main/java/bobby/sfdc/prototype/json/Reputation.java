package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_reputation.htm
 * @author bobby.white
 *
 */
public class Reputation {
    ReputationLevel reputationLevel;
    Double reputationPoints;
    String url;
	public ReputationLevel getReputationLevel() {
		return reputationLevel;
	}
	public void setReputationLevel(ReputationLevel reputationLevel) {
		this.reputationLevel = reputationLevel;
	}
	public Double getReputationPoints() {
		return reputationPoints;
	}
	public void setReputationPoints(Double reputationPoints) {
		this.reputationPoints = reputationPoints;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Reputation [reputationLevel=" + reputationLevel
				+ ", reputationPoints=" + reputationPoints + ", url=" + url
				+ "]";
	}
}
