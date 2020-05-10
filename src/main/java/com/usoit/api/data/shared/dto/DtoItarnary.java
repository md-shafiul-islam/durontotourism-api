package com.usoit.api.data.shared.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoItarnary implements Serializable{
	
	
	
	private static final long serialVersionUID = -3887357249997148108L;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("publicId")
	private String publicId;
	
	@JsonProperty("category")
	private int category;
	
	@JsonProperty("vendor")
	private String vendor;
	
	@JsonProperty("itn_country")
	private int country;
	
	@JsonProperty("city")
	private String city;

	@JsonProperty("dayOrDurations")
	private int dayOrDurations;

	@JsonProperty("sourceInfo")
	private String sourceInfo;

	@JsonProperty("heading")
	private String heading;

	@JsonProperty("description")
	private String description;

	@JsonProperty("hightLightText")
	private String hightLightText;

	@JsonProperty("hotelText")
	private String hotelText;

	@JsonProperty("includedText")
	private String includedText;

	@JsonProperty("excludedText")
	private String excludedText;

	
	@JsonProperty("sourceUrl")
	private String sourceUrl;

	@JsonProperty("sourceUrl2")
	private String sourceUrl2;
	
	@JsonProperty("expDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expDate;
}
