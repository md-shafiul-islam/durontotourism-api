package com.usoit.api.services;

import com.usoit.api.model.PhoneVerificationToken;

public interface SMSSenderServices {

	public boolean sendSMS(PhoneVerificationToken phoneToken);

}
