package jk28_web;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class JavaMailTest {
	@Test
	public void testJavaMail() throws MessagingException{
		Properties props = new Properties();
		props.put("mail.stmp.host", "smtp.163.com");
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		Address address = new InternetAddress("18337103130@163.com");
		message.setFrom(address);
		
		Address toAddress = new InternetAddress("itheima14@163.com");
		message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
		
		message.setSubject("hello world");
		message.setText("hello world11111");
		
		message.saveChanges();
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.163.com","","");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
}
