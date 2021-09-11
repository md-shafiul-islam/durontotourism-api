package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.response.BankAccountTypeOption;

public interface BankAccountTypeServices {

	public BankAccountType getBankAccountTypeById(int id);

	public List<BankAccountTypeOption> getAccountTypeOptions();

	public List<BankAccountType> getAllAccountTypes();

}
