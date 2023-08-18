/**
 * 
 */
package bobby.sfdc.prototype;

import bobby.sfdc.prototype.json.BaseFeedElement;
import bobby.sfdc.prototype.json.Comment;
import bobby.sfdc.prototype.json.MessageSegment;
import bobby.sfdc.prototype.json.Motif;

/**
 * @author bobby.white
 *
 */
public class Scheme3Key extends SectionIndexKey {


	private boolean isUserMentioned=false;
	private boolean isRecommendation=false;
	private SchemeSectionRank rank;
	private boolean isMyGroup;
	
	/**
	 * Let the super drive initialization
	 * 
	 * @param element
	 */
	public Scheme3Key(BaseFeedElement element, String digestUserId) {
		super(element,digestUserId);
	}
	
	/**
	 * Canonical ranks of sections
	 * @author bobby.white
	 *
	 */
	protected enum SchemeSectionRank {
		ATME("To Me"),
		MYGROUPS("My Groups"),
		FOLLOWING("What I Follow"),
		RECOMMENDATIONS("Recommendations");
		
		private String label;
		
		SchemeSectionRank(String label) {
			this.label = label;
		}
		@Override
		/* Returns a value that FORCES sorting rank  and describes the Section */
		public String toString() {
			return this.ordinal() + ":" + this.name();
		}
		/* A friendly label suitable for use in the digest */
		public String getLabel() {
			return this.label;
		}
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.SectionIndexKey#categorize(bobby.sfdc.prototype.json.BaseFeedElement)
	 */
	@Override
	protected String calculateSectionKey(BaseFeedElement element) {
		this.isRecommendation = isRecommendation(element);
		this.isMyGroup = isMyGroup(element);
		this.isUserMentioned = isUserMentioned(element);

		if (isRecommendation) {
			rank = SchemeSectionRank.RECOMMENDATIONS;
		} else if (isUserMentioned) {
			rank = SchemeSectionRank.ATME;
		} else if (isMyGroup) {
			rank = SchemeSectionRank.MYGROUPS;
		} else {
			rank = SchemeSectionRank.FOLLOWING;
		}
		return rank.toString();
	}
	
	/**
	 * Parent Object is a Collaboration Group and I am a Member, Manager, or Owner.
	 * @param element
	 * @return
	 */
	private boolean isMyGroup(BaseFeedElement element) {
		if (element.getParent() != null 
				&& element.getParent().isGroup()
				&& element.getParent().getMySubscription() != null) {
			return true;
			
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.SectionIndexKey#calculateSectionLabel(bobby.sfdc.prototype.json.BaseFeedElement)
	 */
	@Override
	protected String calculateSectionLabel(BaseFeedElement element) {
		return rank.getLabel();
	}


	private boolean isRecommendation(BaseFeedElement element) {
		return element.getType() == null 
				&& element.getCapabilities().getRecommendations() != null
				&& element.getCapabilities().getRecommendations().getItems() != null;
	}

	/**
	 * is this user mentioned on the Post or a Comment
	 * 
	 * @param element
	 * @return
	 */
	private boolean isUserMentioned(BaseFeedElement element) {
		// On my wall!
		if (isOnMyWall(element)) {
			return true;
		}
		// Mentioned on the Original Post
		if (isUserMentioned(element.getBody().getMessageSegments())) {
			return true;
		}
		// Mentioned on one of the Comments
		if (element.getCapabilities().getComments() != null
				&& element.getCapabilities().getComments().getPage()!= null 
				&& element.getCapabilities().getComments().getPage().getItems()!=null) {
			for (Comment comment :element.getCapabilities().getComments().getPage().getItems() ) {
				if (isUserMentioned(comment.getBody().getMessageSegments())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Is this Feed Post on the Digest User's Wall?
	 * @param element
	 * @return
	 */
	private boolean isOnMyWall(BaseFeedElement element) {
		return digestUserId != null 
				&& element.getParent() != null 
				&& element.getParent().getId() != null 
				&& element.getParent().getId().compareTo(this.digestUserId)==0;
	}

	/**
	 * Is the Digest User directly @mentioned on a post or comment
	 * @param segments
	 * @return true if they are directly mentioned
	 */
	private boolean isUserMentioned(MessageSegment[] segments) {
		for(MessageSegment segment : segments ) {
			if (segment.getType().compareTo("Mention")==0 
					&& (segment.getRecord() != null) 
					&& segment.getRecord().getId().compareTo(this.digestUserId)==0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Are we Following this item in the Feed?
	 * 
	 * One criteria that we'll use:
	 * a) Following this Record/User/Group/File
	 * 
	 * @param element
	 * @return
	 */
	protected boolean isFollowing(BaseFeedElement element) {
		boolean isFollowing = element.getParent().getMySubscription() !=null;
		return  isFollowing;
	}

	/* (non-Javadoc)
	 * @see bobby.sfdc.prototype.SectionIndexKey#calculateSectionMotif(bobby.sfdc.prototype.json.BaseFeedElement)
	 */
	@Override
	protected Motif calculateSectionMotif(BaseFeedElement element) {
		return null;
	}

	@Override
	/**
	 * Rank by type like this:   Groups,  Files, (and Record Type), User, Other
	 */
	protected String calculateSubSectionKey(BaseFeedElement element) {
		String typeName = element.getParent() != null ? element.getParent().getType() : "";
		if (element.getParent()==null) {
			return "9:<no name>";
		} else if (element.getParent() != null && element.getParent().isFile()) {
			return "1:File:"+ element.getParent().getName();
		} else if (element.getParent() != null && element.getParent().isGroup()) {
			return "2:Group:"+ element.getParent().getName();
		} else if (typeName.compareTo("User")==0) {
			return "5:User:"+ element.getParent().getName();
		} else {
			return "3:" + element.getParent().getType() + ":" + element.getParent().getName();
		}
	}

}
