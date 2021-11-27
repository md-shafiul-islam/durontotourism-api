package com.usoit.api.mapper;

import com.usoit.api.model.Customer;
import com.usoit.api.model.Passport;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ReqCustomerSignUp;
import com.usoit.api.model.response.ResEsCustomer;
import com.usoit.api.model.response.RestEsCustomer;
import com.usoit.api.model.response.RestPrfileInfo;

public interface CustomerMapper {

	public RestEsCustomer mapEsRestCustomer(Customer customer);

	public RestEsCustomer mapSignUpRestCustomer(Customer nCustomer);

	public Customer mapCustomerSignUp(ReqCustomerSignUp customerSignUp);

	public ResEsCustomer getEsCustomer(Customer customer);

	public RestPrfileInfo getCustomerPersonalInfo(Customer customer, Traveler traveler, Passport passport);

}
