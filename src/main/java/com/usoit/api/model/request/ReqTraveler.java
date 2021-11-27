package com.usoit.api.model.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqTraveler {
	
	private String id;
	private String firstName;	
	private String lastName;
	private String nationality;
	private String gender;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateOfBirth;
	
	private String passportNo;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date passportExpiry;
	private String passportIssuCuntry;
	private String passportAttach;
	
	private int status;

	private String email;
	
	private String phoneCode;
	
	private String phoneNo;
	
	

}
