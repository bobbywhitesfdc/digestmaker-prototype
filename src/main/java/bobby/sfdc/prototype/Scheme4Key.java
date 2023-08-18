/**
 * 
 */
package bobby.sfdc.prototype;

import org.joda.time.DateTime;

import bobby.sfdc.prototype.json.BaseFeedElement;

/**
 * Builds in Scheme3 but overrides SubSection Ordering to take MRU data into account
 * @author bobby.white
 *
 */
public class Scheme4Key extends Scheme3Key {

	private static final String DUMMY_ORDINAL = "00000000000000000";

	/**
	 * @param element
	 * @param digestUserId
	 * @param mruReferenceDate used to order by MRU
	 */
	public Scheme4Key(BaseFeedElement element, String digestUserId) {
		super(element, digestUserId);
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.Scheme3Key#calculateSubSectionKey(bobby.sfdc.prototype.json.BaseFeedElement)
	 */
	@Override
	protected String calculateSubSectionKey(BaseFeedElement element) {
		String mruValue = getDescendingMRUValue(element);
		if (element.getParent()==null) {
			return "9:Recommendation:" + DUMMY_ORDINAL;
		} else	{	
			return "1:" + mruValue + element.getParent().getId();
		}
	}

	/**
	 * Safely calculate a value that can give MRU in descending order.
	 * Handles unsafe scenarios by returning an empty value
	 * @param element the FeedElement to consider
	 * @return an ordinal which forces Most Recent to Least Recent viewing order, otherwise empty
	 */
	private String getDescendingMRUValue(BaseFeedElement element) {
		String ordinal=DUMMY_ORDINAL;
		
		String mruValue="";
		
		if (element.getParent() != null && element.getParent().getMruDate() !=null) {
			mruValue = element.getParent().getMruDate();
			if (mruValue != null && !mruValue.isEmpty()) {
				DateTime mruDate = DigestMaker.convertToDateTime(mruValue);
				// Pad with Leading Zeros to force numerical ordering
				ordinal = (DUMMY_ORDINAL + (Scheme4Factory.mruReferenceDate.getMillis() - mruDate.getMillis()));
				ordinal = ordinal.substring(ordinal.length()-DUMMY_ORDINAL.length());  
			}
		}
		return ordinal;
	}

}
