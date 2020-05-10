package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqUpdateUser implements Serializable{

	private static final long serialVersionUID = -5368938714886193752L;

	@JsonProperty("publicId")
	private String publicId;

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
	
	@JsonProperty("role")
	private int role;

	@JsonProperty("userAddresses")
	private List<ReqAddress> userAddresses = new ArrayList<>();

	@JsonProperty("passportNo")
	private String passportNo;

	@JsonProperty("contactName1")
	private String contactName1;

	@JsonProperty("contactName2")
	private String contactName2;
	
	@Transient
	private MultipartFile pfImageFile;

	@Transient
	private MultipartFile signImgFile;

	@Transient
	private MultipartFile resumeFile;

	@Transient
	private MultipartFile pledgeFile;

	@Transient
	private MultipartFile applicationForJobFile;

	@Transient
	private MultipartFile nidFile;

	@Transient
	private MultipartFile sscEquivalentFile;

	@Transient
	private MultipartFile hscEquivalentFile;

	@Transient
	private MultipartFile bachelorHonoursFile;

	@Transient
	private MultipartFile mastersFile;

	@Transient
	private MultipartFile birthCertificateFile;

	@Transient
	private MultipartFile caFcaCmaFile;

	@Transient
	private MultipartFile pfCaFcaCmaFile;

	@Transient
	private MultipartFile diplomaFile;

	@Transient
	private MultipartFile employmentFile;

	@Transient
	private MultipartFile nationalityCertificateFile;

	@Transient
	private MultipartFile jobAgreementFile;

	@Transient
	private MultipartFile securityDeedFile;

	@Transient
	private MultipartFile appointmentLetterFile;

	@Transient
	private MultipartFile fieldVerificationFile;


}
