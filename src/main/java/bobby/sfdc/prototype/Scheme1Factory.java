package bobby.sfdc.prototype;

import java.util.Map;
import java.util.TreeMap;

import bobby.sfdc.prototype.json.BaseFeedElement;

public class Scheme1Factory implements IDigestSectionIndexKeyFactory {

	private static final String STRATEGY = "Group Posts first, WallPosts second, Record Posts third.  This algorithm is pretty simple, it doesn't consider following or any other subtlety";
	private String digestUserId;

	public Scheme1Factory() {
	}

	@Override
	public Map<SectionIndexKey, BaseFeedElement> createMap() {
		return new TreeMap<SectionIndexKey, BaseFeedElement>();
	}

	@Override
	public SectionIndexKey createKeyFromElement(BaseFeedElement element) {
		if (element != null) {
			return new Scheme1Key(element,this.digestUserId);
		}
		return null;
	}

	@Override
	public String describeStrategy() {
		return STRATEGY;
	}

	@Override
	public void destroy() {
	}

	@Override
	public String getSchemeName() {
		return "Scheme1-Group by Parent 'Kind'";
	}

	@Override
	public void initialize(String digestUserId) {
		this.digestUserId=digestUserId;
	}

}
