package com.usoit.api.data.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.usoit.api.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestUserDetails implements Serializable{

	
	private static final long serialVersionUID = -6585797241396957019L;
	
	private String userGemId;
	
	private String publicId;

	private RestDepartment department;

	private String name;

	private String fatherName;

	private String motherName;

	private String husbandName;

	private String officialEmail;

	private String personalEmail;

	private RestGender gender;

	private String nationalIdNo;

	private String tinNno;

	private Date dateOfBirth;

	private Date joiningDate;

	private String emergencyContactNo1;

	private String emergencyContactNo2;

	private RestMaritalStatus maritalStatus;

	private Date aniversaryDate;

	private double salary;

	private double mobileAllowance;

	private RestRoleOption role;

	private String officeLocation;

	private RestDesignation designation;

	private String officialPhoneNumber;

	private String personalPhoneNumber;

	private String profileIimage;

	private String bankName;

	private String accountName;

	private String accountNo;

	private String branchName;

	private String userSignaturesUrl;

	private String resumeUrl;

	private String pledgeUrl;

	private String applicationForJobUrl;

	private String nidUrl;

	private String birthCertificateUrl;

	private String sscEquivalentUrl;

	private String hscEquivalentUrl;

	private String bachelorHonoursUrl;

	private String mastersUrl;

	private String caFcaCmaUrl;

	private String pfCaFcaCmaUrl;

	private String diplomaUrl;

	private String employmentUrl;

	private String nationalityCertificateUrl;

	private String jobAgreementUrl;

	private String securityDeedUrl;

	private String appointmentLetterUrl;

	private String fieldVerificationUrl;

	private List<RestUserAddress> userAddresses = new ArrayList<>();

	private String passportNo;

	private int approvalStatus;
	
	private int updateApproveStatus;

	private int status;

	private String contactName1;

	private String contactName2;
	
	private int authenticationStatus;

}
