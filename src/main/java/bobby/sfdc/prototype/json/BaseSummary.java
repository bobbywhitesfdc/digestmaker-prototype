package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * NOTE:  Union Type   File Summary | Group | Record Summary | User Summary
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_file.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_group.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_record_summary.htm
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_user_summary.htm
 * 
 * From FeedElement.parent also these types
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_file_details.htm#connect_responses_file_details
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_group_detail.htm#connect_responses_group_detail
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recordView.htm#connect_responses_recordView
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_user_detail.htm#connect_responses_user_detail
 * 
 * @author bobby.white
 *
 */
public class BaseSummary implements IActor {
	public static final String CONTENT_VERSION_TYPEID = "069";
	/* RecordSummary -- Least common denominator */
	   String id;
	   Motif motif;
	   Reference mySubscription;
	   String name;
	   String recordViewUrl;
	   String type;
	   String url;

		/*Group -- unique fields only, some overlap*/
	   Announcement announcement;
	   boolean canHaveChatterGuests;
	   Reference community;
	   String description;
	   String emailToChatterAddress;
	   boolean isArchived;
	   boolean isAutoArchiveDisabled;
	   int fileCount;
	   String lastFeedElementPostDate;
	   int memberCount;
	   String myRole;
	   UserSummary owner;
	   String visibility;

	/* UserSummary*/
	   String additionalLabel;
	   String communityNickname;
	   String companyName;
	   String displayName;
	   String firstName;
	   boolean isActive;
	   boolean isChatterGuest;
	   boolean isInThisCommunity;
	   String lastName;
	   Photo photo;
	   Reputation reputation;
	   String title;
	   String userType;
	   
	   /*FileDetails */
	   int pageCount;
	   
	   /*Group Detail -- unique fields*/
	   GroupInformation information;
	   
	   /* RecordView */
	   ListViewDefinition[] relatedListDefinitions;
	   RecordViewSection[] sections;
	   
	   /* UserDetail -- unique fields */
	   String aboutMe;
	   Address address;
	   ChatterActivity chatterActivity;
	   ChatterInfluence chatterInfluence;
	   String email;
	   int followersCount;
	   FollowingCounts followingCounts;
	   int groupCount;
	   boolean hasChatter;
	   String managerId;
	   String managerName;
	   PhoneNumber[] phoneNumbers;
	   int thanksReceived;
	   String username;
	   

	/*FileSummary -- lots of unique adds here*/
	   String checkSum;
	   int contentSize;
	   FilesConnectRepository contentHubRepository;
	   String contentModifiedDate;
	   String contentUrl;
	   String downloadUrl;
		String externalDocumentUrl;
		String fileExtension;
		String fileSize;
		String fileType;
		boolean hasPdfPreview;
		boolean isInMyFileSync;
		boolean isMajorVersion; 
		String mimeType;
		ModerationFlags moderationFlags; 
		String modifiedDate;
		String origin;
		Reference parentFolder;
		String renditionUrl;
		String renditionUrl240By180;
		String renditionUrl720by480;
		String repositoryFileUrl;
		String sharingRole; 
		String textPreview;
		String thumb120By90RenditionStatus;
		String thumb240By180RenditionStatus;
		String thumb720By480RenditionStatus;
		String versionNumber;
		
		/**
		 * Synthetic data, added after it was received
		 */
		private transient String mruDate;
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			final int maxLen = 50;
			StringBuilder builder = new StringBuilder();
			builder.append("BaseSummary [");
			if (id != null) {
				builder.append("id=");
				builder.append(id);
				builder.append(", ");
			}
			if (motif != null) {
				builder.append("motif=");
				builder.append(motif);
				builder.append(", ");
			}
			if (mySubscription != null) {
				builder.append("mySubscription=");
				builder.append(mySubscription);
				builder.append(", ");
			}
			if (name != null) {
				builder.append("name=");
				builder.append(name);
				builder.append(", ");
			}
			if (recordViewUrl != null) {
				builder.append("recordViewUrl=");
				builder.append(recordViewUrl);
				builder.append(", ");
			}
			if (type != null) {
				builder.append("type=");
				builder.append(type);
				builder.append(", ");
			}
			if (url != null) {
				builder.append("url=");
				builder.append(url);
				builder.append(", ");
			}
			if (announcement != null) {
				builder.append("announcement=");
				builder.append(announcement);
				builder.append(", ");
			}
			builder.append("canHaveChatterGuests=");
			builder.append(canHaveChatterGuests);
			builder.append(", ");
			if (community != null) {
				builder.append("community=");
				builder.append(community);
				builder.append(", ");
			}
			if (description != null) {
				builder.append("description=");
				builder.append(description);
				builder.append(", ");
			}
			if (emailToChatterAddress != null) {
				builder.append("emailToChatterAddress=");
				builder.append(emailToChatterAddress);
				builder.append(", ");
			}
			builder.append("isArchived=");
			builder.append(isArchived);
			builder.append(", isAutoArchiveDisabled=");
			builder.append(isAutoArchiveDisabled);
			builder.append(", fileCount=");
			builder.append(fileCount);
			builder.append(", ");
			if (lastFeedElementPostDate != null) {
				builder.append("lastFeedElementPostDate=");
				builder.append(lastFeedElementPostDate);
				builder.append(", ");
			}
			builder.append("memberCount=");
			builder.append(memberCount);
			builder.append(", ");
			if (myRole != null) {
				builder.append("myRole=");
				builder.append(myRole);
				builder.append(", ");
			}
			if (owner != null) {
				builder.append("owner=");
				builder.append(owner);
				builder.append(", ");
			}
			if (visibility != null) {
				builder.append("visibility=");
				builder.append(visibility);
				builder.append(", ");
			}
			if (additionalLabel != null) {
				builder.append("additionalLabel=");
				builder.append(additionalLabel);
				builder.append(", ");
			}
			if (communityNickname != null) {
				builder.append("communityNickname=");
				builder.append(communityNickname);
				builder.append(", ");
			}
			if (companyName != null) {
				builder.append("companyName=");
				builder.append(companyName);
				builder.append(", ");
			}
			if (displayName != null) {
				builder.append("displayName=");
				builder.append(displayName);
				builder.append(", ");
			}
			if (firstName != null) {
				builder.append("firstName=");
				builder.append(firstName);
				builder.append(", ");
			}
			builder.append("isActive=");
			builder.append(isActive);
			builder.append(", isChatterGuest=");
			builder.append(isChatterGuest);
			builder.append(", isInThisCommunity=");
			builder.append(isInThisCommunity);
			builder.append(", ");
			if (lastName != null) {
				builder.append("lastName=");
				builder.append(lastName);
				builder.append(", ");
			}
			if (photo != null) {
				builder.append("photo=");
				builder.append(photo);
				builder.append(", ");
			}
			if (reputation != null) {
				builder.append("reputation=");
				builder.append(reputation);
				builder.append(", ");
			}
			if (title != null) {
				builder.append("title=");
				builder.append(title);
				builder.append(", ");
			}
			if (userType != null) {
				builder.append("userType=");
				builder.append(userType);
				builder.append(", ");
			}
			builder.append("pageCount=");
			builder.append(pageCount);
			builder.append(", ");
			if (information != null) {
				builder.append("information=");
				builder.append(information);
				builder.append(", ");
			}
			if (relatedListDefinitions != null) {
				builder.append("relatedListDefinitions=");
				builder.append(Arrays.asList(relatedListDefinitions).subList(0,
						Math.min(relatedListDefinitions.length, maxLen)));
				builder.append(", ");
			}
			if (sections != null) {
				builder.append("sections=");
				builder.append(Arrays.asList(sections).subList(0,
						Math.min(sections.length, maxLen)));
				builder.append(", ");
			}
			if (aboutMe != null) {
				builder.append("aboutMe=");
				builder.append(aboutMe);
				builder.append(", ");
			}
			if (address != null) {
				builder.append("address=");
				builder.append(address);
				builder.append(", ");
			}
			if (chatterActivity != null) {
				builder.append("chatterActivity=");
				builder.append(chatterActivity);
				builder.append(", ");
			}
			if (chatterInfluence != null) {
				builder.append("chatterInfluence=");
				builder.append(chatterInfluence);
				builder.append(", ");
			}
			if (email != null) {
				builder.append("email=");
				builder.append(email);
				builder.append(", ");
			}
			builder.append("followersCount=");
			builder.append(followersCount);
			builder.append(", ");
			if (followingCounts != null) {
				builder.append("followingCounts=");
				builder.append(followingCounts);
				builder.append(", ");
			}
			builder.append("groupCount=");
			builder.append(groupCount);
			builder.append(", hasChatter=");
			builder.append(hasChatter);
			builder.append(", ");
			if (managerId != null) {
				builder.append("managerId=");
				builder.append(managerId);
				builder.append(", ");
			}
			if (managerName != null) {
				builder.append("managerName=");
				builder.append(managerName);
				builder.append(", ");
			}
			if (phoneNumbers != null) {
				builder.append("phoneNumbers=");
				builder.append(Arrays.asList(phoneNumbers).subList(0,
						Math.min(phoneNumbers.length, maxLen)));
				builder.append(", ");
			}
			builder.append("thanksReceived=");
			builder.append(thanksReceived);
			builder.append(", ");
			if (username != null) {
				builder.append("username=");
				builder.append(username);
				builder.append(", ");
			}
			if (checkSum != null) {
				builder.append("checkSum=");
				builder.append(checkSum);
				builder.append(", ");
			}
			builder.append("contentSize=");
			builder.append(contentSize);
			builder.append(", ");
			if (contentHubRepository != null) {
				builder.append("contentHubRepository=");
				builder.append(contentHubRepository);
				builder.append(", ");
			}
			if (contentModifiedDate != null) {
				builder.append("contentModifiedDate=");
				builder.append(contentModifiedDate);
				builder.append(", ");
			}
			if (contentUrl != null) {
				builder.append("contentUrl=");
				builder.append(contentUrl);
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
			builder.append(", isInMyFileSync=");
			builder.append(isInMyFileSync);
			builder.append(", isMajorVersion=");
			builder.append(isMajorVersion);
			builder.append(", ");
			if (mimeType != null) {
				builder.append("mimeType=");
				builder.append(mimeType);
				builder.append(", ");
			}
			if (moderationFlags != null) {
				builder.append("moderationFlags=");
				builder.append(moderationFlags);
				builder.append(", ");
			}
			if (modifiedDate != null) {
				builder.append("modifiedDate=");
				builder.append(modifiedDate);
				builder.append(", ");
			}
			if (origin != null) {
				builder.append("origin=");
				builder.append(origin);
				builder.append(", ");
			}
			if (parentFolder != null) {
				builder.append("parentFolder=");
				builder.append(parentFolder);
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
			if (sharingRole != null) {
				builder.append("sharingRole=");
				builder.append(sharingRole);
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
			if (versionNumber != null) {
				builder.append("versionNumber=");
				builder.append(versionNumber);
			}
			builder.append("]");
			return builder.toString();
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getEmailToChatterAddress() {
			return emailToChatterAddress;
		}
		public void setEmailToChatterAddress(String emailToChatterAddress) {
			this.emailToChatterAddress = emailToChatterAddress;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public Photo getPhoto() {
			return photo;
		}
		public void setPhoto(Photo photo) {
			this.photo = photo;
		}
		public Motif getMotif() {
			return motif;
		}
		public Reference getMySubscription() {
			return mySubscription;
		}
		public String getRecordViewUrl() {
			return recordViewUrl;
		}
		public String getUrl() {
			return url;
		}
		public Announcement getAnnouncement() {
			return announcement;
		}
		public boolean isCanHaveChatterGuests() {
			return canHaveChatterGuests;
		}
		public Reference getCommunity() {
			return community;
		}
		public boolean isArchived() {
			return isArchived;
		}
		public boolean isAutoArchiveDisabled() {
			return isAutoArchiveDisabled;
		}
		public int getFileCount() {
			return fileCount;
		}
		public String getLastFeedElementPostDate() {
			return lastFeedElementPostDate;
		}
		public int getMemberCount() {
			return memberCount;
		}
		public String getMyRole() {
			return myRole;
		}
		public UserSummary getOwner() {
			return owner;
		}
		public String getVisibility() {
			return visibility;
		}
		public String getAdditionalLabel() {
			return additionalLabel;
		}
		public String getCommunityNickname() {
			return communityNickname;
		}
		public String getCompanyName() {
			return companyName;
		}
		public String getFirstName() {
			return firstName;
		}
		public boolean isActive() {
			return isActive;
		}
		public boolean isChatterGuest() {
			return isChatterGuest;
		}
		public boolean isInThisCommunity() {
			return isInThisCommunity;
		}
		public String getLastName() {
			return lastName;
		}
		public Reputation getReputation() {
			return reputation;
		}
		public String getTitle() {
			return title;
		}
		public String getUserType() {
			return userType;
		}
		public int getPageCount() {
			return pageCount;
		}
		public GroupInformation getInformation() {
			return information;
		}
		public ListViewDefinition[] getRelatedListDefinitions() {
			return relatedListDefinitions;
		}
		public RecordViewSection[] getSections() {
			return sections;
		}
		public String getAboutMe() {
			return aboutMe;
		}
		public Address getAddress() {
			return address;
		}
		public ChatterActivity getChatterActivity() {
			return chatterActivity;
		}
		public ChatterInfluence getChatterInfluence() {
			return chatterInfluence;
		}
		public String getEmail() {
			return email;
		}
		public int getFollowersCount() {
			return followersCount;
		}
		public FollowingCounts getFollowingCounts() {
			return followingCounts;
		}
		public int getGroupCount() {
			return groupCount;
		}
		public boolean isHasChatter() {
			return hasChatter;
		}
		public String getManagerId() {
			return managerId;
		}
		public String getManagerName() {
			return managerName;
		}
		public PhoneNumber[] getPhoneNumbers() {
			return phoneNumbers;
		}
		public int getThanksReceived() {
			return thanksReceived;
		}
		public String getUsername() {
			return username;
		}
		public String getCheckSum() {
			return checkSum;
		}
		public int getContentSize() {
			return contentSize;
		}
		public FilesConnectRepository getContentHubRepository() {
			return contentHubRepository;
		}
		public String getContentModifiedDate() {
			return contentModifiedDate;
		}
		public String getContentUrl() {
			return contentUrl;
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
		public boolean isInMyFileSync() {
			return isInMyFileSync;
		}
		public boolean isMajorVersion() {
			return isMajorVersion;
		}
		public String getMimeType() {
			return mimeType;
		}
		public ModerationFlags getModerationFlags() {
			return moderationFlags;
		}
		public String getModifiedDate() {
			return modifiedDate;
		}
		public String getOrigin() {
			return origin;
		}
		public Reference getParentFolder() {
			return parentFolder;
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
		public String getSharingRole() {
			return sharingRole;
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
		public String getVersionNumber() {
			return versionNumber;
		}
		
		/**
		 * Convenience method
		 * @return true if this is a Group
		 */
		public boolean isGroup() {
			return (type!=null && type.compareTo("CollaborationGroup")==0);
		}
		
		/**
		 * Convenience method
		 * @return true if this is a File
		 */
		public boolean isFile() {
			String typeID = this.id != null ? this.id.substring(0,3) : "";
			return (typeID.compareTo(CONTENT_VERSION_TYPEID)==0);
		}
		public boolean isUser() {
			return (type!=null && type.compareTo("User")==0);
		}
		
		/**
		 * @return the mruDate
		 */
		public String getMruDate() {
			return mruDate;
		}
		/**
		 * @param mruDate the mruDate to set
		 */
		public void setMruDate(String mruDate) {
			this.mruDate = mruDate;
		}
}
