package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.mapper.BankMapper;
import com.usoit.api.model.Bank;
import com.usoit.api.model.request.ReqBank;
import com.usoit.api.model.response.BankOption;
import com.usoit.api.model.response.RestBank;
import com.usoit.api.model.response.SelectOption;
import com.usoit.api.services.UtilsServices;

@Service
public class BankMapperImpl implements BankMapper {
	
	@Autowired
	private UtilsServices utilsServices;
	
	@Override
	public List<SelectOption> mapBankOptions(List<Bank> banks) {
		List<SelectOption> options = new ArrayList<>();
		
		if(banks != null) {
			for (Bank bank : banks) {
				SelectOption option = new SelectOption();
				option.setLabel(bank.getName());
				option.setValue(bank.getPublicId());
				options.add(option);
			}
		}
		
		return options;
	}
	
	@Override
	public RestBank mapRestBank(Bank bank) {
		if(bank != null) {
			RestBank restBank = new RestBank();
			
			restBank.setContactInf(bank.getContatInf());
			restBank.setId(bank.getPublicId());
			restBank.setLogoUrl(bank.getLogoUrl());
			restBank.setName(bank.getName());
			
			return restBank;
		}
		return null;
	}

	@Override
	public List<RestBank> mapAllBank(List<Bank> banks) {
		if(banks != null) {
			List<RestBank> restBanks = new ArrayList<>();
			for (Bank bank : banks) {
				RestBank restBank = mapRestBank(bank);
				if(restBank != null) {
					restBanks.add(restBank);
				}
				
			}
			
			return restBanks;
		}
		return null;
	}

	@Override
	public Bank mapBank(ReqBank reqBank) {
		
		if(reqBank != null) {
			Bank bank = new Bank();
			bank.setName(reqBank.getName());
			bank.setLogoUrl(reqBank.getLogoUrl());
			bank.setContatInf(reqBank.getContactInf());
			bank.setPublicId(utilsServices.getGeneralPublicId());
			return bank;
		}
		
		return null;
	}

	@Override
	public List<BankOption> getAllBankOption(List<Bank> allBank) {

		if (allBank != null) {
			List<BankOption> options = new ArrayList<>();
			for (Bank bank : allBank) {
				BankOption option = getEachBankOption(bank);
				if (option != null) {
					options.add(option);
				}
			}
			return options;
		}

		return null;
	}

	@Override
	public BankOption getEachBankOption(Bank bank) {
		
		if(bank != null) {
			
			BankOption bankOption = new BankOption();
			
			bankOption.setLabel(bank.getName());
			bankOption.setValue(bank.getPublicId());
			
			return bankOption;
		}
		
		return null;
	}

}
