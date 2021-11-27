package com.usoit.api.services;

import com.usoit.api.model.MailVerifiedToken;

public interface MailVerificationServices {

	public MailVerifiedToken getVerificationTokenByCustomerID(String publicId);

}
