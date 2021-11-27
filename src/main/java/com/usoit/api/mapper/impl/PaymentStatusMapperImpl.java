package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.PaymentStatusMapper;
import com.usoit.api.model.PaymentStatus;
import com.usoit.api.model.response.RestPaymentStatus;
import com.usoit.api.model.response.SelectOption;

@Service
public class PaymentStatusMapperImpl implements PaymentStatusMapper {

	@Override
	public RestPaymentStatus mapRestPaymentStatusOnly(PaymentStatus paymentStatus) {

		if(paymentStatus != null) {
			
			RestPaymentStatus status = new RestPaymentStatus();
			
			status.setId(paymentStatus.getId());
			status.setName(paymentStatus.getName());
			
			return status;
		}
		return null;
	}
	
	@Override
	public List<RestPaymentStatus> mapAllRestPaymentStatusOnly(List<PaymentStatus> paymentStatuses) {
		
		if(paymentStatuses != null) {
			
			List<RestPaymentStatus> list = new ArrayList<>();
			
			for (PaymentStatus paymentStatus : paymentStatuses) {
				RestPaymentStatus status = mapRestPaymentStatusOnly(paymentStatus);
				if(status != null) {
					list.add(status);
				}
			}
			
			return list;
		}
		
		return null;
	}
	
	@Override
	public List<SelectOption> mapAllRestPaymentStatusOnlyAsOption(List<PaymentStatus> allPaymentStatus) {
		
		if(allPaymentStatus != null) {
			List<SelectOption> options = new ArrayList<>();
			
			for (PaymentStatus paymentStatus : allPaymentStatus) {
				SelectOption option = mapSelectOption(paymentStatus);
				
				if(option != null) {
					options.add(option);
				}
			}
			
			return options;
		}
		
		return null;
	}

	private SelectOption mapSelectOption(PaymentStatus paymentStatus) {
		
		if(paymentStatus != null) {
			SelectOption option = new SelectOption();
			
			option.setLabel(paymentStatus.getName());
			option.setValue(Integer.toString(paymentStatus.getId()));
			
			return option;
		}
		
		return null;
	}
}
