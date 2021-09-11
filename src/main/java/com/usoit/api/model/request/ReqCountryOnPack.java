package com.usoit.api.model.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqCountryOnPack implements Serializable {

	private static final long serialVersionUID = 8906870433090817379L;

	@JsonProperty("id")
	private int id;

	@JsonProperty("index")
	private double index;

}
