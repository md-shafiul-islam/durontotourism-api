package com.usoit.api.mapper.impl;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.BankMapper;
import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.response.RestBankAccount;
import com.usoit.api.model.response.RestBankAccountType;

@Service
public class BankMapperImpl implements BankMapper{

	@Override
	public RestBankAccountType mappBankAccountType(BankAccountType accountType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public RestBankAccount mappBankAccount(BankAccount account) {
		// TODO Auto-generated method stub
		return null;
	}
}
