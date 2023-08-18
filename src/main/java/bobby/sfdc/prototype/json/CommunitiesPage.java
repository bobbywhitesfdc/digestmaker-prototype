package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_community_page.htm
 * 
 * @author bobby.white
 *
 */
public class CommunitiesPage {
	Community[] communities;
	int total;
	/**
	 * @return the communities
	 */
	public Community[] getCommunities() {
		return communities;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 50;
		StringBuilder builder = new StringBuilder();
		builder.append("CommunitiesPage [");
		if (communities != null) {
			builder.append("communities=");
			builder.append(Arrays.asList(communities).subList(0,
					Math.min(communities.length, maxLen)));
			builder.append(", ");
		}
		builder.append("total=");
		builder.append(total);
		builder.append("]");
		return builder.toString();
	}
}
