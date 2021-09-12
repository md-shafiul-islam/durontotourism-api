package com.usoit.api.mapper;

import com.usoit.api.model.Customer;
import com.usoit.api.model.response.RestEsCustomer;

public interface CustomerMapper {

	public RestEsCustomer mapEsRestCustomer(Customer customer);

}
