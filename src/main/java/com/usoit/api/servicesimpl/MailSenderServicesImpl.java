package com.usoit.api.servicesimpl;

import java.io.File;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.Customer;
import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.services.MailSenderServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableConfigurationProperties
@Service
public class MailSenderServicesImpl implements MailSenderServices {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${usermail.verifide.link}")
	protected String mailVerifiedLink;
	
	@Value("${usermail.mail.from}")
	protected String fromMail;

//	@Autowired
//	private SimpleMailMessage simpleMailMessage;

	@Override
	public void sendMail(String mailReceiver, String subject, String body) {

		try {

////					spring.mail.properties.mail.smtp.auth=true
////					spring.mail.properties.mail.smtp.starttls.enable=true
//
//			JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//			javaMailSender.setHost("smtp.gmail.com");
//			javaMailSender.setPort(587);
//			javaMailSender.setUsername("shafiul2014bd@gmail.com");
//			javaMailSender.setPassword("01725^*^)@(");

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(mailReceiver);
			mailMessage.setSubject(subject);
			mailMessage.setText(body);
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will send a pre-configured message
	 */
//	public void sendPreConfiguredMail(String message) {
//		SimpleMailMessage mailMessage = new SimpleMailMessage(simpleMailMessage);
//		mailMessage.setText(message);
//		mailSender.send(mailMessage);
//	}

	public void getTestMailSendLink() {
		log.info("Hosted Link, " + mailVerifiedLink);

	}

	public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mimeMessage.setFrom(new InternetAddress(fromMail));
				mimeMessage.setSubject(subject);
				mimeMessage.setText(body);

				FileSystemResource file = new FileSystemResource(new File(fileToAttach));
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.addAttachment("logo.jpg", file);
			}
		};

		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}
	}

	@Override
	public boolean sendMailWithInlineResources(Customer customer, String subject, MailVerifiedToken token,
			String fileToAttach) {
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(customer.getPrimaryEmail()));
				mimeMessage.setFrom(new InternetAddress(fromMail));
				mimeMessage.setSubject(subject);

				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				log.info("Template Began ... "+fromMail);
				helper.setText("<html><body>" + getEmailTempelte(customer, token) + "</body></html>", true);
				log.info("Template End ");

				if (fileToAttach != null) {
					FileSystemResource res = new FileSystemResource(new File(fileToAttach));
					helper.addInline("dto_file", res);
				}

			}
		};

		try {
			log.debug("User Resources Mail Sending Befor...");
			mailSender.send(preparator);
			log.debug("User Resources Mail Send");
			return true;
		} catch (MailSendException ex) {
			log.debug("User Resources Mail Send Failed " + ex.getMessage());
			ex.printStackTrace();

			return false;

		} catch (MailException ex) {
			log.debug("User Resources Mail Send Failed " + ex.getMessage());
			ex.printStackTrace();

			return false;
		}
	}

	private String getEmailTempelte(Customer customer, MailVerifiedToken token) {
		String content = "Dear " + customer.getFullName() + ",<br />" + "Your OTP is  <b>" + token.getDigitCode()
				+ "</b> provided for your mail address verification. Please do not share this with anyone. <br /><br />"
				+ "If you did not attempt the request, please contact our customer care immediately. <br /><br />"
				+ "Thank you for choosing us as your primary travel partner.<br />" + "<br />" + "Best Regards,<br />"
				+ "<b>Duronto Tourism</b>";

		return content;

	}

}
