package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.Bank;
import com.usoit.api.repository.BankRepository;
import com.usoit.api.services.BankServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BankServicesImpl implements BankServices {

	@Autowired
	private BankRepository bankRepository;

	@Override
	public List<Bank> getAllBank() {

		return (List<Bank>) bankRepository.findAll();
	}

	@Override
	public boolean addBak(Bank bank) {

		if (bank != null) {
			bankRepository.save(bank);
			if (bank.getId() > 0) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Bank getBanByPublicId(String id) {
		Optional<Bank> optional = bankRepository.getBankByPublicId(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
