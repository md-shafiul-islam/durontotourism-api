package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqItarnaryOnPack implements Serializable{

	
	private static final long serialVersionUID = -837311759425884742L;

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
