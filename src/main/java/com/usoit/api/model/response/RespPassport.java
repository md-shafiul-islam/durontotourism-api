package com.usoit.api.model.response;

import java.util.Date;

import com.usoit.api.model.Traveler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPassport {

	private RespTraveler traveler;
	
	private String country;
	
	private String number;
		
	private String attach;
	
	private boolean active;	
	
	private Date expiryDate;
	
	private Date date;
	
}
