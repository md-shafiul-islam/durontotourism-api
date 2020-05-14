package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.vo.RestVendorUserId;

public interface VendorMapper {

	public List<RestVendorUserId> getRestVendorsUID(List<Vendor> vendors);

}
