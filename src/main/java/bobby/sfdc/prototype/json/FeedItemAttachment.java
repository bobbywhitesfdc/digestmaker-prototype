package bobby.sfdc.prototype.json;
/**
 * Concrete Parent of FeedItemAttachment:xxx types
 * 
 * @author bobby.white
 *
 */
public class FeedItemAttachment {
   String type;

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

@Override
public String toString() {
	return "FeedItemAttachment [type=" + type + "]";
}
}
