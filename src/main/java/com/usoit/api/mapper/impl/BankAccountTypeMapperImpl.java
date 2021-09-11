package com.usoit.api.mapper.impl;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.BankAccountTypeMapper;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.response.RestBankAccountType;

@Service
public class BankAccountTypeMapperImpl implements BankAccountTypeMapper{

	
	@Override
	public RestBankAccountType mapAccountRestTypeOnly(BankAccountType bankAccountType) {
		
		if(bankAccountType != null) {
			RestBankAccountType accountType = new RestBankAccountType();
			
			accountType.setId(bankAccountType.getId());
			accountType.setName(bankAccountType.getName());
			accountType.setNumValue(bankAccountType.getNumValue());
			accountType.setValue(bankAccountType.getValue());
			
			return accountType;
		}
		
		return null;
	}
}
