package controllers;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Represents a controller which take care of notifying students on course
 * successfully registered.
 * 
 * @author
 *
 */
public class notificationController {
	/**
	 * A method used to send email to student.
	 * 
	 * @param email
	 * student's email address.
	 * @param courseCode
	 * course code of the course registered.
	 * @param index
	 * index of course code registered
	 * 
	 */
	public void notifyByEmail(String email, String courseCode, int index) {
		
		final String sendingusername = "durby1998@gmail.com"; // username to be added
		final String sendingpassword = "1998durby"; // password to be added
		
		try {
			Properties props = new Properties();// email setup here
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.port", "465");
			Session emailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sendingusername, sendingpassword);
				}
			});
			
			MimeMessage msg = new MimeMessage(emailSession); // create email messaging object
			//msg.setFrom(new InternetAddress("cz2002sender@hotmail.com"));// setting of email content and parameters here
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // to be added an email address
			msg.setSubject(courseCode + " Waitlist notification");
			msg.setText("Hello! You have been successfully registered to " + courseCode + " , index " + index);
			// DO YOU WANT A SEND DATE??
			Transport.send(msg);// sending of message
			System.out.println("Email successfully sent to " + email);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
