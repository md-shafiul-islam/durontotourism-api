package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Vendor;

public interface VendorServices {

	public boolean save(Vendor vendor);

	public Vendor getVendorById(int id);

	public boolean update(Vendor vendor);

	public List<Vendor> getAllVendors();

	public long getCount();

	public List<Vendor> getAllPanding();

	public Vendor getVendorByPublicId(String publicId);

}
