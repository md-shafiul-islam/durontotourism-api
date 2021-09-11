package com.usoit.api.mapper;

import com.usoit.api.model.PaymentStatus;
import com.usoit.api.model.Status;
import com.usoit.api.model.response.RestPaymentStatus;
import com.usoit.api.model.response.RestStatus;

public interface StatusMapper {

	public RestStatus mapRestStatus(Status status);

	public RestPaymentStatus mapRestPaymentStatus(PaymentStatus paymetStatus);

}
