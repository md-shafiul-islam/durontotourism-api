package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.TermsAndConditions;

public interface TermsAndConditionsServices {

	public List<TermsAndConditions> getAllTermAndConds();

	public long getCount();

	public boolean save(TermsAndConditions termAndConditions);

	public TermsAndConditions getTermAndCondsByPubId(String pubId);

	public boolean update(TermsAndConditions termAndConditions);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
