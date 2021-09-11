package com.usoit.api.mapper;

import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.response.RestBankAccountType;

public interface BankAccountTypeMapper {

	public RestBankAccountType mapAccountRestTypeOnly(BankAccountType bankAccountType);

}
