package bobby.sfdc.prototype.json;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.lang.reflect.Type;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Custom deserializer required to handle the mixed typed collection member called "elements".
 * 
 * @author bobby.white
 *
 */
public class FeedElementPageDeserializer implements JsonDeserializer<FeedElementPage>{
	private static  Logger logger = Logger.getLogger(FeedElementPageDeserializer.class.getName());

	@Override
	public FeedElementPage deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) {
		FeedElementPage fepage = new FeedElementPage();
		JsonObject pageObj = (JsonObject)json;
		
		// Must manually parse the individual fields
		fepage.setCurrentPageUrl(getString(pageObj,"currentPageUrl"));
		fepage.setIsModifiedToken(getString(pageObj,"isModifiedToken"));
		fepage.setIsModifiedUrl(getString(pageObj,"isModifiedUrl"));
		fepage.setNextPageToken(getString(pageObj,"nextPageToken"));
		fepage.setNextPageUrl(getString(pageObj,"nextPageUrl"));
		fepage.setUpdatesToken(getString(pageObj,"updatesToken"));
		fepage.setUpdatesUrl(getString(pageObj,"updatesUrl"));
		
		// Handle the elements member which has a mixed array
		JsonArray rawArray = (JsonArray) pageObj.get("elements");
		
		logger.info("Element count="+ rawArray.size());
		
		List<BaseFeedElement> elementList = new ArrayList<>();
		
		for (JsonElement thisElement : rawArray) {
			String type = getString(thisElement,"type"); // Effectively this field is a "selector" that indicates which subtype we should see
			logger.info("Element type="+type);
			
			if (type==null) {
				//TODO handle this case
				
			} else if ("\"TextPost\"".compareToIgnoreCase(type)==0) {
				// deserialize to a FeedItem of type "TextPost"
				BaseFeedElement feedItem = context.deserialize(thisElement, FeedItem.class);
				elementList.add(feedItem);
				
			} else if ("\"CollaborationGroupCreated\"".compareToIgnoreCase(type)==0) {
				//TODO handle this case
			}
		}
		
		// Save the List we've built as an array
		BaseFeedElement[] bfeArray = new BaseFeedElement[elementList.size()];
		elementList.toArray(bfeArray);
		fepage.setElements(bfeArray);
		
		
		return fepage;
	}

	private String getString(JsonElement thisElement, String fieldName) {
		return getString((JsonObject)thisElement,fieldName);
	}

	private String getString(JsonObject pageObj, String fieldName) {
		return pageObj.get(fieldName) != null ? pageObj.get(fieldName).toString() : null;
	}


}
