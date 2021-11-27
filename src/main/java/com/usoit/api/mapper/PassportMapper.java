package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.model.Passport;
import com.usoit.api.model.request.ReqTraveler;
import com.usoit.api.model.response.RespPassport;

public interface PassportMapper {

	public Passport mapPassport(ReqTraveler travelerInf);

	public List<RespPassport> mapRespPasportOnly(List<Passport> passports);

	public RespPassport mapActivePassportOnly(List<Passport> passports);

}
