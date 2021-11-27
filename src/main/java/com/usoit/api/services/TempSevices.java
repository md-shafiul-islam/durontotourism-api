package com.usoit.api.services;

import com.usoit.api.model.TempVendor;

public interface TempSevices {

	public boolean save(TempVendor tempVendor);

	public TempVendor getTempVendorByPublicId(String pubId);

	public TempVendor getValidTempVendorByPublicId(String pubId);

	public boolean getUpdateValidation(String pubId);

	public boolean getUpdateRejectAction(String pubId);

}
