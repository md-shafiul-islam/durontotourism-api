package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer>{


}
