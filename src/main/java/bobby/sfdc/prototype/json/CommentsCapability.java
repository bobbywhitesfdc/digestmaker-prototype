package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_comments_capability.htm
 * @author bobby.white
 *
 */
public class CommentsCapability {
  CommentPage page;

public CommentPage getPage() {
	return page;
}

protected void setPage(CommentPage page) {
	this.page = page;
}

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("CommentsCapability [page=");
	builder.append(page);
	builder.append("]");
	return builder.toString();
}
}
