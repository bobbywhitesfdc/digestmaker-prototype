package bobby.sfdc.prototype.json;

public class Topic {
	@Override
	public String toString() {
		return "Topic [createdDate=" + createdDate + ", description="
				+ description + ", id=" + id + ", images=" + images + ", name="
				+ name + ", talkingAbout=" + talkingAbout + ", url=" + url
				+ "]";
	}
	String createdDate;
	String description;
	String id;
	TopicImages images;
	String name;
	Integer talkingAbout;
	String url;
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TopicImages getImages() {
		return images;
	}
	public void setImages(TopicImages images) {
		this.images = images;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTalkingAbout() {
		return talkingAbout;
	}
	public void setTalkingAbout(Integer talkingAbout) {
		this.talkingAbout = talkingAbout;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
