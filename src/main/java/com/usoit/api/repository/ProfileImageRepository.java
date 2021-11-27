package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.Customer;
import com.usoit.api.model.ProfileImage;


public interface ProfileImageRepository extends CrudRepository<ProfileImage, Integer>{

	@Query
	public Optional<ProfileImage> getProfileImageByCustomerAndActive(Customer customer, boolean b);

}
