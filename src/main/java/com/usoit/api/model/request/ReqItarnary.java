package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqItarnary implements Serializable {

	private static final long serialVersionUID = 1197763714552950965L;

	
	@JsonProperty("category")
	private ReqCategory category;
	
	
	@JsonProperty("vendor")
	private ReqVendor vendor;
	
	
	@JsonProperty("country")
	private ReqCountry country;
	
	
	
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
