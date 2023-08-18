package bobby.sfdc.prototype;

import bobby.sfdc.prototype.json.BaseFeedElement;
import bobby.sfdc.prototype.json.Motif;

/**
 * Magic comparator used to evaluate a FeedItem and place it into a 3 level hierarchy.
 * Level 1 = Section  (has an arbitrary header label)
 * Level 2 = Subsection (has a header for parent info)
 * Level 3 = Detail  (has all of the feed details & capabilities)
 * 
 * @author bobby.white
 *
 */
public abstract class SectionIndexKey implements Comparable<SectionIndexKey> {
	private final String _sectionKey;
	private final String _subSectionKey;
	private final String _detailKey;
	private final String _extraSubDetailKey;
	private final String _sectionLabel;
	private final Motif _sectionMotif;
	protected String digestUserId;
	
	SectionIndexKey(BaseFeedElement element, String digestUserId) {
		this.digestUserId=digestUserId;
		
		_sectionKey = calculateSectionKey(element);
		_sectionLabel = calculateSectionLabel(element);
		_sectionMotif =  this.calculateSectionMotif(element);
		
		_subSectionKey = calculateSubSectionKey(element);
		
		_detailKey = element.getModifiedDate();
		_extraSubDetailKey = element.getId();
	}


	/**
	 * This method examines the FeedElement and decides how to categorize it by (section, subsection, detail) hierarchy.
	 * 
	 * Need to force arbitrary precedence of sections based on the "kind" or weight of the parent object
	 * 
	 * Intent here is to bubble force this ordering:   Groups, Users, Records
	 * Don't want to introduce another level of hierarchy to do that, so we'll create a compound key
	 * 
	 * @param element
	 * @return a synthetic key to be used as the SectionKey
	 */
	abstract protected String calculateSectionKey(BaseFeedElement element);
	
	/**
	 * Determine the "user friendly label" for the section
	 * @param element
	 * @return Determine the "user friendly label" for the section
	 */
	abstract protected String calculateSectionLabel(BaseFeedElement element);
	/**
	 * Determine which Motif (icon) will represent this section
	 * @param element
	 * @return
	 */
	abstract protected Motif calculateSectionMotif(BaseFeedElement element);
	
	abstract protected String calculateSubSectionKey(BaseFeedElement element);
	
	/**
	 * Return the section label for this element
	 * @return
	 */
	public final String getSectionLabel() {
		return this._sectionLabel;
	}
	
	/**
	 * Return the motif (icon image) that will be used for the section
	 * @return
	 */
	public final Motif getSectionMotif() {
		return this._sectionMotif;
	}


	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_extraSubDetailKey == null) ? 0 : _extraSubDetailKey.hashCode());
		result = prime
				* result
				+ ((_detailKey == null) ? 0 : _detailKey
						.hashCode());
		result = prime
				* result
				+ ((_subSectionKey == null) ? 0 : _subSectionKey
						.hashCode());
		result = prime * result
				+ ((_sectionKey == null) ? 0 : _sectionKey.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectionIndexKey other = (SectionIndexKey) obj;

		if (_extraSubDetailKey == null) {
			if (other._extraSubDetailKey != null)
				return false;
		} else if (!_extraSubDetailKey.equals(other._extraSubDetailKey))
			return false;
		if (_detailKey == null) {
			if (other._detailKey != null)
				return false;
		} else if (!_detailKey.equals(other._detailKey))
			return false;
		if (_subSectionKey == null) {
			if (other._subSectionKey != null)
				return false;
		} else if (!_subSectionKey.equals(other._subSectionKey))
			return false;
		if (_sectionKey == null) {
			if (other._sectionKey != null)
				return false;
		} else if (!_sectionKey.equals(other._sectionKey))
			return false;
		return true;
	}

	@Override
	public final int compareTo(SectionIndexKey o) {
	    final int EQUAL = 0;
	    
	    if (this.equals(o)) return EQUAL;
	    
	    int key1Cmp = this._sectionKey.compareTo(o._sectionKey);
	    
	    if (key1Cmp != EQUAL) return key1Cmp;
	    
	    int key2Cmp = this._subSectionKey.compareTo(o._subSectionKey);
	    if (key2Cmp != EQUAL) return key2Cmp;
	    
	    int key3Cmp = this._detailKey.compareTo(o._detailKey) *-1; //Invert this so that it's in Descending order of this key (mod_ts)
	    if (key3Cmp != EQUAL) return key3Cmp;
	    
	    // Final Tiebreaker
	    int key4Cmp = this._extraSubDetailKey.compareTo(o._extraSubDetailKey); 
	     

		return key4Cmp;
	}

	public final String getSectionKey() {
		return _sectionKey;
	}

	public final String getSubSectionKey() {
		return _subSectionKey;
	}

	public final String getDetailKey() {
		return _detailKey;
	}
}
