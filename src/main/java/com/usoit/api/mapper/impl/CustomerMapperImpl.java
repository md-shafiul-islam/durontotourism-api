package com.usoit.api.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.mapper.CustomerMapper;
import com.usoit.api.model.Customer;
import com.usoit.api.model.response.RestEsCustomer;
import com.usoit.api.services.HelperServices;

@Service
public class CustomerMapperImpl implements CustomerMapper {

	@Autowired
	private HelperServices helperServices;

	@Override
	public RestEsCustomer mapEsRestCustomer(Customer customer) {

		if (customer != null) {

			RestEsCustomer restCustomer = new RestEsCustomer();

//			restCustomer.setEmail(customer.getPersonalEmail());
//			restCustomer.setFirstName(customer.getName());
//			restCustomer.setPhone(customer.getPersonalPhoneNumber());
			restCustomer.setPublicId(customer.getPublicId());

			if (customer.getWallet() != null) {
				restCustomer.setWalletAmount(helperServices.getDoubleToString(customer.getWallet().getTotalAmount()));
			}
			return restCustomer;
		}

		return null;
	}
}
