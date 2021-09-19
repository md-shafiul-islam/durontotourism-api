package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Bank;

public interface BankServices {

	public List<Bank> getAllBank();

	public boolean addBak(Bank bank);

	public Bank getBanByPublicId(String id);

}
