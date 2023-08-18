package bobby.sfdc.prototype.json;


/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_announcement.htm
 * 
 * @author bobby.white
 *
 */
public class Announcement {
  String expirationDate;
  FeedItem feedElement;
  String id;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Announcement [expirationDate=");
	builder.append(expirationDate);
	builder.append(", feedElement=");
	builder.append(feedElement);
	builder.append(", id=");
	builder.append(id);
	builder.append("]");
	return builder.toString();
}
/**
 * @return the expirationDate
 */
public String getExpirationDate() {
	return expirationDate;
}
/**
 * @return the feedElement
 */
public FeedItem getFeedElement() {
	return feedElement;
}
/**
 * @return the id
 */
public String getId() {
	return id;
}
}
