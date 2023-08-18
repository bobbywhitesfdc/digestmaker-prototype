package bobby.sfdc.prototype;

import bobby.sfdc.prototype.json.BaseFeedElement;

/**
 * Encapulates the logic of managing the start and end of sections, and subsections.
 * The ordering of content and the categorization is completely controlled by the Index class:  SectionKey.
 * 
 * 
 * @author bobby.white
 *
 */
public class SectionBasedRenderer{
	protected String sectionKey;
	protected String subSectionKey;
	protected final IRenderingEngine worker;
	private boolean digestOpen;
	
	public SectionBasedRenderer(IRenderingEngine worker) {
	     this.worker=worker;
	     this.sectionKey="";
	     this.subSectionKey="";
	     this.digestOpen=false;
	}

	private String getSectionKey() {
		return sectionKey;
	}

	private void setSectionKey(String sectionKey) {
		this.sectionKey = sectionKey;
	}

	private String getSubSectionKey() {
		return subSectionKey;
	}

	private void setSubSectionKey(String subSectionKey) {
		this.subSectionKey = subSectionKey;
	}

	/**
	 * This method triggers all of the rendering by managing the transition of keys
	 * @param key
	 */
	public void setNewKey(SectionIndexKey key, BaseFeedElement currentItem) {
		
		worker.setItem(key,currentItem);
		
		if (!isDigestOpen()) {
			openDigest();
		}
		
		if (key.getSectionKey().compareTo(this.getSectionKey())!=0) { // Detected Change in Section

			if (isSectionOpen()) {
				worker.addComment("Change of Section");
				closeSection();
			}
			
			setSectionKey(key.getSectionKey());
			openSection();
			
		}
		
		if (key.getSubSectionKey().compareTo(this.getSubSectionKey())!=0) { // Detected Change in Subsection
			if (isSubSectionOpen()) {
				worker.addComment("Change of sub-section detected");
				closeSubSection();
			}
			
			setSubSectionKey(key.getSubSectionKey());
			openSubSection();
		}
		
		worker.renderDetail();
		
		// always update the "last seen keys" so we can detect future section & sub-section changes
		this.setSectionKey(key.getSectionKey());
		this.setSubSectionKey(key.getSubSectionKey());

		
	}


	private boolean isDigestOpen() {
		return this.digestOpen;
	}

	private void openSubSection() {
		worker.renderBeginSubSection();
	}

	private void closeSection() {
		if (isSubSectionOpen()) {
			worker.addComment("force Sub-section close by closeSection()");
			closeSubSection();
		}
		worker.renderEndOfSection();
		this.setSectionKey("");
	}

	private boolean isSectionOpen() {
		return this.sectionKey!=null && !this.sectionKey.isEmpty();
	}

	private boolean isSubSectionOpen() {
		return this.subSectionKey!=null && !this.subSectionKey.isEmpty();
	}

	private void closeSubSection() {
		worker.renderEndOfSubSection();
		this.setSubSectionKey("");
	}
	
	public void closeDigest() {
		worker.addComment("closeDigest()");
		this.closeSection();
		worker.renderEndDigestFeedContent();
		worker.renderEndDocument();
		this.digestOpen=false;
	}
	
	public void openDigest() {
		this.digestOpen=true;
		worker.addComment("openDigest()");
		worker.renderBeginDocument();
		worker.renderBeginDigestFeedContent();
	}
	
	private void openSection() {
		worker.addComment("openSection()");
		worker.renderBeginSection();
	}
	
}
