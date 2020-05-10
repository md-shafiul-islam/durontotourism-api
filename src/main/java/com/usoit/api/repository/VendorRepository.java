package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Vendor;

public interface VendorRepository extends CrudRepository<Vendor, Integer>{

	public Vendor getVendorByPublicId(String publicId);

}
