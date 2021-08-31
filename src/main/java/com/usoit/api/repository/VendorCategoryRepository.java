package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.VendorCategory;

@Repository
public interface VendorCategoryRepository extends CrudRepository<VendorCategory, Integer>{

	

}
