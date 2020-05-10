package com.usoit.api.data.shared.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoImageGallery implements Serializable{
	
	
	private static final long serialVersionUID = 5887029841269709171L;

	@JsonProperty("id")
	private int id;

	@JsonProperty("srcUrl")
	private String srcUrl;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("altTag")
	private String altTag;
	
	@JsonProperty("location")
	private String location;
}
