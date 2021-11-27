package com.usoit.api.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.repository.MailVerifiedTokenRepository;
import com.usoit.api.services.MailVerificationServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailVerificationServicesImpl implements MailVerificationServices {
	
	@Autowired
	private MailVerifiedTokenRepository mailVerifiedTokenRepository;
	
	@Override
	public MailVerifiedToken getVerificationTokenByCustomerID(String publicId) {
		
		Optional<MailVerifiedToken> optional = mailVerifiedTokenRepository.findById(publicId);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
