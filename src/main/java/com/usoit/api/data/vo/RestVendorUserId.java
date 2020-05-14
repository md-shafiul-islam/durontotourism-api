package com.usoit.api.data.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestVendorUserId {


	@JsonProperty("publicId")
	private String publicId;
	
	@JsonProperty("vGenId")
	private String vGenId;
	
	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("website")
	private String website;
	
	private List<RestContactPerson> contactPersons;
	
	private List<RestAddress> addresses;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("userPublicId")
	private String userPublicId;
	
	@JsonProperty("restVendorCategory")
	private RestVendorCategory restVendorCategory;
	
	
}
