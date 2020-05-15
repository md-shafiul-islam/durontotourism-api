package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.vo.RestVendorUserId;
import com.usoit.api.model.request.ReqVendor;

public interface VendorMapper {

	public List<RestVendorUserId> getRestVendorsUID(List<Vendor> vendors);

	public Vendor getVendor(ReqVendor reqVendor);

	public ReqVendor getReqVendor(Vendor vendor);

}
