package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.PrivacyPolicy;

public interface PrivacyPolicyServices {

	public List<PrivacyPolicy> getAllPrivacyPolicy();

	public long getCount();

	public boolean update(PrivacyPolicy termprivacyPolicy);

	public PrivacyPolicy getPrivacyPolicyByPubId(String pubId);

	public boolean save(PrivacyPolicy termprivacyPolicy);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
