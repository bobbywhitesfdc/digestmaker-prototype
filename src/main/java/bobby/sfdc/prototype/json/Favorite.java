package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_favorite.htm
 * 
 * @author bobby.white
 *
 */
public class Favorite {
   Reference community;
   UserSummary createdBy;
   String feedUrl;
   String id;
   String lastViewDate;
   String name;
   String searchText;
   Reference target;
   String type;
   String url;
   UserSummary user;
/**
 * @return the community
 */
public Reference getCommunity() {
	return community;
}
/**
 * @return the createdBy
 */
public UserSummary getCreatedBy() {
	return createdBy;
}
/**
 * @return the feedUrl
 */
public String getFeedUrl() {
	return feedUrl;
}
/**
 * @return the id
 */
public String getId() {
	return id;
}
/**
 * @return the lastViewDate
 */
public String getLastViewDate() {
	return lastViewDate;
}
/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @return the searchText
 */
public String getSearchText() {
	return searchText;
}
/**
 * @return the target
 */
public Reference getTarget() {
	return target;
}
/**
 * @return the type
 */
public String getType() {
	return type;
}
/**
 * @return the url
 */
public String getUrl() {
	return url;
}
/**
 * @return the user
 */
public UserSummary getUser() {
	return user;
}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Favorite [");
	if (community != null) {
		builder.append("community=");
		builder.append(community);
		builder.append(", ");
	}
	if (createdBy != null) {
		builder.append("createdBy=");
		builder.append(createdBy);
		builder.append(", ");
	}
	if (feedUrl != null) {
		builder.append("feedUrl=");
		builder.append(feedUrl);
		builder.append(", ");
	}
	if (id != null) {
		builder.append("id=");
		builder.append(id);
		builder.append(", ");
	}
	if (lastViewDate != null) {
		builder.append("lastViewDate=");
		builder.append(lastViewDate);
		builder.append(", ");
	}
	if (name != null) {
		builder.append("name=");
		builder.append(name);
		builder.append(", ");
	}
	if (searchText != null) {
		builder.append("searchText=");
		builder.append(searchText);
		builder.append(", ");
	}
	if (target != null) {
		builder.append("target=");
		builder.append(target);
		builder.append(", ");
	}
	if (type != null) {
		builder.append("type=");
		builder.append(type);
		builder.append(", ");
	}
	if (url != null) {
		builder.append("url=");
		builder.append(url);
		builder.append(", ");
	}
	if (user != null) {
		builder.append("user=");
		builder.append(user);
	}
	builder.append("]");
	return builder.toString();
}
}
