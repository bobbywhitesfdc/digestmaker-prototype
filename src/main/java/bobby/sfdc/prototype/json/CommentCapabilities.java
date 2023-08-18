package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_comments_capability.htm
 * 
 * @author bobby.white
 *
 */
public class CommentCapabilities {
   CommentPage page;

public CommentPage getPage() {
	return page;
}

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("CommentCapabilities [");
	if (page != null) {
		builder.append("page=");
		builder.append(page);
	}
	builder.append("]");
	return builder.toString();
}
}
