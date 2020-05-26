package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPackage implements Serializable{


	
	private static final long serialVersionUID = 781063690772882453L;
	
	private String publicId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("code")
	private String code;

	@JsonProperty("description")
	private String description;

	@JsonProperty("packIncludedText")
	private String packIncludedText;

	@JsonProperty("packExcludedText")
	private String packExcludedText;

	@JsonProperty("packHightlightText")
	private String packHightlightText;

	@JsonProperty("packageCat")
	private int packageCat;

	
	@JsonProperty("itarnarys")
	private List<ReqItarnaryOnPack> itarnarys = new ArrayList<>();
	
	
	@JsonProperty("imageGalleries")
	private List<ReqImageGallery> imageGalleries = new ArrayList<>();
	
	
	
	@JsonProperty("price")
	private String price;

	@JsonProperty("videoUrl")
	private String videoUrl;
	
	@JsonProperty("duration")
	private int duration;
	
	
	@JsonProperty("countries")
	private List<ReqCountryOnPack> countries = new ArrayList<>();
	
	private double price4Person;
	
	private double price6Person;
	
	private double price8Person;
	
	// Getter Methods

}
