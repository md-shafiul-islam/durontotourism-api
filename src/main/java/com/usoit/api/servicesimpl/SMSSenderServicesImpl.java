package com.usoit.api.servicesimpl;

import org.springframework.stereotype.Service;

import com.usoit.api.model.PhoneVerificationToken;
import com.usoit.api.services.SMSSenderServices;

@Service
public class SMSSenderServicesImpl implements SMSSenderServices {

	@Override
	public boolean sendSMS(PhoneVerificationToken phoneToken) {
		// TODO Auto-generated method stub
		return false;
	}

}
