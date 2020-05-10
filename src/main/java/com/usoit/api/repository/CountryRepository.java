package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Country;

public interface CountryRepository extends CrudRepository<Country, Integer>{

}
