package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

	@Query
	public Optional<Customer> getCustomerByPrimaryEmail(String primaryEmail);

	@Query
	public Optional<Customer> getCustomerByPhoneNo(String phoneNo);

	@Query
	public Optional<Customer> getCustomerByPublicId(String publicId);

	@Query
	public Optional<Customer> getCustomerByPhoneNoAndPhoneCode(String phoneNo, String code);

}
