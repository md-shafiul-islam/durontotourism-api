package com.usoit.api.data.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageGalleryAPI implements Serializable{

	private static final long serialVersionUID = 8147065886685971775L;

	@JsonProperty("srcUrl")
	private String srcUrl;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("altTag")
	private String altTag;
	
	@JsonProperty("location")
	private String location;
	
	
	
	
	
}
