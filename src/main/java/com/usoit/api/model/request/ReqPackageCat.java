package com.usoit.api.model.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqPackageCat implements Serializable{

	private static final long serialVersionUID = -6192798324220632979L;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
		
	
}
