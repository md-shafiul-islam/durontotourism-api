package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.model.PaymentStatus;

public interface PayemtStatusRepository extends CrudRepository<PaymentStatus, Integer>{

}
