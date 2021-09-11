package com.usoit.api.mapper.impl;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.StatusMapper;
import com.usoit.api.model.PaymentStatus;
import com.usoit.api.model.Status;
import com.usoit.api.model.response.RestPaymentStatus;
import com.usoit.api.model.response.RestStatus;

@Service
public class StatusMapperImpl implements StatusMapper {

	@Override
	public RestStatus mapRestStatus(Status status) {

		if (status != null) {

			RestStatus restStatus = new RestStatus();

			restStatus.setId(status.getId());
			restStatus.setName(status.getName());
			restStatus.setNumValue(status.getNumValue());
			restStatus.setValue(status.getValue());

			return restStatus;
		}

		return null;
	}

	@Override
	public RestPaymentStatus mapRestPaymentStatus(PaymentStatus paymetStatus) {

		if (paymetStatus != null) {

			RestPaymentStatus restStatus = new RestPaymentStatus();

			restStatus.setId(paymetStatus.getId());
			restStatus.setName(paymetStatus.getName());

			return restStatus;
		}

		return null;
	}

}
