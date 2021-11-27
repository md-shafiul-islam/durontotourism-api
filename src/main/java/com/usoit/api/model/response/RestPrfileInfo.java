package com.usoit.api.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPrfileInfo {
	
	private String id;	
	
	private String publicId;
	
	private String imageUrl;
	
	private String firstName;	
	
	private String lastName;
	
	private String gender;
	
	private RespCountry nationality;
	
	private Date dateOfBirth;
	
	private String passportNo;
	
	private RespCountry passportIssuingCountry;
	
	private Date passportExpiry;
	
	private String phoneNo;
	
	private String email;
	
	private String passportAttach;
	
	private boolean emailVerified;
	
	private boolean phoneVerified;
	
	private Date since;
	
	
}
