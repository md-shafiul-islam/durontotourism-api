package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.Wallet;


public interface WalletRepository extends CrudRepository<Wallet, Integer>{

	@Query
	public Optional<Wallet> getWalletByCustomer(int id);

}
