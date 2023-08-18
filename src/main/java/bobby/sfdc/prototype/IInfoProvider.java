package bobby.sfdc.prototype;

import bobby.sfdc.prototype.json.ContentCapability;
import bobby.sfdc.soql.Info;

public interface IInfoProvider {

	/**
	 * Get Extra Context for this Object
	 * @param id the object for which to get context
	 * @return Info state or null if not found
	 */
	public abstract Info getInfoForId(String id);
	public abstract String getLabelForType(String type);
	public abstract String getCacheDir();
	public abstract void fetchAndStore(ContentCapability content, String expectedCacheFileName);

}