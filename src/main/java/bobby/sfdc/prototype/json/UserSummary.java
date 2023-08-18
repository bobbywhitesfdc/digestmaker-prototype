package bobby.sfdc.prototype.json;

import java.util.Arrays;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_user_summary.htm
 * 
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_user_detail.htm#connect_responses_user_detail
 * 
 * @author bobby.white
 *
 */
public class UserSummary implements IActor {
   String additionalLabel;
   String communityNickname;
   String companyName;
   String displayName;
   String firstName;
   String id;
   boolean isActive;
   boolean isInThisCommunity;
   String lastName;
   Motif motif;
   Reference mySubscription;
   String name;
   Photo photo;
   String recordViewUrl;
   Reputation reputation;
   String title;
   String type;
   String url;
   String userType;
   
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

@Override
public String toString() {
	final int maxLen = 50;
	StringBuilder builder = new StringBuilder();
	builder.append("UserSummary [");
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
	if (id != null) {
		builder.append("id=");
		builder.append(id);
		builder.append(", ");
	}
	builder.append("isActive=");
	builder.append(isActive);
	builder.append(", isInThisCommunity=");
	builder.append(isInThisCommunity);
	builder.append(", ");
	if (lastName != null) {
		builder.append("lastName=");
		builder.append(lastName);
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
	if (photo != null) {
		builder.append("photo=");
		builder.append(photo);
		builder.append(", ");
	}
	if (recordViewUrl != null) {
		builder.append("recordViewUrl=");
		builder.append(recordViewUrl);
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
	if (userType != null) {
		builder.append("userType=");
		builder.append(userType);
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
	}
	builder.append("]");
	return builder.toString();
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

public String getDisplayName() {
	return displayName;
}

public String getFirstName() {
	return firstName;
}

public String getId() {
	return id;
}

public boolean isActive() {
	return isActive;
}

public boolean isInThisCommunity() {
	return isInThisCommunity;
}

public String getLastName() {
	return lastName;
}

public Motif getMotif() {
	return motif;
}

public Reference getMySubscription() {
	return mySubscription;
}

public String getName() {
	return name;
}

public Photo getPhoto() {
	return photo;
}

public String getRecordViewUrl() {
	return recordViewUrl;
}

public Reputation getReputation() {
	return reputation;
}

public String getTitle() {
	return title;
}

public String getType() {
	return type;
}

public String getUrl() {
	return url;
}

public String getUserType() {
	return userType;
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

/**
 * @param additionalLabel the additionalLabel to set
 */
public void setAdditionalLabel(String additionalLabel) {
	this.additionalLabel = additionalLabel;
}

/**
 * @param communityNickname the communityNickname to set
 */
public void setCommunityNickname(String communityNickname) {
	this.communityNickname = communityNickname;
}

/**
 * @param companyName the companyName to set
 */
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

/**
 * @param displayName the displayName to set
 */
public void setDisplayName(String displayName) {
	this.displayName = displayName;
}

/**
 * @param firstName the firstName to set
 */
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

/**
 * @param id the id to set
 */
public void setId(String id) {
	this.id = id;
}

/**
 * @param isActive the isActive to set
 */
public void setActive(boolean isActive) {
	this.isActive = isActive;
}

/**
 * @param isInThisCommunity the isInThisCommunity to set
 */
public void setInThisCommunity(boolean isInThisCommunity) {
	this.isInThisCommunity = isInThisCommunity;
}

/**
 * @param lastName the lastName to set
 */
public void setLastName(String lastName) {
	this.lastName = lastName;
}

/**
 * @param motif the motif to set
 */
public void setMotif(Motif motif) {
	this.motif = motif;
}

/**
 * @param mySubscription the mySubscription to set
 */
public void setMySubscription(Reference mySubscription) {
	this.mySubscription = mySubscription;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @param photo the photo to set
 */
public void setPhoto(Photo photo) {
	this.photo = photo;
}

/**
 * @param recordViewUrl the recordViewUrl to set
 */
public void setRecordViewUrl(String recordViewUrl) {
	this.recordViewUrl = recordViewUrl;
}

/**
 * @param reputation the reputation to set
 */
public void setReputation(Reputation reputation) {
	this.reputation = reputation;
}

/**
 * @param title the title to set
 */
public void setTitle(String title) {
	this.title = title;
}

/**
 * @param type the type to set
 */
public void setType(String type) {
	this.type = type;
}

/**
 * @param url the url to set
 */
public void setUrl(String url) {
	this.url = url;
}

/**
 * @param userType the userType to set
 */
public void setUserType(String userType) {
	this.userType = userType;
}

/**
 * @param aboutMe the aboutMe to set
 */
public void setAboutMe(String aboutMe) {
	this.aboutMe = aboutMe;
}

/**
 * @param address the address to set
 */
public void setAddress(Address address) {
	this.address = address;
}

/**
 * @param chatterActivity the chatterActivity to set
 */
public void setChatterActivity(ChatterActivity chatterActivity) {
	this.chatterActivity = chatterActivity;
}

/**
 * @param chatterInfluence the chatterInfluence to set
 */
public void setChatterInfluence(ChatterInfluence chatterInfluence) {
	this.chatterInfluence = chatterInfluence;
}

/**
 * @param email the email to set
 */
public void setEmail(String email) {
	this.email = email;
}

/**
 * @param followersCount the followersCount to set
 */
public void setFollowersCount(int followersCount) {
	this.followersCount = followersCount;
}

/**
 * @param followingCounts the followingCounts to set
 */
public void setFollowingCounts(FollowingCounts followingCounts) {
	this.followingCounts = followingCounts;
}

/**
 * @param groupCount the groupCount to set
 */
public void setGroupCount(int groupCount) {
	this.groupCount = groupCount;
}

/**
 * @param hasChatter the hasChatter to set
 */
public void setHasChatter(boolean hasChatter) {
	this.hasChatter = hasChatter;
}

/**
 * @param managerId the managerId to set
 */
public void setManagerId(String managerId) {
	this.managerId = managerId;
}

/**
 * @param managerName the managerName to set
 */
public void setManagerName(String managerName) {
	this.managerName = managerName;
}

/**
 * @param phoneNumbers the phoneNumbers to set
 */
public void setPhoneNumbers(PhoneNumber[] phoneNumbers) {
	this.phoneNumbers = phoneNumbers;
}

/**
 * @param thanksReceived the thanksReceived to set
 */
public void setThanksReceived(int thanksReceived) {
	this.thanksReceived = thanksReceived;
}

/**
 * @param username the username to set
 */
public void setUsername(String username) {
	this.username = username;
}
   
}
