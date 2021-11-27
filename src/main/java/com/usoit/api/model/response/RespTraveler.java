package com.usoit.api.model.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespTraveler {

	private String publicId;
	
	private List<RespPassport> passports;
	
	private RespPassport respPassport;

	private String nationality;
	
	private String firstName;
	
	private String lastName;
	
	private String phoneCode;
	
	private String phoneNo;
	
	private String gender;
	
	private boolean primary;
	
	private Date dateOfBirth;	
	
	private Date date;
	
}
