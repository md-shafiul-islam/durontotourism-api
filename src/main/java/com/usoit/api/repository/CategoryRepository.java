package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>{

}
