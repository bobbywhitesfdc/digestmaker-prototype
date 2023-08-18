/**
 * 
 */
package bobby.sfdc.prototype;

import java.util.Map;
import java.util.TreeMap;

import bobby.sfdc.prototype.json.BaseFeedElement;

/**
 * @author bobby.white
 *
 */
public class Scheme3Factory implements IDigestSectionIndexKeyFactory {

	protected final String digestUserId;

	/**
	 * Constructor with with minimum set of parameters
	 */
	public Scheme3Factory(String userId) {
		digestUserId = userId;
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
		return new Scheme3Key(element,digestUserId);
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#getSchemeName()
	 */
	@Override
	public String getSchemeName() {
		return "Scheme3 - @Me, Groups, Following, Recommendations";
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#describeStrategy()
	 */
	@Override
	public String describeStrategy() {
		StringBuffer info=new StringBuffer();
		info.append("Section #1 Contains Posts that are on my wall or where I'm specifically @Mentioned\n");
		info.append("Section #2 Contains Groups that I belong to, own, or manage.  Groups are ordered alphabetically within\n");
		info.append("Section #3 Things that I Follow, other than Groups. Rank within is:  Files, Records, Users\n");
		info.append("Section #4 Recommendations.\n");
		return info.toString();
		
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#initialize(java.lang.String)
	 */
	@Override
	public void initialize(String digestUserId) {
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.IDigestSectionIndexKeyFactory#destroy()
	 */
	@Override
	public void destroy() {
	}

}
