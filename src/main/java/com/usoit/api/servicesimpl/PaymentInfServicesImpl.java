package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Department;
import com.usoit.api.model.PaymentInfo;
import com.usoit.api.repository.PaymentInfRepository;
import com.usoit.api.services.PaymentInfServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentInfServicesImpl implements PaymentInfServices{

	@Autowired
	private PaymentInfRepository paymentInfRepository;
	
	@Override
	public boolean isKeyExist(String key) {

		if(key != null) {
			PaymentInfo option = paymentInfRepository.getPaymentInfoByPublicId(key);
			
			if(option != null) {
				option = null;
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public List<PaymentInfo> getAllPayInf() {
		
		return (List<PaymentInfo>) paymentInfRepository.findAll();
	}

	@Override
	public long getCount() {
		return paymentInfRepository.count();
	}

	@Override
	public boolean update(PaymentInfo paymentInfo) {
		
		if (paymentInfo.getId() > 0) {
			
			paymentInfRepository.save(paymentInfo);
			return true;
		}
		
		return false;
	}

	@Override
	public PaymentInfo getPayInfById(int id) {
		
		if (id > 0) {
			
			Optional<PaymentInfo> optional = paymentInfRepository.findById(id);
			
			if (optional != null) {
				return optional.get();
			}
		}
		
		return null;
	}

}
