package com.usoit.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.PaymentInfo;

@Repository
public interface PaymentInfRepository extends CrudRepository<PaymentInfo, Integer>{

	@Query
	public PaymentInfo getPaymentInfoByPublicId(String key);

}
