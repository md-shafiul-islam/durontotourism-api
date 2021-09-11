package com.usoit.api.data.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestPackageCat implements Serializable{

	private static final long serialVersionUID = -6192798324220632979L;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
		
	
}
