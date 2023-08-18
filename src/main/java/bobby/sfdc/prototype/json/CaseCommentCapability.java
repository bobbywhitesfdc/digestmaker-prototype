package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_case_comment_capability.htm#connect_responses_case_comment_capability
 * 
 * @author bobby.white
 *
 */
public class CaseCommentCapability {
	String actorType;
	UserSummary createdBy;
	String createdDate;
	String eventType;
	String id;
	boolean published;
	String text;
	protected String getActorType() {
		return actorType;
	}
	protected void setActorType(String actorType) {
		this.actorType = actorType;
	}
	protected UserSummary getCreatedBy() {
		return createdBy;
	}
	protected void setCreatedBy(UserSummary createdBy) {
		this.createdBy = createdBy;
	}
	protected String getCreatedDate() {
		return createdDate;
	}
	protected void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	protected String getEventType() {
		return eventType;
	}
	protected void setEventType(String eventType) {
		this.eventType = eventType;
	}
	protected String getId() {
		return id;
	}
	protected void setId(String id) {
		this.id = id;
	}
	protected boolean isPublished() {
		return published;
	}
	protected void setPublished(boolean published) {
		this.published = published;
	}
	protected String getText() {
		return text;
	}
	protected void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CaseCommentCapability [actorType=");
		builder.append(actorType);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", eventType=");
		builder.append(eventType);
		builder.append(", id=");
		builder.append(id);
		builder.append(", published=");
		builder.append(published);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
