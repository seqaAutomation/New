package Utilities;

import org.testng.annotations.Test;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtility {
	private MimeMessage message; // the email message
    private MimeBodyPart mainbody; // the email main body
    private MimeBodyPart[] attachments; // the email attachments
    private int count; // the number of attachments
    private static final int STEP = 32;

    /**
	* Check whether the given string represents a valid Internet address.
	*/
	public static boolean isValidAddress(String str){
		try{
			//new InternetAddress(str);
			InternetAddress.parse(str);
		} catch(AddressException e){
			return false;
		}
		return true;
	}

    /** The default contructor, also the prefered one. It uses the default session, which means
     *  the better efficiency.
     */
    public EmailUtility()
    {
           Properties props = new Properties();
             props.put("mail.smtp.host", "internal-mail-router.oracle.com");
             props.put("mail.debug", "true");
           //session = Session.getDefaultInstance(props, null);
           //session.setDebug(false)
           Session session = Session.getInstance(props);
           message = new MimeMessage(session);
           mainbody = new MimeBodyPart();
           attachments = new MimeBodyPart[STEP];
           count = 0;
    }

    /* Expand the attachemnts array when adding new attachments */
    private void expand(){
            if (count == attachments.length) {
               MimeBodyPart[] tmp = new MimeBodyPart[attachments.length + STEP];
               for (int i = 0; i < attachments.length; i++) tmp[i] = attachments[i];
               attachments = tmp;
            }
    }

    /** Set the subject field of this email.
     *  @param subject the subject of the email.
     *  @see javax.mail.internet.MimeMessage.setSubject().
     */
    public void setSubject(String subject) throws MessagingException {
           this.message.setSubject(subject);
    }

    /** Get the subject field of this email.
     *  @return the subject of the email.
     *  @see javax.mail.internet.MimeMessage.getSubject().
     */
    public String getSubject() throws MessagingException {
           return this.message.getSubject();
    }

    /** Set the "From" field of this email.
     *  @param sender the sender's information (email address and name).
     *  @see javax.mail.internet.MimeMessage.setFrom().
     */
    public void setFrom(Address sender) throws MessagingException {
           this.message.setFrom(sender);
    }

    /** Set the "From" field of this email.
     *  @param address the sender's email address.
     *  @see javax.mail.internet.MimeMessage.setFrom().
     */
    public void setFrom(String address) throws MessagingException {
           this.message.setFrom(new InternetAddress(address));
    }

    /** Set the "From" field of this email.
     *  @param address the sender's email address.
     *  @param name the sender's name.
     *  @see javax.mail.internet.MimeMessage.setFrom().
     */
    public void setFrom(String address, String name) throws UnsupportedEncodingException, MessagingException{
           this.message.setFrom(new InternetAddress(address, name));
    }

    /** Add a sender to the "From" field of this email.
     *  @param sender the sender's information (email address and name).
     *  @see javax.mail.internet.MimeMessage.addFrom().
     */
    public void addFrom(Address sender) throws MessagingException {
           Address[] list = new Address[1];
           list[0] = sender;
           this.message.addFrom(list);
    }

    /** Add a sender to the "From" field of this email.
     *  @param address the sender's email address.
     *  @see javax.mail.internet.MimeMessage.addFrom().
     */
    public void addFrom(String address) throws MessagingException {
           this.addFrom(new InternetAddress(address));
    }

    /** Add a sender to the "From" field of this email.
     *  @param address the sender's email address.
     *  @param name the sender's name.
     *  @see javax.mail.internet.MimeMessage.addFrom().
     */
    public void addFrom(String address, String name) throws UnsupportedEncodingException, MessagingException{
           this.addFrom(new InternetAddress(address, name));
    }

    /** Set the "TO" field of this email.
     *  @param address the recipient's information (email address and name).
     *  @see javax.mail.Message.setRecipient().
     */
    public void setTO(Address address) throws MessagingException {
           this.message.setRecipient(Message.RecipientType.TO, address);
    }

    /** Set the "TO" field of this email.
     *  @param address the recipient's email address.
     *  @see javax.mail.Message.setRecipient().
     */
    public void setTO(String address) throws MessagingException {
           this.message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
    }

    /** Set the "TO" field of this email.
     *  @param address the recipient's email address. 
     *  @param name the recipient's name.
     *  @see javax.mail.Message.setRecipient().
     */
    public void setTO(String address, String name) throws UnsupportedEncodingException, MessagingException {
           this.message.setRecipient(Message.RecipientType.TO, new InternetAddress(address, name));
    }

    /** Add a recipient to the "TO" field of this email.
     *  @param address the recipient's informatin (email ddress and name).
     *  @see javax.mail.Message.addRecipient().
     */
    public void addTO(Address address) throws MessagingException {
           this.message.addRecipient(Message.RecipientType.TO, address);
    }

    /** Add a recipient to the "TO" field of this email.
     *  @param address the recipient's email address.
     *  @see javax.mail.Message.addRecipient().
     */
    public void addTO(String address) throws MessagingException {
           this.message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
    }
    
    /** Add a recipient to the "TO" field of this email.
     *  @param address the recipient's email address separated by ";"
     */
    public void setTo(String emailId) throws MessagingException {
        String[] recipientList = emailId.split(";");
        InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
        int counter = 0;
        for (String recipients : recipientList) {
            recipientAddress[counter] = new InternetAddress(recipients.trim());
            counter++;
        }
        this.message.setRecipients(Message.RecipientType.TO, recipientAddress);
 }

    /** Add a recipient to the "TO" field of this email.
     *  @param address the recipient's email address.
     *  @param name the recipient's name.
     *  @see javax.mail.Message.addRecipient().
     */
    public void addTO(String address, String name) throws UnsupportedEncodingException, MessagingException {
           this.message.addRecipient(Message.RecipientType.TO, new InternetAddress(address, name));
    }

    /** Set the "CC" field of this email.
     *  @param address the recipient's information (email address and name).
     *  @see javax.mail.Message.setRecipient().
     */
    public void setCC(Address address) throws MessagingException {
           this.message.setRecipient(Message.RecipientType.CC, address);
    }

    /** Set the "CC" field of this email.
     *  @param address the recipient's email address.
     *  @see javax.mail.Message.setRecipient().
     */
    public void setCC(String address) throws MessagingException {
           this.message.setRecipient(Message.RecipientType.CC, new InternetAddress(address));
    }

    /** Set the "CC" field of this email.
     *  @param address the recipient's email address.
     *  @param name the recipient's name.
     *  @see javax.mail.Message.setRecipient().
     */
    public void setCC(String address, String name) throws UnsupportedEncodingException, MessagingException {
           this.message.setRecipient(Message.RecipientType.CC, new InternetAddress(address, name));
    }

    /** Add a recipient to the "CC" field of this email.
     *  @param address the recipient's information (email address and name).
     *  @see javax.mail.Message.addRecipient().
     */
    public void addCC(Address address) throws MessagingException {
           this.message.addRecipient(Message.RecipientType.CC, address);
    }

    /** Add a recipient to the "CC" field of this email.
     *  @param address the recipient's email address.
     *  @see javax.mail.Message.addRecipient().
     */
    public void addCC(String address) throws MessagingException {
           this.message.addRecipient(Message.RecipientType.CC, new InternetAddress(address));
    }

    /** Add a recipient to the "CC" field of this email.
     *  @param address the recipient's email address.
     *  @param name the recipient's name.
     *  @see javax.mail.Message.addRecipient().
     */
    public void addCC(String address, String name) throws UnsupportedEncodingException, MessagingException {
           this.message.addRecipient(Message.RecipientType.CC, new InternetAddress(address, name));
    }

    /** Set the "BCC" field of this email.
     *  @param address the recipient's information (email address and name).
     *  @see javax.mail.Message.setRecipient().
     */
    public void setBCC(Address address) throws MessagingException {
           this.message.setRecipient(Message.RecipientType.BCC, address);
    }

    /** Set the "BCC" field of this email.
     *  @param address the recipient's email address.
     *  @see javax.mail.Message.setRecipient().
     */
    public void setBCC(String address) throws MessagingException {
           this.message.setRecipient(Message.RecipientType.BCC, new InternetAddress(address));
    }

    /** Set the "BCC" field of this email.
     *  @param address the recipient's email address.
     *  @param name the recipient's name.
     *  @see javax.mail.Message.setRecipient().
     */
    public void setBCC(String address, String name) throws UnsupportedEncodingException, MessagingException {
           this.message.setRecipient(Message.RecipientType.BCC, new InternetAddress(address, name));
    }

    /** Add a recipient to the "BCC" field of this email.
     *  @param address the recipient's information (email address and name).
     *  @see javax.mail.Message.addRecipient().
     */
    public void addBCC(Address address) throws MessagingException {
           this.message.addRecipient(Message.RecipientType.BCC, address);
    }

    /** Add a recipient to the "BCC" field of this email.
     *  @param address the recipient's email address.
     *  @see javax.mail.Message.addRecipient().
     */
    public void addBCC(String address) throws MessagingException {
           this.message.addRecipient(Message.RecipientType.BCC, new InternetAddress(address));
    }

    /** Add a recipient to the "BCC" field of this email.
     *  @param address the recipient's email address.
     *  @param name the recipient's name.
     *  @see javax.mail.Message.addRecipient().
     */
    public void addBCC(String address, String name) throws UnsupportedEncodingException, MessagingException {
           this.message.addRecipient(Message.RecipientType.BCC, new InternetAddress(address, name));
    }

    /** Get all recipients of the email.
     *  @return all recipients of the email.
     *  @see javax.mail.getAllRecipients().
     */
    public String[] getAllRecipients() throws MessagingException {
           Address[] recipients = this.message.getAllRecipients();
           String[] res = new String[recipients.length];
           for (int i = 0; i< res.length; i++)
               res[i] = recipients[i].toString();
           return res;
    }

    /** Set the main body of the email.
     *  @param text the main text of the email.
     *  @see javax.mail.internet.MimeBodyPart.setText().
     */
    public void setMainContent(String text) throws MessagingException {
           this.mainbody.setText(text);
    }

    /** Get the main body of the email.
     *  @return the main text of the email.
     *  @see javax.mail.internet.MimeBodyPart.getContent().
     */
    public String getMainContent() throws IOException, MessagingException {
           return (String)this.mainbody.getContent();
    }

    /** Add an attachment to the email.
     *  @param text the attachment content.
     */
    public void addAttachment(String text) throws MessagingException {
           expand();
           this.attachments[count] = new MimeBodyPart();
           this.attachments[count].setText(text);
           count++;
    }

    /** Add an attachment with a specified type to the email.
     *  @param obj the attachment content.
     *  @param type the attachment type.
     */
    public void addAttachment(Object obj, String type) throws MessagingException {
           expand();
           this.attachments[count] = new MimeBodyPart();
           this.attachments[count].setContent(obj, type);
           count++;
    }

    /** Add an attachment to the email.
     *  @param dh the attachment.
     */
    public void addAttachment(DataHandler dh) throws MessagingException {
           expand();
           this.attachments[count] = new MimeBodyPart();
           this.attachments[count].setDataHandler(dh);
           this.attachments[count].setFileName(dh.getName());
           count++;
    }
    public void addAttachment(DataHandler dh, String name) throws MessagingException {
           expand();
           this.attachments[count] = new MimeBodyPart();
           this.attachments[count].setDataHandler(dh);
           this.attachments[count].setFileName(name);
           count++;
    }

    /** Add an attachment to the email.
     *  @param mp the attachment.
     */
    public void addAttachment(Multipart mp) throws MessagingException {
           expand();
           this.attachments[count] = new MimeBodyPart();
           this.attachments[count].setContent(mp);
           count++;
    }

    /** Add an attachment to the email.
     *  @param ds the attachment.
     */
    public void addAttachment(DataSource ds) throws MessagingException {
           addAttachment(new DataHandler(ds));
    }
    public void addAttachment(DataSource ds, String name) throws MessagingException {
           addAttachment(new DataHandler(ds), name);
    }

    /** Add an attachment to the email.
     *  @param url the attachment's URL.
     */
    public void addAttachment(URL url) throws MessagingException {
           addAttachment(new DataHandler(url));
    }

    /** Add an attachment to the email.
     *  @param fl the file containging the attachment.
     */
    public void addAttachment(File fl) throws MessagingException {
           addAttachment(new FileDataSource(fl));
    }
    public void addAttachment(File fl, String name) throws MessagingException {
           addAttachment(new FileDataSource(fl), name);
    }

    /** Set the sent date.
     *  @param date the sent date.
     */
    public void setSentDate(Date date) throws MessagingException {
           this.message.setSentDate(date);
    }

    /** Send the email.
    */
    public void send() throws MessagingException {
           MimeMultipart mp = new MimeMultipart();
           mp.addBodyPart(mainbody);
           for (int i = 0; i< this.count; i++)
               mp.addBodyPart(this.attachments[i]);
           this.message.setContent(mp);
           Transport.send(this.message);
    }

	   public void send(MimeMultipart mp) throws MessagingException {
           this.message.setContent(mp);
           Transport.send(this.message);
    }

     /**
      * A convenient method to send email.
      * @param subject The subject.
      * @param from The sender's email address.
      * @param to The receipients' email addresses.
      * @param body The mail content.
      * @throws MessagingException
      */
     @SuppressWarnings("rawtypes")
		public void send(String subject, String from, List to, String body) throws MessagingException, SendFailedException {
       message.setSubject(subject);
       message.setFrom(new InternetAddress(from));
       InternetAddress[] addressTo = new InternetAddress[to.size()];
       for (int i = 0; i < to.size(); i++) {
           addressTo[i] = new InternetAddress((String)to.get(i));
       }
       message.addRecipients(Message.RecipientType.TO, addressTo);
       mainbody.setText(body);
       send();

     }
		 
		 @SuppressWarnings("rawtypes")
		public void send(String subject, String from, List to, String cc, String body) throws MessagingException, SendFailedException {
       message.setSubject(subject);
       message.setFrom(new InternetAddress(from));
       InternetAddress[] addressTo = new InternetAddress[to.size()];
       for (int i = 0; i < to.size(); i++) {
           addressTo[i] = new InternetAddress((String)to.get(i));
       }
       message.addRecipients(Message.RecipientType.TO, addressTo);
		  message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
       mainbody.setText(body);
       send();

     }

		 @SuppressWarnings("rawtypes")
		public void send(String subject, String from, List to, List cc, String body) throws MessagingException, SendFailedException {
       message.setSubject(subject);
       message.setFrom(new InternetAddress(from));
       InternetAddress[] addressTo = new InternetAddress[to.size()];
       for (int i = 0; i < to.size(); i++) {
           addressTo[i] = new InternetAddress((String)to.get(i));
       }
       message.addRecipients(Message.RecipientType.TO, addressTo);

       InternetAddress[] addressCc = new InternetAddress[cc.size()];
       for (int j = 0; j < cc.size(); j++) {
           addressCc[j] = new InternetAddress((String)cc.get(j));
       }
		  message.addRecipients(Message.RecipientType.CC, addressCc);

       mainbody.setText(body);
       send();

     }

     @SuppressWarnings("rawtypes")
		public void send(String subject, String from, String fromAlias, List to, String body) throws UnsupportedEncodingException,MessagingException, SendFailedException {
       message.setSubject(subject);
       message.setFrom(new InternetAddress(from,fromAlias));
       InternetAddress[] addressTo = new InternetAddress[to.size()];
       for (int i = 0; i < to.size(); i++) {
           addressTo[i] = new InternetAddress((String)to.get(i));
       }
       message.addRecipients(Message.RecipientType.TO, addressTo);
       mainbody.setText(body);
       send();

     }


     /**
      * A convenient method to send email to a single recipient.
      * @param subject The subject.
      * @param from The sender's email address.
      * @param to The receipient's email address.
      * @param body The mail content.
      * @throws MessagingException
      */
     public void send(String subject, String from, String to, String body) throws MessagingException, SendFailedException {
       message.setSubject(subject);
       message.setFrom(new InternetAddress(from));
       InternetAddress addressTo = new InternetAddress(to);
       message.addRecipient(Message.RecipientType.TO, addressTo);
       mainbody.setText(body);
       send();

    }

     public void send(String subject, String from,String fromAlias, String to, String body) throws UnsupportedEncodingException,MessagingException, SendFailedException {
       message.setSubject(subject);
       message.setFrom(new InternetAddress(from,fromAlias));
       InternetAddress addressTo = new InternetAddress(to);
       message.addRecipient(Message.RecipientType.TO, addressTo);
       mainbody.setText(body);
       send();

    }
}
