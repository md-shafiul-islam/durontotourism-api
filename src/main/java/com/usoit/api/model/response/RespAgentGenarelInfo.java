package com.usoit.api.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespAgentGenarelInfo {

	private String applicantName;
	
	private String publicId;

	private String id;

	private String email;

	private String phoneCode;

	private String phoneNo;

	private String photo;
	
	private Date since; 
}
