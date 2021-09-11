package com.usoit.api.data.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestItarnary implements Serializable {

	private static final long serialVersionUID = 1197763714552950965L;

	@JsonProperty("publicId")
	private String publicId;
	
	@JsonProperty("category")
	private RestCategory category;
	
	
	@JsonProperty("vendor")
	private RestVendor vendor;
	
	
	@JsonProperty("country")
	private RestCountry country;
	
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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expDate;

}
