package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.TransactionType;

public interface TransactionTypeRepository extends CrudRepository<TransactionType, Integer>{

	@Query
	public Optional<TransactionType> getTransactionTypeByKey(String codeKey);

}
