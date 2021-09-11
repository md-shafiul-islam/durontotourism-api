package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
