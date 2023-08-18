package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_question_and_answers_capability.htm
 * 
 * @author bobby.white
 *
 */
public class QuestionAndAnswersCapability {
  Comment bestAnswer;
  UserSummary bestAnswerSelectedBy;
  boolean canCurrentUserSelectOrRemoveBestAnswer;
  String questionTitle;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("QuestionAndAnswersCapability [bestAnswer=");
	builder.append(bestAnswer);
	builder.append(", bestAnswerSelectedBy=");
	builder.append(bestAnswerSelectedBy);
	builder.append(", canCurrentUserSelectOrRemoveBestAnswer=");
	builder.append(canCurrentUserSelectOrRemoveBestAnswer);
	builder.append(", questionTitle=");
	builder.append(questionTitle);
	builder.append("]");
	return builder.toString();
}
/**
 * @return the bestAnswer
 */
public Comment getBestAnswer() {
	return bestAnswer;
}
/**
 * @return the bestAnswerSelectedBy
 */
public UserSummary getBestAnswerSelectedBy() {
	return bestAnswerSelectedBy;
}
/**
 * @return the canCurrentUserSelectOrRemoveBestAnswer
 */
public boolean isCanCurrentUserSelectOrRemoveBestAnswer() {
	return canCurrentUserSelectOrRemoveBestAnswer;
}
/**
 * @return the questionTitle
 */
public String getQuestionTitle() {
	return questionTitle;
}
}
