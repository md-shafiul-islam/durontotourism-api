package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.WalletWithDraw;

public interface WalletWithDrawRepository extends CrudRepository<WalletWithDraw, Integer>{

	@Query
	public Optional<WalletWithDraw> getWalletWithDrawByPublicId(String publicId);


}
