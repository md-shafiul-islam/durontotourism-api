package com.usoit.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class ReqCategory {

	@JsonProperty("id")
	private int id;
	
	private String name;
	
	private String description;
}
