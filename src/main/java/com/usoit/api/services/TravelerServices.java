package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Customer;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ReqTraveler;

public interface TravelerServices {

	public boolean updatePrimaryTraveler(int id, ReqTraveler travelerInf);

	public boolean updateTraveler(ReqTraveler travelerInf);

	public boolean addTraveler(Traveler traveler);

	public Traveler getPrimaryTravelerUsingCustomerID(int id);

	public boolean createGuestTraveler(ReqTraveler travelerInf, Customer customer);

	public List<Traveler> getTravelersByCustomerId(int id);

}
