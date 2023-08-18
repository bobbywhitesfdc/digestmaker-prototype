package bobby.sfdc.prototype;

import bobby.sfdc.prototype.json.BaseFeedElement;

public interface IRenderingEngine {

	public abstract void init(IInfoProvider provider);
	
	public abstract void setItem(SectionIndexKey key, BaseFeedElement currentItem);
	
	public abstract void addComment(String comment);
	
	/**
	 * An opportunity to render a salutation
	 */
	public abstract void renderBeginDocument();
	
	/**
	 * An opportunity to render a closing summary or footer
	 */
	public abstract void renderEndDocument();
	
	public abstract void renderBeginDigestFeedContent();

	public abstract void renderEndDigestFeedContent();

	public abstract void renderBeginSection();

	public abstract void renderEndOfSection();

	public abstract void renderBeginSubSection();

	public abstract void renderEndOfSubSection();

	public abstract void renderDetail();
	
	public abstract void setFooterText(String footer);
	
	public abstract void setSalutationText(String salutation);

}