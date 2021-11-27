package com.usoit.api.services;

import com.usoit.api.model.Customer;
import com.usoit.api.model.User;

public interface HelperAuthenticationServices {

	public User getCurrentUser();

	public Customer getCurrentCustomer();

}
