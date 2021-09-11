package com.usoit.api.data.vo;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCategory implements Serializable{
	
	
	private static final long serialVersionUID = -9066091946549887897L;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
		
}
