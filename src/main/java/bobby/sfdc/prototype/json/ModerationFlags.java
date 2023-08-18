package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_moderation_flags.htm
 * @author bobby.white
 *
 */
public class ModerationFlags {
   int flagCount;
   boolean flaggedByMe;
   
	public int getFlagCount() {
		return flagCount;
	}
	public void setFlagCount(int flagCount) {
		this.flagCount = flagCount;
	}
	public boolean isFlaggedByMe() {
		return flaggedByMe;
	}
	public void setFlaggedByMe(boolean flaggedByMe) {
		this.flaggedByMe = flaggedByMe;
	}
	@Override
	public String toString() {
		return "ModerationFlags [flagCount=" + flagCount + ", flaggedByMe="
				+ flaggedByMe + "]";
	}
   
}
