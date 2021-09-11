package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.PaymentInfo;

public interface PaymentInfServices {

	public List<PaymentInfo> getAllPayInf();

	public long getCount();

	public boolean update(PaymentInfo paymentInfo);

	public PaymentInfo getPayInfById(int id);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
