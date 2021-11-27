package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ReqTraveler;
import com.usoit.api.model.response.RespTraveler;

public interface TravelerMapper {

	public Traveler mapTraveler(ReqTraveler travelerInf);

	public Traveler mapCreateTraveler(ReqTraveler travelerInf);

	public List<RespTraveler> getRespTraveler(List<Traveler> travelers);

	public List<RespTraveler> getRespTravelersWithActivePassport(List<Traveler> travelersByCustomerId);

}
