package com.usoit.api.services;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.PaymentStatus;

public interface PaymentStatusServices extends CrudRepository<PaymentStatus, Integer>{

	public PaymentStatus getPaymentStatusById(int i);

}
