package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.PaymentInfo;

public interface PaymentInfRepository extends CrudRepository<PaymentInfo, Integer>{

}
