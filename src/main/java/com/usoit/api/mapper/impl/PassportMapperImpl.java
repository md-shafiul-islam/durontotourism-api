package com.usoit.api.mapper.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.usoit.api.mapper.PassportMapper;
import com.usoit.api.model.Passport;
import com.usoit.api.model.request.ReqTraveler;
import com.usoit.api.model.response.RespPassport;

@Service
public class PassportMapperImpl implements PassportMapper {

	@Override
	public Passport mapPassport(ReqTraveler travelerInf) {

		if (travelerInf != null) {
			Passport passport = new Passport();
			passport.setActive(true);
			passport.setAttach(travelerInf.getPassportAttach());
			passport.setCountry(travelerInf.getPassportIssuCuntry());
			passport.setDate(new Date());
			passport.setExpiryDate(travelerInf.getPassportExpiry());
			passport.setNumber(travelerInf.getPassportNo());
			return passport;
		}

		return null;
	}

	@Override
	public List<RespPassport> mapRespPasportOnly(List<Passport> passports) {

		if (passports != null) {
			List<RespPassport> respPassports = new ArrayList<>();

			for (Passport passport : passports) {

				if (passport != null) {

					RespPassport respPassport = new RespPassport();

					respPassport.setActive(passport.isActive());
					respPassport.setAttach(passport.getAttach());
					respPassport.setCountry(passport.getCountry());
					respPassport.setDate(passport.getDate());
					respPassport.setExpiryDate(passport.getExpiryDate());
					respPassport.setNumber(passport.getNumber());

					respPassports.add(respPassport);
				}
			}

			return respPassports;
		}

		return null;
	}

	@Override
	public RespPassport mapActivePassportOnly(List<Passport> passports) {
		RespPassport respPassport = null;
		if (passports != null) {

			for (Passport passport : passports) {

				if (passport != null) {
					if (passport.isActive()) {

						respPassport = new RespPassport();
						respPassport.setActive(passport.isActive());
						respPassport.setAttach(passport.getAttach());
						respPassport.setCountry(passport.getCountry());
						respPassport.setDate(passport.getDate());
						respPassport.setExpiryDate(passport.getExpiryDate());
						respPassport.setNumber(passport.getNumber());
						
						break;
					}

				}
			}

		}

		return respPassport;
	}
}
