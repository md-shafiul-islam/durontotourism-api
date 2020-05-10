package com.usoit.api.model.request;


import java.io.Serializable;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUserAddress implements Serializable{

	
	private static final long serialVersionUID = 1L;

	private int id;
	
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
	private ReqCountry country;
	
	@Column(name = "country_code")
	private String countryCode;
		
	
}
