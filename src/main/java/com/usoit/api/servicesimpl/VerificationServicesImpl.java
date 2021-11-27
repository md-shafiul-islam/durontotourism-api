package com.usoit.api.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.model.PhoneVerificationToken;
import com.usoit.api.repository.MailVerivicationTokenRepository;
import com.usoit.api.repository.PhoneVerificationTokenRepository;
import com.usoit.api.services.VerificationServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VerificationServicesImpl implements VerificationServices{

	@Autowired
	private MailVerivicationTokenRepository mailVerivicationTokenRepository;
	
	@Autowired
	private PhoneVerificationTokenRepository phoneVerificationTokenRepository;
	
	@Override
	public MailVerifiedToken getMailVerificationTokenById(String id) {
		
		if(id != null) {
			Optional<MailVerifiedToken> optional = mailVerivicationTokenRepository.findById(id);
			
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		
		return null;
	}
	
	@Override
	public PhoneVerificationToken getPhoneVerificationTokenById(String id) {
		
		if(id != null) {
			Optional<PhoneVerificationToken> optional = phoneVerificationTokenRepository.findById(id);
			
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
}
