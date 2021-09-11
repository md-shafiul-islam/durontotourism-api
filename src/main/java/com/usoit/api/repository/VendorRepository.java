package com.usoit.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.Vendor;

public interface VendorRepository extends CrudRepository<Vendor, Integer>{

	public Vendor getVendorByPublicId(String publicId);

	public List<Vendor> getAllVendorByApproveStatusAndUpdateApprove(int i, int j);

}
