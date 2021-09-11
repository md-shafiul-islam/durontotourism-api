package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{
	

}
