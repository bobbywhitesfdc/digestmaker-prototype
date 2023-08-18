package bobby.sfdc.prototype.json;

/**
 * Defined by
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_reference.htm
 * 
 * @author bobby.white
 *
 */
public class Reference {
	String id;
	String url;
	@Override
	public String toString() {
		return "Reference [id=" + id + ", url=" + url + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
