package com.usoit.api.mapper;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.response.RestBankAccount;
import com.usoit.api.model.response.RestBankAccountType;

public interface BankMapper {

	public RestBankAccountType mappBankAccountType(BankAccountType accountType);

	public RestBankAccount mappBankAccount(BankAccount account);

}
