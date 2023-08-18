package bobby.sfdc.prototype.rest;

import org.apache.http.Header;

public class RawFileResponse {
	byte[] body;
	long expectedLength;
	String mimeType;
	
	/**
	 * @return the body
	 */
	public byte[] getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(byte[] body) {
		this.body = body;
	}
	/**
	 * @return the expectedLength
	 */
	public long getExpectedLength() {
		return expectedLength;
	}
	/**
	 * @param expectedLength the expectedLength to set
	 */
	public void setExpectedLength(long expectedLength) {
		this.expectedLength = expectedLength;
	}
	public void setMimeType(Header contentType) {
		this.mimeType = contentType.getValue();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RawFileResponse [expectedLength=");
		builder.append(expectedLength);
		builder.append(", ");
		if (mimeType != null) {
			builder.append("mimeType=");
			builder.append(mimeType);
		}
		builder.append("]");
		return builder.toString();
	}
}
