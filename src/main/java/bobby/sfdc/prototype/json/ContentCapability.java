package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_content_capability.htm#connect_responses_content_capability
 * 
 * @author bobby.white
 *
 */
public class ContentCapability {
	String checksum;
	FilesConnectRepository contentHubRepository;
	String contentUrl;
	String description;
	String downloadUrl;
	String externalDocumentUrl;
	String fileExtension;
	String fileSize;
	String fileType;
	boolean hasPdfPreview;
	String id;
	boolean isInMyFileSync;
	String mimeType;
	String renditionUrl;
	String renditionUrl240By180;
	String renditionUrl720by480;
	String repositoryFileUrl;
	String textPreview;
	String thumb120By90RenditionStatus;
	String thumb240By180RenditionStatus;
	String thumb720By480RenditionStatus;
	String title;
	String versionId;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContentCapability [");
		if (checksum != null) {
			builder.append("checksum=");
			builder.append(checksum);
			builder.append(", ");
		}
		if (contentHubRepository != null) {
			builder.append("contentHubRepository=");
			builder.append(contentHubRepository);
			builder.append(", ");
		}
		if (contentUrl != null) {
			builder.append("contentUrl=");
			builder.append(contentUrl);
			builder.append(", ");
		}
		if (description != null) {
			builder.append("description=");
			builder.append(description);
			builder.append(", ");
		}
		if (downloadUrl != null) {
			builder.append("downloadUrl=");
			builder.append(downloadUrl);
			builder.append(", ");
		}
		if (externalDocumentUrl != null) {
			builder.append("externalDocumentUrl=");
			builder.append(externalDocumentUrl);
			builder.append(", ");
		}
		if (fileExtension != null) {
			builder.append("fileExtension=");
			builder.append(fileExtension);
			builder.append(", ");
		}
		if (fileSize != null) {
			builder.append("fileSize=");
			builder.append(fileSize);
			builder.append(", ");
		}
		if (fileType != null) {
			builder.append("fileType=");
			builder.append(fileType);
			builder.append(", ");
		}
		builder.append("hasPdfPreview=");
		builder.append(hasPdfPreview);
		builder.append(", ");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		builder.append("isInMyFileSync=");
		builder.append(isInMyFileSync);
		builder.append(", ");
		if (mimeType != null) {
			builder.append("mimeType=");
			builder.append(mimeType);
			builder.append(", ");
		}
		if (renditionUrl != null) {
			builder.append("renditionUrl=");
			builder.append(renditionUrl);
			builder.append(", ");
		}
		if (renditionUrl240By180 != null) {
			builder.append("renditionUrl240By180=");
			builder.append(renditionUrl240By180);
			builder.append(", ");
		}
		if (renditionUrl720by480 != null) {
			builder.append("renditionUrl720by480=");
			builder.append(renditionUrl720by480);
			builder.append(", ");
		}
		if (repositoryFileUrl != null) {
			builder.append("repositoryFileUrl=");
			builder.append(repositoryFileUrl);
			builder.append(", ");
		}
		if (textPreview != null) {
			builder.append("textPreview=");
			builder.append(textPreview);
			builder.append(", ");
		}
		if (thumb120By90RenditionStatus != null) {
			builder.append("thumb120By90RenditionStatus=");
			builder.append(thumb120By90RenditionStatus);
			builder.append(", ");
		}
		if (thumb240By180RenditionStatus != null) {
			builder.append("thumb240By180RenditionStatus=");
			builder.append(thumb240By180RenditionStatus);
			builder.append(", ");
		}
		if (thumb720By480RenditionStatus != null) {
			builder.append("thumb720By480RenditionStatus=");
			builder.append(thumb720By480RenditionStatus);
			builder.append(", ");
		}
		if (title != null) {
			builder.append("title=");
			builder.append(title);
			builder.append(", ");
		}
		if (versionId != null) {
			builder.append("versionId=");
			builder.append(versionId);
		}
		builder.append("]");
		return builder.toString();
	}
	public String getChecksum() {
		return checksum;
	}
	public FilesConnectRepository getContentHubRepository() {
		return contentHubRepository;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public String getDescription() {
		return description;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public String getExternalDocumentUrl() {
		return externalDocumentUrl;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public String getFileSize() {
		return fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public boolean isHasPdfPreview() {
		return hasPdfPreview;
	}
	public String getId() {
		return id;
	}
	public boolean isInMyFileSync() {
		return isInMyFileSync;
	}
	public String getMimeType() {
		return mimeType;
	}
	public String getRenditionUrl() {
		return renditionUrl;
	}
	public String getRenditionUrl240By180() {
		return renditionUrl240By180;
	}
	public String getRenditionUrl720by480() {
		return renditionUrl720by480;
	}
	public String getRepositoryFileUrl() {
		return repositoryFileUrl;
	}
	public String getTextPreview() {
		return textPreview;
	}
	public String getThumb120By90RenditionStatus() {
		return thumb120By90RenditionStatus;
	}
	public String getThumb240By180RenditionStatus() {
		return thumb240By180RenditionStatus;
	}
	public String getThumb720By480RenditionStatus() {
		return thumb720By480RenditionStatus;
	}
	public String getTitle() {
		return title;
	}
	public String getVersionId() {
		return versionId;
	}

}
