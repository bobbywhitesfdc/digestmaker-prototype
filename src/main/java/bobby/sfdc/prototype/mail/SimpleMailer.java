package bobby.sfdc.prototype.mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Adapted from http://lasanthals.blogspot.com/2013/09/send-email-using-java-and-google-account.html
 * 
 * @author bobby.white
 *
 */
public class SimpleMailer {
 
    protected final String USER_NAME = "bobby.white9@gmail.com";  
    protected final String PASSWORD = "asnvxnekdjsmgafq";
    protected final String FROM_ADDRESS = "bobby.white9@gmail.com"; 
    private Session session=null;
    
    public SimpleMailer() {
    }

    public static void main(String[] args) {
    	SimpleMailer email = new SimpleMailer();
    	
    	String destinationAddress = "bobby.white@salesforce.com";
    	String ccAddress = "bobbywhitenc@yahoo.com";
    	
        StringBuilder body = new StringBuilder();
        body.append("<body>");
        body.append("Hello!  This is a test");
        body.append("<br/><b>Bold</b>");
        body.append("</body>");
        email.sendEmailMessage( "Test email subject", body.toString(), destinationAddress,ccAddress);
    }
  
    /**
     * SendHTML Mail
     * 
     * @param msgSubject
     * @param messageBody  HTML body
     * @param toAddress  a single to address
     */
    public void sendEmailMessage(String msgSubject, String messageBody, String toAddress, String ccAddress ) {
     
    try {

        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(FROM_ADDRESS)); 
        message.setContent(messageBody,"text/html");
        
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress));
        
        if (ccAddress != null) {
            message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(ccAddress));
        }
       
        message.setSubject(msgSubject);
        Transport.send(message);
        System.out.println("Mail sent, Subject:" + msgSubject + " to: " + toAddress + " cc: " + ccAddress);
        
    } catch (MessagingException e) {
          throw new RuntimeException(e);
     }


    }

	/**
	 * @return
	 */
	public Session getSession() {
		
		if (session ==null) {
			//Create email sending properties
			 Properties props = new Properties();
			 props.put("mail.smtp.auth", "true");
			 props.put("mail.smtp.starttls.enable", "true");
			 props.put("mail.smtp.host", "smtp.gmail.com");
			 props.put("mail.smtp.port", "587");
	  
			Authenticator authenticator = new javax.mail.Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(USER_NAME, PASSWORD); }
			        };
			session = Session.getInstance(props,authenticator);	
		}
		return session;
	}  
}
