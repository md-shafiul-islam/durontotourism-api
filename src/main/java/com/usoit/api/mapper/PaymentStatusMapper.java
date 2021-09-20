package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.PaymentStatus;
import com.usoit.api.model.response.RestPaymentStatus;
import com.usoit.api.model.response.SelectOption;

public interface PaymentStatusMapper {

	public RestPaymentStatus mapRestPaymentStatusOnly(PaymentStatus paymentStatus);

	public List<RestPaymentStatus> mapAllRestPaymentStatusOnly(List<PaymentStatus> paymentStatuses);

	public List<SelectOption> mapAllRestPaymentStatusOnlyAsOption(List<PaymentStatus> allPaymentStatus);

}
