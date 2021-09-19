package com.usoit.api.servicesimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Bank;
import com.usoit.api.repository.BankRepository;
import com.usoit.api.services.BankServices;

@Service
public class BankServicesImpl implements BankServices{
	
	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public List<Bank> getAllBank() {		
		
		return (List<Bank>) bankRepository.findAll();
	}
	
	@Override
	public boolean addBak(Bank bank) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Bank getBanByPublicId(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
