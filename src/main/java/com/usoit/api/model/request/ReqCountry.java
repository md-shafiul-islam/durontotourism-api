package com.usoit.api.model.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCountry implements Serializable{

	
	private static final long serialVersionUID = 7490368397691311081L;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("isoCode")
	private String isoCode;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("niceName")
	private String niceName;
	
	@JsonProperty("iso3Code")
	private String iso3Code;
	
	@JsonProperty("numCode")
	private int numCode;
	
	@JsonProperty("dialOrPhoneCode")
	private String dialOrPhoneCode;

	
	
	
}
