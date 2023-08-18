package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_address.htm
 * @author bobby.white
 *
 */

public class Address {
	String city;
	String country;
	String formattedAddress;
	String state;
	String street;
	String zip;
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public String getState() {
		return state;
	}
	public String getStreet() {
		return street;
	}
	public String getZip() {
		return zip;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [");
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (country != null) {
			builder.append("country=");
			builder.append(country);
			builder.append(", ");
		}
		if (formattedAddress != null) {
			builder.append("formattedAddress=");
			builder.append(formattedAddress);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
			builder.append(", ");
		}
		if (street != null) {
			builder.append("street=");
			builder.append(street);
			builder.append(", ");
		}
		if (zip != null) {
			builder.append("zip=");
			builder.append(zip);
		}
		builder.append("]");
		return builder.toString();
	}
}
