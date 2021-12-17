package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.Bank;

public interface BankRepository extends CrudRepository<Bank, Integer>{

	@Query
	public Optional<Bank> getBankByPublicId(String id);

	
}
