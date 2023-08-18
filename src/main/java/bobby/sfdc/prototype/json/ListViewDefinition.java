package bobby.sfdc.prototype.json;

/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_relatedList.htm
 * 
 * @author bobby.white
 *
 */
public class ListViewDefinition {
   ListViewColumn[] columns;
   String label;
   ListViewObject object;
   int rowLimit;
   ListViewSort[] sortColumns;
}
