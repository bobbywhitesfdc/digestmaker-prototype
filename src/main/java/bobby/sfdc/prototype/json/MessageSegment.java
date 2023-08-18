package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * Concrete Parent Type "Message Segment"
 * has many child types, for simplicity of deserialization, modeling this as a Union of all of them
 * 
 * Union Type:
 * Text             http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_text.htm
 * Mention          http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_mention.htm
 * HashTag          http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_hashtag.htm
 * Link             http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_link.htm
 * EntityLink       http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_msg_entity_link.htm
 * FieldChange      http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_field_change.htm
 * FieldChangeName  http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_field_change_name.htm
 * FieldChangeValue http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_field_change_value.htm
 * MoreChanges      http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_more.htm
 * ResourceLink     http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_ms_resource.htm

 * 
 * @author bobby.white
 *
 */
public class MessageSegment {
	// Base attributes common to all subtypes
	String type;
	String text;
	
	// Added by Mention 
	boolean accessible;
	String name;
	BaseSummary record; // union of Group, User, UserSummary
	UserSummary user;
	
	// Added by Link (also used by ResourceLink)
	String url;
	
	// Added by Hashtag
	String tag;
	String topicUrl;
	
	// Added by EntityLink
	Motif motif;
	Reference reference;
	
	// Added by Message Segment
	MessageSegment[] segments; // Using the Union Type
	
	
	public String getType() {
		return type;
	}
	public String getText() {
		return text;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public String getName() {
		return name;
	}
	public BaseSummary getRecord() {
		return record;
	}
	public UserSummary getUser() {
		return user;
	}
	public String getUrl() {
		return url;
	}
	public String getTag() {
		return tag;
	}
	public String getTopicUrl() {
		return topicUrl;
	}
	@Override
	public String toString() {
		final int maxLen = 50;
		StringBuilder builder = new StringBuilder();
		builder.append("MessageSegment [");
		if (type != null) {
			builder.append("type=");
			builder.append(type);
			builder.append(", ");
		}
		if (text != null) {
			builder.append("text=");
			builder.append(text);
			builder.append(", ");
		}
		builder.append("accessible=");
		builder.append(accessible);
		builder.append(", ");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (record != null) {
			builder.append("record=");
			builder.append(record);
			builder.append(", ");
		}
		if (user != null) {
			builder.append("user=");
			builder.append(user);
			builder.append(", ");
		}
		if (url != null) {
			builder.append("url=");
			builder.append(url);
			builder.append(", ");
		}
		if (tag != null) {
			builder.append("tag=");
			builder.append(tag);
			builder.append(", ");
		}
		if (topicUrl != null) {
			builder.append("topicUrl=");
			builder.append(topicUrl);
			builder.append(", ");
		}
		if (motif != null) {
			builder.append("motif=");
			builder.append(motif);
			builder.append(", ");
		}
		if (reference != null) {
			builder.append("reference=");
			builder.append(reference);
			builder.append(", ");
		}
		if (segments != null) {
			builder.append("segments=");
			builder.append(Arrays.asList(segments).subList(0,
					Math.min(segments.length, maxLen)));
		}
		builder.append("]");
		return builder.toString();
	}
	public Motif getMotif() {
		return motif;
	}
	public Reference getReference() {
		return reference;
	}
	public MessageSegment[] getSegments() {
		return segments;
	}
	
}
