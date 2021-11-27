package com.usoit.api.servicesimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.response.BankAccountTypeOption;
import com.usoit.api.repository.BankAccountTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BankAccountTypeServices implements com.usoit.api.services.BankAccountTypeServices {

	@Autowired
	private BankAccountTypeRepository bankAccountTypeRepository;
	
	@Override
	public BankAccountType getBankAccountTypeById(int id) {
		
		Optional<BankAccountType> optional = bankAccountTypeRepository.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		
		return null;
	}
	
	@Override
	public List<BankAccountType> getAllAccountTypes() {
		
		return (List<BankAccountType>) bankAccountTypeRepository.findAll();
	}
	
	@Override
	public List<BankAccountTypeOption> getAccountTypeOptions() {
		
		List<BankAccountTypeOption> accountTypeOptions = new ArrayList<>();
		
		List<BankAccountType> accountTypes = (List<BankAccountType>) bankAccountTypeRepository.findAll();
		
		for (int i = 0; i < accountTypes.size(); i++) {
			if(accountTypes.get(i) != null) {
				BankAccountTypeOption accountTypeOption = new BankAccountTypeOption();
				
				accountTypeOption.setLabel(accountTypes.get(i).getName());
				accountTypeOption.setValue(accountTypes.get(i).getId());
				
				accountTypeOptions.add(accountTypeOption);
			}
		}
		
		return accountTypeOptions;
	}

}
