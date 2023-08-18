package bobby.sfdc.prototype.json;
/**
 * http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_responses_banner_capability.htm
 * @author bobby.white
 *
 */
public class BannerCapability {
  Motif motif;
  String style;
public Motif getMotif() {
	return motif;
}
public String getStyle() {
	return style;
}
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("BannerCapability [motif=");
	builder.append(motif);
	builder.append(", style=");
	builder.append(style);
	builder.append("]");
	return builder.toString();
}
}
