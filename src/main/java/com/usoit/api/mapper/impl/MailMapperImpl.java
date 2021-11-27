package com.usoit.api.mapper.impl;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.MailMapper;
import com.usoit.api.model.MailVerifiedToken;

@Service
public class MailMapperImpl implements MailMapper {
	
	@Override
	public String getMailContent(MailVerifiedToken verificationToken) {
		
		if(verificationToken != null) {
			String tokenStr = "";
			
			tokenStr += "/token?id="+verificationToken.getId()+"&key="+verificationToken.getToken()+"&dc="+verificationToken.getDigitCode();
			
			return tokenStr;
		}
		
		return null;
	}

}
