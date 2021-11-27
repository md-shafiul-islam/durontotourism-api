package com.usoit.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespCountry {

	@JsonProperty("id")
	private int id;

	@JsonProperty("isoCode")
	private String isoCode;

	@JsonProperty("name")
	private String name;

	@JsonProperty("iso3Code")
	private String iso3Code;

	@JsonProperty("dialOrPhoneCode")
	private String dialOrPhoneCode;	
	

}
