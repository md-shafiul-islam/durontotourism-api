package com.usoit.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.TempVendor;

public interface TempVendorRepository extends CrudRepository<TempVendor, Integer>{

	public List<TempVendor> getAllTempVendorByPublicId(String pubId);

	public TempVendor getTempVendorByPublicIdAndValidStatus(String pubId, int i);

}
