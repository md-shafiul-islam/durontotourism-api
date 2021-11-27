package com.usoit.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer>{

	public Optional<Country> getCountryByIsoCode(String code);


}
