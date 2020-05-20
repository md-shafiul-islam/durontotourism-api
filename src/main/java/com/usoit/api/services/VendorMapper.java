package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.TempVendor;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.vo.RestVendorDetails;
import com.usoit.api.data.vo.RestVendorUserId;
import com.usoit.api.model.request.ReqVendor;

public interface VendorMapper {

	public List<RestVendorUserId> getRestVendorsUID(List<Vendor> vendors);

	public Vendor getVendor(ReqVendor reqVendor);

	public ReqVendor getReqVendor(Vendor vendor);

	public TempVendor getTempVendor(ReqVendor reqVendor);

	public Vendor getTempVendorToVendor(TempVendor tempVendor);

	public RestVendorDetails getRestVendorDetails(Vendor vendor);

}
