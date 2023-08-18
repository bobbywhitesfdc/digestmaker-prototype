package bobby.sfdc.prototype;

import bobby.sfdc.prototype.json.BaseFeedElement;
import bobby.sfdc.prototype.json.Motif;

public class Scheme1Key extends SectionIndexKey {

	public Scheme1Key(BaseFeedElement element, String digestUserId) {
		super(element, digestUserId);
	}

	@Override
	/**
	 * In this scheme, we are forcing this order by FeedElement ParentType:
	 * 
	 * Groups:  (all groups)
	 * User:  (all users)
	 * Records: (all records)
	 * (????) : (recommendations?)
	 * 
	 * There's no consideration of other factors.
	 */
	protected String calculateSectionKey(BaseFeedElement element) {
		int priority;
		String typeName = element.getParent() != null ? element.getParent().getType() : "";
		
		if (typeName.compareTo("CollaborationGroup")==0) {
			priority=1;
		} else if (typeName.compareTo("User")==0) {
			priority=2;
		} else if (!typeName.isEmpty()) {
			priority=3;
		} else {
			priority=4;
		}
		
		return ""+priority+"."+typeName;
	}

	@Override
	/**
	 * Used to name the Sections in the digests.
	 * In our approach, the name is derived from the Feed Parent's type
	 */
	protected String calculateSectionLabel(BaseFeedElement element) {
		String type = element.getParent().getType();
		if (type.compareTo("CollaborationGroup")==0) {
			return "My Groups";
		} else if (type.compareTo("User")==0) {
			return "People I Follow";
		} else {
			return type + "s";	// in the production version, we'd get the plural label, faking it for now		
		}
	}

	@Override
	/**
	 * Our section motif is based on the parent object type
	 */
	protected Motif calculateSectionMotif(BaseFeedElement element) {
		return element.getParent().getMotif();
	}

	@Override
	/**
	 * For this simple scheme, just use the Type name as the SubSection key
	 */
	protected String calculateSubSectionKey(BaseFeedElement element) {
		return element.getParent() != null ? element.getParent().getType() : "";
	}

}
