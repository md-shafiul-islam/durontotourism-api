package com.usoit.api.mapper;

import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.model.Country;

public interface CountryMapper {

	public RestCountry mappCountry(Country country);

}
