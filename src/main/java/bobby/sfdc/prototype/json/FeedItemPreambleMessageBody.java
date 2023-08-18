package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_item_preamble.htm
 * @author bobby.white
 *
 */
public class FeedItemPreambleMessageBody {
   MessageSegment[] messageSegments;
   String text;
public MessageSegment[] getMessageSegments() {
	return messageSegments;
}
public void setMessageSegments(MessageSegment[] messageSegments) {
	this.messageSegments = messageSegments;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("FeedItemPreambleMessageBody [");
	if (messageSegments != null) {
		builder.append("messageSegments=");
		builder.append(Arrays.asList(messageSegments).subList(0,
				Math.min(messageSegments.length, maxLen)));
		builder.append(", ");
	}
	if (text != null) {
		builder.append("text=");
		builder.append(text);
	}
	builder.append("]");
	return builder.toString();
}
}
