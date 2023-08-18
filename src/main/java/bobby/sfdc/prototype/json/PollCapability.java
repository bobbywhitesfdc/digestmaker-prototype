package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_poll_capability.htm
 * @author bobby.white
 *
 */
public class PollCapability {
  FeedPollChoice[] choices;
  String myChoiceId;
  String totalVoteCount;
}
