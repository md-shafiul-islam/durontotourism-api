package com.usoit.api.services;

import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.model.PhoneVerificationToken;

public interface VerificationServices {

	public MailVerifiedToken getMailVerificationTokenById(String id);

	public PhoneVerificationToken getPhoneVerificationTokenById(String id);

}
