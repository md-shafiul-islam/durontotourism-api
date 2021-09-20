package com.usoit.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.usoit.api.model.PaymentStatus;

@Service
public interface PaymentStatusServices {

	public PaymentStatus getPaymentStatusById(int i);

	public List<PaymentStatus> getAllPaymentStatus();
	

}
