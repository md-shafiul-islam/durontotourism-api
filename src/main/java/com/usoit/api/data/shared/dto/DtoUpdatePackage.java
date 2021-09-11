package com.usoit.api.data.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.usoit.api.model.request.ReqCountryOnPack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUpdatePackage implements Serializable{

	private static final long serialVersionUID = 1L;
	
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
	private List<DtoItarnary> itarnarys = new ArrayList<>();
	
	
	@JsonProperty("imageGalleries")
	private List<DtoImageGallery> imageGalleries = new ArrayList<>();
	
	
	
	@JsonProperty("price")
	private String price;

	@JsonProperty("videoUrl")
	private String videoUrl;
	
	@JsonProperty("duration")
	private int duration;
	
	
	@JsonProperty("countries")
	private List<ReqCountryOnPack> countries = new ArrayList<>();
	
	

}
