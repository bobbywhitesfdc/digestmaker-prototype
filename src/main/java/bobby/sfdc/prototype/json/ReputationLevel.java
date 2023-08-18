package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_reputation_level.htm
 * @author bobby.white
 *
 */
public class ReputationLevel {
	String levelImageUrl;
	String levelName;
	int levelNumber;
	public String getLevelImageUrl() {
		return levelImageUrl;
	}
	public void setLevelImageUrl(String levelImageUrl) {
		this.levelImageUrl = levelImageUrl;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public int getLevelNumber() {
		return levelNumber;
	}
	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	@Override
	public String toString() {
		return "ReputationLevel [levelImageUrl=" + levelImageUrl
				+ ", levelName=" + levelName + ", levelNumber=" + levelNumber
				+ "]";
	}
}
