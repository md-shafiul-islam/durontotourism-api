package com.usoit.api.servicesimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.TransactionType;
import com.usoit.api.repository.TransactionTypeRepository;
import com.usoit.api.services.TransactionTypeServices;

@Service
public class TransectionTypeServicesImpl implements TransactionTypeServices {

	@Autowired
	private TransactionTypeRepository transactionTypaRepository;

	@Override
	public TransactionType getCodeByKey(String codeKey) {
		Optional<TransactionType> optional = transactionTypaRepository.getTransactionTypeByKey(codeKey);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
