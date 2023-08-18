package bobby.sfdc.prototype.json;
/**   
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_feed_element_capabilities.htm
 * 
 * @author bobby.white
 *
 */
public class FeedElementCapabilities {
   ApprovalCapability approval;
   AssociatedActionsCapability associatedActions;
   BannerCapability banner;
   BookmarksCapability bookmarks;
   GenericBundleCapability bundle;  // This is the Union of GenericBundleCapability AND TrackedChangeBundleCapability
   CanvasCapability canvas;
   CaseCommentCapability caseCommentCapability;
   ChatterLikesCapability chatterLikes;
   CommentsCapability comments;
   ContentCapability content;
   DashboardComponentSnapshotCapability dashboardComponentSnapshot;
   EmailMessageCapability emailMessage;
   EnhancedLinkCapability enhancedLink;
   LinkCapability link;
   ModerationCapability moderation;
   PollCapability poll;
   QuestionAndAnswersCapability questionAndAnswers;
   RecommendationsCapability recommendations;
   RecordSnapshotCapability recordSnapshot;
   TopicsCapability topics;
   TrackedChangesCapability trackedChanges;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("FeedElementCapabilities [");
	if (approval != null) {
		builder.append("approval=");
		builder.append(approval);
		builder.append(", ");
	}
	if (associatedActions != null) {
		builder.append("associatedActions=");
		builder.append(associatedActions);
		builder.append(", ");
	}
	if (banner != null) {
		builder.append("banner=");
		builder.append(banner);
		builder.append(", ");
	}
	if (bookmarks != null) {
		builder.append("bookmarks=");
		builder.append(bookmarks);
		builder.append(", ");
	}
	if (bundle != null) {
		builder.append("bundle=");
		builder.append(bundle);
		builder.append(", ");
	}
	if (canvas != null) {
		builder.append("canvas=");
		builder.append(canvas);
		builder.append(", ");
	}
	if (caseCommentCapability != null) {
		builder.append("caseCommentCapability=");
		builder.append(caseCommentCapability);
		builder.append(", ");
	}
	if (chatterLikes != null) {
		builder.append("chatterLikes=");
		builder.append(chatterLikes);
		builder.append(", ");
	}
	if (comments != null) {
		builder.append("comments=");
		builder.append(comments);
		builder.append(", ");
	}
	if (content != null) {
		builder.append("content=");
		builder.append(content);
		builder.append(", ");
	}
	if (dashboardComponentSnapshot != null) {
		builder.append("dashboardComponentSnapshot=");
		builder.append(dashboardComponentSnapshot);
		builder.append(", ");
	}
	if (emailMessage != null) {
		builder.append("emailMessage=");
		builder.append(emailMessage);
		builder.append(", ");
	}
	if (enhancedLink != null) {
		builder.append("enhancedLink=");
		builder.append(enhancedLink);
		builder.append(", ");
	}
	if (link != null) {
		builder.append("link=");
		builder.append(link);
		builder.append(", ");
	}
	if (moderation != null) {
		builder.append("moderation=");
		builder.append(moderation);
		builder.append(", ");
	}
	if (poll != null) {
		builder.append("poll=");
		builder.append(poll);
		builder.append(", ");
	}
	if (questionAndAnswers != null) {
		builder.append("questionAndAnswers=");
		builder.append(questionAndAnswers);
		builder.append(", ");
	}
	if (recommendations != null) {
		builder.append("recommendations=");
		builder.append(recommendations);
		builder.append(", ");
	}
	if (recordSnapshot != null) {
		builder.append("recordSnapshot=");
		builder.append(recordSnapshot);
		builder.append(", ");
	}
	if (topics != null) {
		builder.append("topics=");
		builder.append(topics);
		builder.append(", ");
	}
	if (trackedChanges != null) {
		builder.append("trackedChanges=");
		builder.append(trackedChanges);
	}
	builder.append("]");
	return builder.toString();
}
public ApprovalCapability getApproval() {
	return approval;
}
public AssociatedActionsCapability getAssociatedActions() {
	return associatedActions;
}
public BannerCapability getBanner() {
	return banner;
}
public BookmarksCapability getBookmarks() {
	return bookmarks;
}
public GenericBundleCapability getBundle() {
	return bundle;
}
public CanvasCapability getCanvas() {
	return canvas;
}
public CaseCommentCapability getCaseCommentCapability() {
	return caseCommentCapability;
}
public ChatterLikesCapability getChatterLikes() {
	return chatterLikes;
}
public CommentsCapability getComments() {
	return comments;
}
public ContentCapability getContent() {
	return content;
}
public DashboardComponentSnapshotCapability getDashboardComponentSnapshot() {
	return dashboardComponentSnapshot;
}
public EmailMessageCapability getEmailMessage() {
	return emailMessage;
}
public EnhancedLinkCapability getEnhancedLink() {
	return enhancedLink;
}
public LinkCapability getLink() {
	return link;
}
public ModerationCapability getModeration() {
	return moderation;
}
public PollCapability getPoll() {
	return poll;
}
public QuestionAndAnswersCapability getQuestionAndAnswers() {
	return questionAndAnswers;
}
public RecommendationsCapability getRecommendations() {
	return recommendations;
}
public RecordSnapshotCapability getRecordSnapshot() {
	return recordSnapshot;
}
public TopicsCapability getTopics() {
	return topics;
}
public TrackedChangesCapability getTrackedChanges() {
	return trackedChanges;
}
}
