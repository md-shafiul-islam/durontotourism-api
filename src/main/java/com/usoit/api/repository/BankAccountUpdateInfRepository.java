package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.BankAccountUpdateInf;

public interface BankAccountUpdateInfRepository extends CrudRepository<BankAccountUpdateInf, Integer>{

	public Optional<BankAccountUpdateInf> getBankAccountUpdateInfByBankIdAndActive(String bankId, boolean active);

	
}
