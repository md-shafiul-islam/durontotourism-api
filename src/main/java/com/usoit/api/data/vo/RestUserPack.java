package com.usoit.api.data.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestUserPack implements Serializable{

	private static final long serialVersionUID = 1L;


	private String userGemId;
	
	private String publicId;

	private RestDepartment department;

	private String name;


	private String officialEmail;

	private String personalEmail;


	private String officialPhoneNumber;

	private String personalPhoneNumber;

	private String profileIimage;


	private int approvalStatus;

	private int status;

}
