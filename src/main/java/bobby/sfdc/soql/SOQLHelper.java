package bobby.sfdc.soql;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileNotFoundException;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Manually parses the Json resultsets returned from SOQL queries and places into an Info wrapper object
 * @author bobby.white
 *
 */
public class SOQLHelper {
	
	private static Logger _logger = Logger.getLogger(SOQLHelper.class.getName());

	public static void main(String[] args) {
		
		/**
		String responseBody= "{\"totalSize\":3,\"done\":true,\"records\":[{\"attributes\":{\"type\":\"Case\",\"url\""
		+":\"/services/data/v20.0/sobjects/Case/500i0000001P0rGAAS\"},\"Id\":\"500i0000001P0rGAAS\",\"CaseNumber\":\"00001002\""
		+",\"Subject\":\"Seeking guidance on electrical wiring installation for GC5060\",\"Status\":\"New\"},{\"attributes\":{\"type\":\"Case\",\"url\":\"/services/data/v20.0/sobjects/Case/500i0000007TBdKAAW\"},\"Id\":\"500i0000007TBdKAAW\",\"CaseNumber\":\"00001026\",\"Subject\":\"Test Case\",\"Status\":\"New\"},{\"attributes\":{\"type\":\"Case\",\"url\":\"/services/data/v20.0/sobjects/Case/500i000000E1NwQAAV\"},\"Id\":\"500i000000E1NwQAAV\",\"CaseNumber\":\"00001062\",\"Subject\":\"Intermittent Short Circuits\",\"Status\":\"Escalated\"}]}";
		parseResults(responseBody);
		**/
		
		Scanner scanner=null;
		try {
			scanner = new Scanner( new File("records.json") );
			String json = scanner.useDelimiter("\\A").next();
			List<Info> results = parseResults(json);
			for (Info current : results) {
				System.out.println(current);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
		  if (scanner!=null) scanner.close();
		}
	}

	
	/**
	 * Public constructor takes GSon factory
	 */
	public SOQLHelper() {
	}
	
	/**
	 * Parse it to a Map of NVP[] suitable for lookup later
	 * @param json encoded resultset from SOQL query
	 */
	public static List<Info> parseResults(String responseBody) {
		
		/**
		 * Expecting to see one or more rows, each row must have Id field.
		 * Additional fields go into the NVP[]
		 */
		List<Info> results = new ArrayList<Info>();
		
	    JsonParser parser = new JsonParser();
	    JsonObject obj = parser.parse(responseBody).getAsJsonObject();
	    JsonArray records = obj.get("records").getAsJsonArray();
	    
	    for (JsonElement current : records) {
	    	
	    	_logger.log(Level.FINE,"Id="+current.getAsJsonObject().get("Id").getAsString());
	    	
	    	Info info = new Info(current.getAsJsonObject().get("Id").getAsString());
	    	
	    	for (Entry<String, JsonElement> field : current.getAsJsonObject().entrySet()) { // Enumerate all of the fields
	    		if (field.getValue().isJsonPrimitive() && field.getKey().compareTo("Id")!=0) {
		    		info.add(field.getKey(), field.getValue().getAsString());
	    		} else if (field.getValue().isJsonObject() && field.getKey().compareTo("attributes")!=0) {
	    			// Nested object
	    			for (Entry<String, JsonElement> nestedField : field.getValue().getAsJsonObject().entrySet()) {
	    				if (nestedField.getValue().isJsonPrimitive() 
	    						&& nestedField.getKey().compareTo("Id")!=0) {
	    					String name = field.getKey() + ((nestedField.getKey().compareTo("Name")!=0) ? "." + nestedField.getKey() : "");
	    					String value = nestedField.getValue().getAsString();
	    					info.add(name,value);
	    				}
	    			}
	    			
	    		}
	    	}
	    	
    		results.add(info);

	    }
		
		
		
		return results;
	}

}
