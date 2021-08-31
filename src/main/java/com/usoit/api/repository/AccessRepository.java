package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Access;

@Repository
public interface AccessRepository extends CrudRepository<Access, Integer>{
	
	@Query
	public Access getAccessByPublicId(String key);

}
