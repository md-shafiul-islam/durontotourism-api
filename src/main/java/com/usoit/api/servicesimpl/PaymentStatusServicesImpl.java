package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.PaymentStatus;
import com.usoit.api.repository.PayemtStatusRepository;
import com.usoit.api.services.PaymentStatusServices;

@Service
public class PaymentStatusServicesImpl implements PaymentStatusServices {
	
	@Autowired
	private PayemtStatusRepository payemtStatusRepository;

	@Override
	public PaymentStatus getPaymentStatusById(int i) {
		
		Optional<PaymentStatus> optional = payemtStatusRepository.findById(i);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

	@Override
	public List<PaymentStatus> getAllPaymentStatus() {		
		return (List<PaymentStatus>) payemtStatusRepository.findAll();
	}

}
