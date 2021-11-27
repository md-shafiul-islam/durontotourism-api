package com.usoit.api.mapper;

import java.util.List;

import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.model.Country;
import com.usoit.api.model.response.RespCountry;
import com.usoit.api.model.response.SelectOption;

public interface CountryMapper {

	public RestCountry mappCountry(Country country);

	public List<SelectOption> getCountryOption(List<Country> allCountries);

	public RespCountry getRestCountry(Country country);

}
