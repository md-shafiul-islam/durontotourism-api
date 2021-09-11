package com.usoit.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_temp")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTemp implements Serializable{
	
	
	private static final long serialVersionUID = 4908006086955287812L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@JsonProperty("publicId")
	@Column(name="public_id")
	private String publicId;

	@JsonProperty("name")
	@Column(name="name")
	private String name;

	@JsonProperty("fatherName")
	@Column(name="father_name")
	private String fatherName;

	@JsonProperty("motherName")
	@Column(name="mother_name")
	private String motherName;

	@JsonProperty("husbandName")
	@Column(name="husband_name")
	private String husbandName;

	@JsonProperty("officialEmail")
	@Column(name="official_email")
	private String officialEmail;

	@JsonProperty("personalEmail")
	@Column(name="personal_email")
	private String personalEmail;

	@JsonProperty("gender")
	@Column(name="gender")
	private int gender;

	@JsonProperty("nationalIdNo")
	@Column(name="national_idno")
	private String nationalIdNo;

	@JsonProperty("tinNo")
	@Column(name="tin_no")
	private String tinNno;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date_of_birth")
	@JsonProperty("dateOfBirth")
	private Date dateOfBirth;

	@JsonProperty("joiningDate")
	@Column(name="joining_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joiningDate;

	@JsonProperty("emergencyContactNo1")
	@Column(name="emergency_contact_no_1")
	private String emergencyContactNo1;

	@JsonProperty("emergencyContactNo2")
	@Column(name="emergency_contact_no_2")
	private String emergencyContactNo2;

	@JsonProperty("maritalStatus")
	@Column(name="marital_status")
	private int maritalStatus;
	
	@JsonProperty("department")
	@Column(name="department")
	private int department;

	@JsonProperty("aniversaryDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="aniversary_date")
	private Date aniversaryDate;

	@JsonProperty("salary")
	@Column(name="salary")
	private double salary;

	@JsonProperty("mobileAllowance")
	@Column(name="mobile_allowance")
	private double mobileAllowance;

	@JsonProperty("officeLocation")
	@Column(name="office_location")
	private String officeLocation;

	@JsonProperty("designation")
	@Column(name="designation")
	private int designation;

	@JsonProperty("officialPhoneNumber")
	@Column(name="official_phone_number")
	private String officialPhoneNumber;

	@JsonProperty("personalPhoneNumber")
	@Column(name="personal_phone_number")
	private String personalPhoneNumber;

	@JsonProperty("profileIimage")
	@Column(name="profile_image")
	private String profileIimage;

	@JsonProperty("bankName")
	@Column(name="bank_name")
	private String bankName;

	@JsonProperty("accountName")
	@Column(name="account_name")
	private String accountName;

	@JsonProperty("accountNo")
	@Column(name="account_no")
	private String accountNo;

	@JsonProperty("branchName")
	@Column(name="branch_name")
	private String branchName;

	@JsonProperty("userSignaturesUrl")
	@Column(name="user_signatures_url")
	private String userSignaturesUrl;

	@JsonProperty("resumeUrl")
	@Column(name="resume_url")
	private String resumeUrl;

	@JsonProperty("pledgeUrl")
	@Column(name="pledge_url")
	private String pledgeUrl;

	@JsonProperty("applicationForJobUrl")
	@Column(name="application_for_job_url")
	private String applicationForJobUrl;

	@JsonProperty("nidUrl")
	@Column(name="nid_url")
	private String nidUrl;

	@JsonProperty("birthCertificateUrl")
	@Column(name="birth_certificate_url")
	private String birthCertificateUrl;

	@JsonProperty("sscEquivalentUrl")
	@Column(name="ssc_equivalent_url")
	private String sscEquivalentUrl;

	@JsonProperty("hscEquivalentUrl")
	@Column(name="hsc_equivalent_url")
	private String hscEquivalentUrl;

	@JsonProperty("bachelorHonoursUrl")
	@Column(name="bachelor_honours_url")
	private String bachelorHonoursUrl;

	@JsonProperty("mastersUrl")
	@Column(name="masters_url")
	private String mastersUrl;

	@JsonProperty("caFcaCmaUrl")
	@Column(name="cafcacma_url")
	private String caFcaCmaUrl;

	@JsonProperty("pfCaFcaCmaUrl")
	@Column(name="pfcafcacma_url")
	private String pfCaFcaCmaUrl;

	@JsonProperty("diplomaUrl")
	@Column(name="diploma_url")
	private String diplomaUrl;

	@JsonProperty("employmentUrl")
	@Column(name="employment_url")
	private String employment_url;

	@JsonProperty("nationalityCertificateUrl")
	@Column(name="nationality_certificate_url")
	private String nationalityCertificateUrl;

	@JsonProperty("jobAgreementUrl")
	@Column(name="job_agreement_url")
	private String jobAgreementUrl;

	@JsonProperty("securityDeedUrl")
	@Column(name="security_deed_url")
	private String securityDeedUrl;

	@JsonProperty("appointmentLetterUrl")
	@Column(name="appointment_letter_url")
	private String appointmentLetterUrl;

	@JsonProperty("fieldVerificationUrl")
	@Column(name="field_verification_url")
	private String fieldVerificationUrl;

	@OneToMany(mappedBy = "userTemp")
	@JsonProperty("userAddresses")
	private List<UserAddressTemp> userAddresses = new ArrayList<>();

	@JsonProperty("passportNo")
	@Column(name="passport_no")
	private String passportNo;

	@JsonProperty("contactName1")
	@Column(name="contact_name_1")
	private String contactName1;

	@JsonProperty("contactName2")
	@Column(name="contact_name_2")
	private String contactName2;
	
	@JsonProperty("role")
	@Column(name = "role")
	private int role;
	
	@Column(name = "data_status")
	private int lifelStatus;

	
}
