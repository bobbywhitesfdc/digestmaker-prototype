/**
 * 
 */
package bobby.sfdc.prototype;

import java.util.Map;
import java.util.TreeMap;

import bobby.sfdc.prototype.json.BaseFeedElement;

/**
 * This scheme considers Following (Group Membership or Record/User following),  At Mentions of the user
 * 
 * 
 * @author bobby.white
 *
 */
public class SchemeFactoryFollowingAndAtMe implements
		IDigestSectionIndexKeyFactory {

	private String digestUserId;

	/**
	 * 
	 */
	public SchemeFactoryFollowingAndAtMe() {
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#createMap()
	 */
	@Override
	public Map<SectionIndexKey, BaseFeedElement> createMap() {
		return new TreeMap<SectionIndexKey,BaseFeedElement>();
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#createKeyFromElement(bobby.sfdc.prototype.json.BaseFeedElement)
	 */
	@Override
	public SectionIndexKey createKeyFromElement(BaseFeedElement element) {
		return new FollowingAndAtMentionKey(element,this.digestUserId);
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#getSchemeName()
	 */
	@Override
	public String getSchemeName() {
		return "Scheme2-Following & @Me";
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#describeStrategy()
	 */
	@Override
	public String describeStrategy() {
		return "Priority #1 - @me,  #2 - Following Directly, #3 - Other stuff in my feed,   #4 - Recommendations";
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#destroy()
	 */
	@Override
	public void destroy() {
	}

	@Override
	public void initialize(String digestUserId) {
		this.digestUserId=digestUserId;
	}

}
