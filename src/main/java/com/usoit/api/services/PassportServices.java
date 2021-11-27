package com.usoit.api.services;

import com.usoit.api.model.Passport;

public interface PassportServices {

	public Passport getActivePassportUsingTravelerID(int id);

}
