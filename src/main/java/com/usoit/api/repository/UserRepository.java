package com.usoit.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	@Query
	public User getUserByOfficialPhoneNumber(String officialPhoneNumber); // 

	@Query
	public User getUserByOfficialEmail(String officialEmail);

	@Query
	public User getUserByPersonalEmail(String personalEmail);

	@Query
	public User getUserByPersonalPhoneNumber(String personalPhoneNumber);

	@Query
	public User getUserByPublicId(String pubId);
	


}