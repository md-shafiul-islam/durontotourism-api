package com.usoit.api.data.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestVendorDetails implements Serializable{

	
	private static final long serialVersionUID = 5310659707562206544L;

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
	
	private List<RestPaymentInfo> paymentInfos;
	
	private String comPhoneNo;
	
	private String phoneCode;
}
