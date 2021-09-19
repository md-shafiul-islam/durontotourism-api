package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.WithdrawType;

public interface WalletWithDrawalRepository extends CrudRepository<WithdrawType, Integer>{

	
	@Query
	public Optional<WithdrawType> getWithdrawTypeByValue(String value);

}
