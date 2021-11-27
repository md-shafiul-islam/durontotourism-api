package com.usoit.api.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Wallet;
import com.usoit.api.repository.WalletRepository;
import com.usoit.api.services.WalletServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WalletServicesImpl implements WalletServices {

	@Autowired
	private WalletRepository walletRepository;
	
	@Override
	public Wallet getWalletByCustomerId(int id) {
		
		Optional<Wallet> optional = walletRepository.getWalletByCustomer(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Override
	public void addWallet(Wallet wallet) {
		
		if(wallet != null) {
			walletRepository.save(wallet);
		}
		
	}

}
