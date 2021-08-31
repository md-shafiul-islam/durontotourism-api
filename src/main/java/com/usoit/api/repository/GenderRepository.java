package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.Gender;

public interface GenderRepository extends CrudRepository<Gender, Integer>{

}
