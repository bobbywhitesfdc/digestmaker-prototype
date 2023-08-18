package bobby.sfdc.prototype.json;
/**
 * NOTE: this is the Union of two different Response Bodies:  Recommendation and Non-Entity Recommendation!
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_non_entity_recommendation.htm#connect_responses_non_entity_recommendation
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_recommendation.htm#connect_responses_recommendation
 * @author bobby.white
 * 
 * NOTE:  Intentionally omitted properties that have been obsoleted as of v32.0
 *
 */
public class BaseRecommendation {
	/** Fields from Recommendation **/
   String actOnUrl;
   String action;
   BaseSummary entity;
   ExplanationSummary explanation; 
   String recommendationType;
   /** Fields from Non-Entity Recommendation that are not previously represented **/
   String displayLabel;
   Motif motif;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("BaseRecommendation [actOnUrl=");
	builder.append(actOnUrl);
	builder.append(", action=");
	builder.append(action);
	builder.append(", entity=");
	builder.append(entity);
	builder.append(", explanation=");
	builder.append(explanation);
	builder.append(", recommendationType=");
	builder.append(recommendationType);
	builder.append(", displayLabel=");
	builder.append(displayLabel);
	builder.append(", motif=");
	builder.append(motif);
	builder.append("]");
	return builder.toString();
}
public String getActOnUrl() {
	return actOnUrl;
}
public String getAction() {
	return action;
}
public BaseSummary getEntity() {
	return entity;
}
public ExplanationSummary getExplanation() {
	return explanation;
}
public String getRecommendationType() {
	return recommendationType;
}
public String getDisplayLabel() {
	return displayLabel;
}
public Motif getMotif() {
	return motif;
}
}
