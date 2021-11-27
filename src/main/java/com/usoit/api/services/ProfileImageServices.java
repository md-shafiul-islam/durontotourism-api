package com.usoit.api.services;

import com.usoit.api.model.Customer;
import com.usoit.api.model.ProfileImage;

public interface ProfileImageServices {

	public ProfileImage getProfileImageByCustomer(Customer customer);

}
