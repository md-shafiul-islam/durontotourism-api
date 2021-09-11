package com.usoit.api.data.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPackage implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@JsonProperty("publicId")
	private String publicId;
	
	@JsonProperty("name")
	public String name;

	@JsonProperty("code")
	public String code;

	@JsonProperty("description")
	public String description;

	@JsonProperty("packIncludedText")
	public String packIncludedText;

	@JsonProperty("packExcludedText")
	public String packExcludedText;

	@JsonProperty("packHightlightText")
	public String packHightlightText;

	@JsonProperty("packageCat")
	public RestPackageCat packageCat;

	@JsonProperty("user")
	public RestUser user;

	@JsonProperty("updateUser")
	public RestUser updateUser;

	
	@JsonProperty("itarnarys")
	public List<RestItarnary> itarnarys = new ArrayList<>();

	@JsonBackReference("image_gpack_api")
	@JsonProperty("imageGalleries")
	public List<ImageGalleryAPI> imageGalleries = new ArrayList<>();

	@JsonProperty("duration")
	private RestDuration duration;
	
	@JsonProperty("highlight")
	public boolean highlight;

	@JsonProperty("price")
	public double price;

	@JsonProperty("videoUrl")
	public String videoUrl;

	@JsonProperty("date")
	public Date date;

	@JsonProperty("approvalStatus")
	public int approvalStatus;

	@JsonProperty("updateApproval")
	public int updateApproval;

	@JsonProperty("approveUser")
	public RestUser approveUser;

	@JsonBackReference("country_pa_api")
	@JsonProperty("countries")
	public List<RestCountry> countries = new ArrayList<>();
	
	private double price4Person;
	
	private double price6Person;
	
	private double price8Person;

}
