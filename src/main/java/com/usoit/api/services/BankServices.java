package com.usoit.api.services;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;

public interface BankServices {

	public BankAccount getBankAccounts();

	public BankAccountType getBankAccountType(String id);

	public BankAccount getBankAccount(String id);

}
