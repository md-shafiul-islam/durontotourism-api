package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Vendor;

import net.bytebuddy.asm.Advice.Return;

public interface VendorServices {

	public boolean save(Vendor vendor);

	public Vendor getVendorById(int id);

	public boolean update(Vendor vendor);

	public List<Vendor> getAllVendors();

	public long getCount();

	public List<Vendor> getAllPanding();

	public Vendor getVendorByPublicId(String publicId);

	public boolean approveVendorByPublicID(String pubId);

	public boolean rejectVendorByPublicID(String pubId);

	public boolean approveUpdateVendor(Vendor vendor);

	public List<Vendor> getAllUpdatePendinVendors();

	/**
	 * {@link Return All Rejected Vendors}
	 */
	public List<Vendor> getAllRejectVendor();

	public boolean updateRquestTaken(String publicId);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);


}
