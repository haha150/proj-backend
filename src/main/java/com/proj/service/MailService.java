package com.proj.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	private static String MAILFROM = "alisymeri@hotmail.com";
	private static String PASSWORD = "";
	private static String LINK = "https://localhost/unbook?key=";

	private static String[] MAILCC = {"alisymeri@hotmail.com"};

	@Async
	public synchronized void sendMail(String mailTo, String uuid, String node, String bookedUntil) {
		String host = "smtp.live.com";

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.port", "587");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.host", host);

		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MAILFROM, PASSWORD);
			}
		});
		
		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(MAILFROM));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
			if (!mailTo.equals(MAILCC[0])) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(MAILCC[0]));
			}
			message.setSubject("Booked node: " + node);
			String link = LINK + uuid;
			message.setText("You have successfully booked node: " + node + " until: " + bookedUntil
					+ "\n\nTo unbook the node, use this link: " + link);
			System.out.println(link);
			System.out.println("You have successfully booked node: " + node + " until: " + bookedUntil
					+ "\n\nTo unbook the node, use this link: " + link);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
