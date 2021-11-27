package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.WithdrawType;
import com.usoit.api.repository.WalletWithDrawalRepository;
import com.usoit.api.services.WithDarawalTypeServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WithDarawalTypeServicesImpl implements WithDarawalTypeServices{

	@Autowired
	private WalletWithDrawalRepository walletWithDrawalRepository;
	
	@Override
	public WithdrawType getWithDrawTypeById(int id) {
		if(id > 0) {
			Optional<WithdrawType> optional = walletWithDrawalRepository.findById(id);
			
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
	
	@Override
	public WithdrawType getWithDrawTypeByValue(String withdrawType) {
		
		Optional<WithdrawType> optional = walletWithDrawalRepository.getWithdrawTypeByValue(withdrawType);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}
	
	@Override
	public List<WithdrawType> getAllWithDrawType() {
		return (List<WithdrawType>) walletWithDrawalRepository.findAll();
	}
}
