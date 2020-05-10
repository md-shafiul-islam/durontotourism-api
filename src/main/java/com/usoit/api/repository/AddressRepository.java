package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer>{

}
