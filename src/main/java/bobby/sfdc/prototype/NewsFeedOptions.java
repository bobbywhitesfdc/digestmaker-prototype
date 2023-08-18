/**
 * 
 */
package bobby.sfdc.prototype;

/**
 * These options will be used to control how we fetch the news feed
 * 
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_resource_feeds_news.htm
 * @author bobby.white
 *
 */
public class NewsFeedOptions {
   Frequency frequency;
   Depth depth;
   FeedFilter filter;
   int pageSize;
   int recentCommentCount;
   int elementsPerBundle;
   String network;
   private boolean suppressEmail=false;
   private String configFile=null;
   private String feedResourceOverride=null;
   private boolean eliminateGroupDigestOverlap;
   
   public NewsFeedOptions() {
		this.frequency = Frequency.DAILY;
		this.depth = Depth.ALLUPDATES;
		this.filter = FeedFilter.NONE;
		this.pageSize = 25;
		this.recentCommentCount = 3;
		this.elementsPerBundle = 3;
		this.network=null;
		this.configFile=null;
		this.feedResourceOverride=null;
		this.eliminateGroupDigestOverlap=false;	  
   }
   
   /**
    * Contructor which allows most options to be set at once
    * 
    * @param frequency
    * @param depth
    * @param filter
    * @param pageSize
    * @param recentCommentCount
    * @param elementsPerBundle
    */
   public NewsFeedOptions(Frequency frequency, Depth depth, FeedFilter filter,
		int pageSize, int recentCommentCount, int elementsPerBundle, String network) {
	super();
	this.frequency = frequency;
	this.depth = depth;
	this.filter = filter;
	this.pageSize = pageSize;
	this.recentCommentCount = recentCommentCount;
	this.elementsPerBundle = elementsPerBundle;
	this.network = network;
}

   /* 
    * What is the Frequency (and time duration) that the digest should cover?
    * 
    */
   public enum Frequency {
		DAILY("Daily","activity from the past day"),
		WEEKLY("Weekly","activity from the past week"), 
		EPOC("Unlimited","recent activity");
		
		private final String label;
		private final String chattyLabel;

		private Frequency(String label, String chattyLabel) {
			this.label=label;
			this.chattyLabel=chattyLabel;
		}

		/**
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}

		public String getChattyLabel() {
			return chattyLabel;
		}
		}


	public enum FeedFilter {
		   NONE("None","None"), 
		   ALL_QUESTIONS("AllQuestions","All Questions"),
		   SOLVED_QUESTIONS("SolvedQuestions","Solved Questions"),
		   UNSOLVED_QUESTIONS("UnsolvedQuestions","Unsolved Questions"),
		   UNANSWERED_QUESTIONS("UnansweredQuestions","Unanswered Questions");
		   private final String name;
		   private final String label;
	
		private FeedFilter(String name,String label) {
			   this.name = name;
			   this.label = label;
		   }
	
		/**
		 * @return the settingValue
		 */
		public String getName() {
			return name;
		}
		public String getLabel() {
			return label;
		}
		
		public static String allSettings() {
			String msg = "";
			for (FeedFilter current : FeedFilter.values()) {
				if (!msg.isEmpty()) {
					msg += ", ";
				}
				msg += current.getName();
			}
			return msg;
		}
	
	}
   
   /**
    * Depth of feed
    * 
    * @author bobby.white
    *
    */
   public enum Depth {
   	ALLUPDATES("AllUpdates","All Updates"),
   	FEWERUPDATES("FewerUpdates","Fewer Updates");
   	private final String name;
   	private final String label;

   	Depth(String name, String label) {
   		this.name=name;
   		this.label=label;
   	}

   	/**
   	 * @return the settingValue
   	 */
   	public String getName() {
   		return name;
   	}
   	public String getLabel() {
   		return label;
   	}
   }

/**
 * @return the depth
 */
public Depth getDepth() {
	return depth;
}

/**
 * @param depth the depth to set
 */
public void setDepth(Depth depth) {
	this.depth = depth;
}

/**
 * @return the filter
 */
public FeedFilter getFilter() {
	return filter;
}

/**
 * @param filter the filter to set
 */
public void setFilter(FeedFilter filter) {
	this.filter = filter;
}

/**
 * @return the pageSize
 */
public int getPageSize() {
	return pageSize;
}

/**
 * @param pageSize the pageSize to set
 */
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}

/**
 * @return the recentCommentCount
 */
public int getRecentCommentCount() {
	return recentCommentCount;
}

/**
 * @param recentCommentCount the recentCommentCount to set
 */
public void setRecentCommentCount(int recentCommentCount) {
	this.recentCommentCount = recentCommentCount;
}

/**
 * @return the elementsPerBundle
 */
public int getElementsPerBundle() {
	return elementsPerBundle;
}

/**
 * @param elementsPerBundle the elementsPerBundle to set
 */
public void setElementsPerBundle(int elementsPerBundle) {
	this.elementsPerBundle = elementsPerBundle;
}

/**
 * @return the frequency
 */
public Frequency getFrequency() {
	return frequency;
}

/**
 * @param frequency the frequency to set
 */
public void setFrequency(Frequency frequency) {
	this.frequency = frequency;
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("NewsFeedOptions [");
	if (frequency != null) {
		builder.append("frequency=");
		builder.append(frequency);
		builder.append(", ");
	}
	if (depth != null) {
		builder.append("depth=");
		builder.append(depth);
		builder.append(", ");
	}
	if (filter != null) {
		builder.append("filter=");
		builder.append(filter);
		builder.append(", ");
	}
	builder.append("pageSize=");
	builder.append(pageSize);
	builder.append(", recentCommentCount=");
	builder.append(recentCommentCount);
	builder.append(", elementsPerBundle=");
	builder.append(elementsPerBundle);
	builder.append(", ");
	if (network != null) {
		builder.append("network=");
		builder.append(network);
		builder.append(", ");
	}
	builder.append("suppressEmail=");
	builder.append(suppressEmail);
	builder.append(", ");
	if (configFile != null) {
		builder.append("configFile=");
		builder.append(configFile);
		builder.append(", ");
	}
	if (feedResourceOverride != null) {
		builder.append("feedResourceOverride=");
		builder.append(feedResourceOverride);
		builder.append(", ");
	}
	builder.append("eliminateGroupDigestOverlap=");
	builder.append(eliminateGroupDigestOverlap);
	builder.append("]");
	return builder.toString();
}

/**
 * @return the network
 */
public String getNetwork() {
	return network;
}

/**
 * @param network the network to set
 */
public void setNetwork(String network) {
	this.network = network;
}

public void setSuppressEmail(boolean state) {
	this.suppressEmail = state;
}

/**
 * @return the suppressEmail
 */
public boolean isSuppressEmail() {
	return suppressEmail;
}

/**
 * Override the configuration file used
 * @param filename
 */
public void setConfigFile(String filename) {
	this.configFile = filename;
}

/**
 * @return the configFile
 */
public String getConfigFile() {
	return configFile;
}

/**
 * @return the setFeedResourceOverride
 */
public String getFeedResourceOverride() {
	return feedResourceOverride;
}

/**
 * @param setFeedResourceOverride the setFeedResourceOverride to set
 */
public void setFeedResourceOverride(String feedResourceOverride) {
	this.feedResourceOverride = feedResourceOverride;
}

/**
 * @return the eliminateGroupDigestOverlap
 */
public boolean isEliminateGroupDigestOverlap() {
	return eliminateGroupDigestOverlap;
}

/**
 * @param eliminateGroupDigestOverlap the eliminateGroupDigestOverlap to set
 */
public void setEliminateGroupDigestOverlap(boolean eliminateGroupDigestOverlap) {
	this.eliminateGroupDigestOverlap = eliminateGroupDigestOverlap;
}

}
