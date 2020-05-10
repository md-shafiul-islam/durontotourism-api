package com.usoit.api.data.vo;


import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressAPI {

	
	private int id;
	
	
	private UserAPI user;
	
	private String title;
	
	@JsonProperty("house")
	private String house;
	
	@JsonProperty("village")
	private String village;
	
	@JsonProperty("street")
	private String street;
	
	@JsonProperty("zip_code")
	private String zipCode;
	
	@JsonProperty("city")
	private String city;

	@JsonProperty("country")
	private RestCountry country;
	
	@Column(name = "country_code")
	private String countryCode;
		
	
}
