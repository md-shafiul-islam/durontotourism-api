package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.AccessType;

@Repository
public interface AccessTypeRepository extends CrudRepository<AccessType, Integer>{

	@Query
	public AccessType getAccessTypeByPublicId(String key);

}
