package bobby.sfdc.prototype;

import java.util.Map;

import bobby.sfdc.prototype.json.BaseFeedElement;

/**
 * Handles the creation of the Index and Keys
 * @author bobby.white
 *
 */
public interface IDigestSectionIndexKeyFactory {
	/**
	 * Create the empty map that will be consumed by the DigestRendering Engine
	 * @return
	 */
	public Map<SectionIndexKey,BaseFeedElement> createMap();
	/**
	 * Instantiate the SectionIndexKey from the FeedElement.
	 * 
	 * @param element  the element to consider
	 * @return null if the element is to be discarded
	 */
	public SectionIndexKey createKeyFromElement(BaseFeedElement element);
	/**
	 * A short name (< 40 characters) for this scheme.  Intended to be used as a Subject line in email.
	 * @return
	 */
	public String getSchemeName();
	/**
	 * Describe in English prose, the strategy of this indexing scheme.
	 * 
	 * @return  A plain english text description of the strategy
	 */
	public String describeStrategy();
	/**
	 * Initialize the factory, guaranteed to be called once
	 */
	public void initialize(String digestUserId);
	/**
	 * Destroy the factory, an opportunity to close connections,etc.
	 * Guaranteed to be called once.
	 */
	public void destroy();
	
}
