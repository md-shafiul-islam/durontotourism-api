package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Department;
import com.usoit.api.model.VendorCategory;
import com.usoit.api.repository.VendorCategoryRepository;
import com.usoit.api.services.VendorCategoryServices;

@Service
public class VendorCategoryServicesImpl implements VendorCategoryServices{

	
	@Autowired
	private VendorCategoryRepository vendorCategoryRepository;

	@Override
	public boolean isKeyExist(String key) {
		
		return false;
	}
	
	@Override
	public VendorCategory getVendorCatById(int vcID) {
		
		if (vcID > 0) {
			
			Optional<VendorCategory> optional = vendorCategoryRepository.findById(vcID);
			
			if (optional != null) {
				
				return optional.get();
			}
		}
		
		return null;
	}

	@Override
	public List<VendorCategory> getAllVendorCats() {
		
		return (List<VendorCategory>) vendorCategoryRepository.findAll();
	}

	@Override
	public long getCount() {
		return vendorCategoryRepository.count();
	}
	
	
	
}
