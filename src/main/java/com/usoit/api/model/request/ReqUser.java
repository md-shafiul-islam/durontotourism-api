package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqUser implements Serializable {

	
	private static final long serialVersionUID = -108024697804956601L;

	@JsonProperty("id")
	private int id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("fatherName")
	private String fatherName;

	@JsonProperty("motherName")
	private String motherName;

	@JsonProperty("husbandName")
	private String husbandName;

	@JsonProperty("officialEmail")
	private String officialEmail;

	@JsonProperty("personalEmail")
	private String personalEmail;

	@JsonProperty("gender")
	private int gender;

	@JsonProperty("nationalIdNo")
	private String nationalIdNo;

	@JsonProperty("tinNno")
	private String tinNno;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("dateOfBirth")
	private Date dateOfBirth;

	@JsonProperty("joiningDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joiningDate;

	@JsonProperty("emergencyContactNo1")
	private String emergencyContactNo1;

	@JsonProperty("emergencyContactNo2")
	private String emergencyContactNo2;

	@JsonProperty("maritalStatus")
	private int maritalStatus;
	
	@JsonProperty("department")
	private int department;

	@JsonProperty("aniversaryDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date aniversaryDate;

	@JsonProperty("salary")
	private double salary;

	@JsonProperty("mobileAllowance")
	private double mobileAllowance;

	@JsonProperty("role")
	private int role;

	@JsonProperty("officeLocation")
	private String officeLocation;

	@JsonProperty("designation")
	private int designation;

	@JsonProperty("officialPhoneNumber")
	private String officialPhoneNumber;

	@JsonProperty("personalPhoneNumber")
	private String personalPhoneNumber;

	@JsonProperty("profileIimage")
	private String profileIimage;

	@JsonProperty("bankName")
	private String bankName;

	@JsonProperty("accountName")
	private String accountName;

	@JsonProperty("accountNo")
	private String accountNo;

	@JsonProperty("branchName")
	private String branchName;

	@JsonProperty("userSignaturesUrl")
	private String userSignaturesUrl;

	@JsonProperty("resumeUrl")
	private String resumeUrl;

	@JsonProperty("pledgeUrl")
	private String pledgeUrl;

	@JsonProperty("applicationForJobUrl")
	private String applicationForJobUrl;

	@JsonProperty("nidUrl")
	private String nidUrl;

	@JsonProperty("birthCertificateUrl")
	private String birthCertificateUrl;

	@JsonProperty("sscEquivalentUrl")
	private String sscEquivalentUrl;

	@JsonProperty("hscEquivalentUrl")
	private String hscEquivalentUrl;

	@JsonProperty("bachelorHonoursUrl")
	private String bachelorHonoursUrl;

	@JsonProperty("mastersUrl")
	private String mastersUrl;

	@JsonProperty("caFcaCmaUrl")
	private String caFcaCmaUrl;

	@JsonProperty("pfCaFcaCmaUrl")
	private String pfCaFcaCmaUrl;

	@JsonProperty("diplomaUrl")
	private String diplomaUrl;

	@JsonProperty("employmentUrl")
	private String employmentUrl;

	@JsonProperty("nationalityCertificateUrl")
	private String nationalityCertificateUrl;

	@JsonProperty("jobAgreementUrl")
	private String jobAgreementUrl;

	@JsonProperty("securityDeedUrl")
	private String securityDeedUrl;

	@JsonProperty("appointmentLetterUrl")
	private String appointmentLetterUrl;

	@JsonProperty("fieldVerificationUrl")
	private String fieldVerificationUrl;

	@JsonProperty("userAddresses")
	private List<ReqAddress> userAddresses = new ArrayList<>();

	@JsonProperty("passportNo")
	private String passportNo;

	@JsonProperty("contactName1")
	private String contactName1;

	@JsonProperty("contactName2")
	private String contactName2;
	
	@JsonProperty("authenticationStatus")
	private boolean authenticationStatus;




}
