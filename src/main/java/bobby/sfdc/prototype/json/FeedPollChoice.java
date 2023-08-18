package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_poll.htm#connect_responses_feed_poll
 * @author bobby.white
 *
 */
public class FeedPollChoice {
   String id;
   int postion;
   String text;
   int voteCount;
   Double voteCountRatio;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("FeedPollChoice [id=");
	builder.append(id);
	builder.append(", postion=");
	builder.append(postion);
	builder.append(", text=");
	builder.append(text);
	builder.append(", voteCount=");
	builder.append(voteCount);
	builder.append(", voteCountRatio=");
	builder.append(voteCountRatio);
	builder.append("]");
	return builder.toString();
}
}
