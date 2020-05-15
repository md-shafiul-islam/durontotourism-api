package com.usoit.api.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqContactPerson implements Serializable{

	
	private static final long serialVersionUID = 818957571403827215L;

	private String name;
	
	private String phoneNo;
	
	private String conPhoneCode;
	
	private int country;
		
	private String email;
	
	
	
}
