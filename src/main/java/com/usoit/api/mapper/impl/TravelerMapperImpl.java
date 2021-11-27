package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.mapper.PassportMapper;
import com.usoit.api.mapper.TravelerMapper;
import com.usoit.api.model.Passport;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ReqTraveler;
import com.usoit.api.model.response.RespTraveler;

@Service
public class TravelerMapperImpl implements TravelerMapper {

	@Autowired
	private PassportMapper passportMapper;

	@Override
	public Traveler mapTraveler(ReqTraveler travelerInf) {

		if (travelerInf != null) {
			Traveler traveler = new Traveler();

			traveler.setDate(new Date());
			traveler.setDateOfBirth(travelerInf.getDateOfBirth());
			traveler.setFirstName(travelerInf.getFirstName());
			traveler.setGender(travelerInf.getGender());
			traveler.setLastName(travelerInf.getLastName());
			traveler.setNationality(travelerInf.getNationality());

			Passport passport = passportMapper.mapPassport(travelerInf);
			if (passport != null) {
				passport.setTraveler(traveler);
				traveler.getPassports().add(passport);
			}

			return traveler;
		}

		return null;
	}

	@Override
	public Traveler mapCreateTraveler(ReqTraveler travelerInf) {
		Traveler traveler = new Traveler();
		return null;
	}

	@Override
	public List<RespTraveler> getRespTravelersWithActivePassport(List<Traveler> travelers) {
		
		if(travelers != null) {
			List<RespTraveler> respTravelers = new ArrayList<>();
			for (Traveler traveler : travelers) {
				
				RespTraveler respTraveler = getRespTravelerWithOutPassport(traveler);
				
				if(respTraveler != null) {
					respTraveler.setRespPassport(passportMapper.mapActivePassportOnly(traveler.getPassports()));
					respTravelers.add(respTraveler);
				}
			}
			
			return respTravelers;
		}
		
		return null;
	}

	@Override
	public List<RespTraveler> getRespTraveler(List<Traveler> travelers) {

		if (travelers != null) {
			List<RespTraveler> respTravelers = new ArrayList<>();

			for (Traveler traveler : travelers) {
				RespTraveler respTraveler = getRespTravelerWithOutPassport(traveler);
				if (respTraveler != null) {
					respTraveler.setPassports(passportMapper.mapRespPasportOnly(traveler.getPassports()));
					respTravelers.add(respTraveler);
				}
			}

			return respTravelers;
		}

		return null;
	}

	private RespTraveler getRespTravelerWithOutPassport(Traveler traveler) {
		if (traveler != null) {
			RespTraveler respTraveler = new RespTraveler();

			respTraveler.setDate(traveler.getDate());
			respTraveler.setDateOfBirth(traveler.getDateOfBirth());
			respTraveler.setFirstName(traveler.getFirstName());
			respTraveler.setGender(traveler.getGender());
			respTraveler.setLastName(traveler.getLastName());
			respTraveler.setNationality(traveler.getNationality());

			respTraveler.setPhoneCode(traveler.getPhoneCode());
			respTraveler.setPhoneNo(traveler.getPhoneNo());
			respTraveler.setPublicId(traveler.getPublicId());
			return respTraveler;
		}
		return null;
	}
}
