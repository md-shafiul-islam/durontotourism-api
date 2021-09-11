package com.usoit.api.model.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDepartment implements Serializable{
	
	
	private static final long serialVersionUID = 1190654167084464105L;

	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	

}
