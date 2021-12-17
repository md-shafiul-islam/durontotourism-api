package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.Bank;
import com.usoit.api.model.request.ReqBank;
import com.usoit.api.model.response.BankOption;
import com.usoit.api.model.response.RestBank;
import com.usoit.api.model.response.SelectOption;

public interface BankMapper {

	public List<RestBank> mapAllBank(List<Bank> banks);

	public Bank mapBank(ReqBank reqBank);

	public List<BankOption> getAllBankOption(List<Bank> allBank);
	
	public BankOption getEachBankOption(Bank bank);

	public RestBank mapRestBank(Bank bank);

	public List<SelectOption> mapBankOptions(List<Bank> banks);

}
