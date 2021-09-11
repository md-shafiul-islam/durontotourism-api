package com.usoit.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.UserTemp;

public interface TempUserRepository extends CrudRepository<UserTemp, Integer> {

	@Query
	public UserTemp getUserTempByPublicId(String pubId);

	
}
