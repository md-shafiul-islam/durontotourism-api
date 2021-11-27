package com.usoit.api.services;

import com.usoit.api.model.Customer;
import com.usoit.api.model.MailVerifiedToken;

public interface MailSenderServices {

	public void sendMail(String mailReceiver, String subject, String body);

	public boolean sendMailWithInlineResources(Customer customer, String subject, MailVerifiedToken token, String fileName);

	public void getTestMailSendLink();

}
