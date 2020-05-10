package com.usoit.api.data.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.usoit.api.security.model.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAPI implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2977057772493879911L;

	private int id;

	private String userGemId;

	private List<RestPackage> packages = new ArrayList<>();

	private RestDepartment department;

	private List<CredentialAPI> credentials = new ArrayList<>();


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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date aniversaryDate;

	private double salary;

	private double mobileAllowance;

	private RestRole role;

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

	private List<UserAddressAPI> userAddresses = new ArrayList<>();

	private String passportNo;

	private int approvalStatus;

	private int status;

	private String contactName1;

	private String contactName2;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new Authority(role.getName()));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		
		for (CredentialAPI credential : credentials) {
			if (credential.getStatus() == 1) {
				
				return credential.getPassword();
			}
		}
		
		return null;
	}

	@Override
	public String getUsername() {
		
		return personalEmail;
	}

}
