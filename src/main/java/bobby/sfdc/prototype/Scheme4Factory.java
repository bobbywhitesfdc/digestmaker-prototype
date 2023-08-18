/**
 * 
 */
package bobby.sfdc.prototype;

import org.joda.time.DateTime;

import bobby.sfdc.prototype.json.BaseFeedElement;

/**
 * Builds on Scheme3 introducing MRU into ordering
 * @author bobby.white
 *
 */
public class Scheme4Factory extends Scheme3Factory {

	/** 
	 * Used to calculate ordering
	 **/
	public static DateTime mruReferenceDate;


	/**
	 * Constructor takes DigestUser in order to compute @mention impact
	 * 
	 * @param userId
	 */
	public Scheme4Factory(String userId) {
		super(userId);
	}


	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.Scheme3Factory#createKeyFromElement(bobby.sfdc.prototype.json.BaseFeedElement)
	 */
	@Override
	public SectionIndexKey createKeyFromElement(BaseFeedElement element) {
		return new Scheme4Key(element, this.digestUserId);
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.Scheme3Factory#getSchemeName()
	 */
	@Override
	public String getSchemeName() {
		return "Scheme4 - @Me, Groups, Following, Recommendations all in MRU order";
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.Scheme3Factory#describeStrategy()
	 */
	@Override
	public String describeStrategy() {
		StringBuffer info=new StringBuffer();
		info.append("\nSection #1 Contains Posts that are on my wall or where I'm specifically @Mentioned\n");
		info.append("Section #2 Contains Groups that I belong to, own, or manage.  Groups are ordered by MRU\n");
		info.append("Section #3 Things that I Follow. Rank within is:   MRU newest to oldest\n");
		info.append("Section #4 Recommendations.\n\n");
		return info.toString();
	}


	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.Scheme3Factory#initialize(java.lang.String)
	 */
	@Override
	public void initialize(String digestUserId) {
		super.initialize(digestUserId);
		mruReferenceDate = new DateTime(System.currentTimeMillis());
	}

}
