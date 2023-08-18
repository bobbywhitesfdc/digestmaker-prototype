package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_following_counts.htm
 * 
 * @author bobby.white
 *
 */
public class FollowingCounts {
   int people;
   int records;
   int total;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("FollowingCounts [people=");
	builder.append(people);
	builder.append(", records=");
	builder.append(records);
	builder.append(", total=");
	builder.append(total);
	builder.append("]");
	return builder.toString();
}
public int getPeople() {
	return people;
}
public int getRecords() {
	return records;
}
public int getTotal() {
	return total;
}
}
