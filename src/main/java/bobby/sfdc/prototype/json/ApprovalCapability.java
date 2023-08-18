package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_approval_capability.htm#connect_responses_approval_capability
 * @author bobby.white
 *
 */
public class ApprovalCapability {
   /** workitem Id **/
   String id;
   ApprovalPostTemplateField[] postTemplateFields;
   String processInstanceStepId;
   String status;
}
