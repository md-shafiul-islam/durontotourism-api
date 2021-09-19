package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.BankMapper;
import com.usoit.api.model.Bank;
import com.usoit.api.model.request.ReqBank;
import com.usoit.api.model.response.BankOption;
import com.usoit.api.model.response.RestBank;

@Service
public class BankMapperImpl implements BankMapper {

	@Override
	public RestBank maoRestBank(Bank bank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RestBank> mapAllBank(List<Bank> banks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bank mapBank(ReqBank reqBank) {
		// TODO Auto-generated method stub
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
			bankOption.setValue(Integer.toString(bank.getId()));
			
			return bankOption;
		}
		
		return null;
	}

}
