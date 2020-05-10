package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.PaymentInfo;

public interface PaymentInfServices {

	public List<PaymentInfo> getAllPayInf();

	public long getCount();

	public boolean update(PaymentInfo paymentInfo);

	public PaymentInfo getPayInfById(int id);

}
