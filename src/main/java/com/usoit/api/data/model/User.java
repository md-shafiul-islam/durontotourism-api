package com.usoit.api.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.usoit.api.security.model.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

	
	private static final long serialVersionUID = 1110136015410098457L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "user_gen_id")
	private String userGemId;
	
	@Column(name = "pb_gen_id")
	private String publicId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Packages> packages = new ArrayList<Packages>();

	@ManyToOne
	@JoinColumn(name = "department", referencedColumnName = "id")
	private Department department;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	private List<Credential> credentials = new ArrayList<>();
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "approveUser")
	private List<Packages> approvePackages = new ArrayList<>();

	private String name;

	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "mother_name")
	private String motherName;

	@Column(name = "husband_name")
	private String husbandName;

	@Column(name = "official_email")
	private String officialEmail;

	@Column(name = "personal_email")
	private String personalEmail;

	@ManyToOne
	@JoinColumn(name = "gender", referencedColumnName = "id")
	private Gender gender;

	@Column(name = "national_Id_no")
	private String nationalIdNo;

	@Column(name = "tin_no")
	private String tinNno;

	@Column(name = "date_of_birth")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	@Column(name = "joining_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joiningDate;

	@Column(name = "emergency_contact_no1")
	private String emergencyContactNo1;

	@Column(name = "emergency_contact_no2")
	private String emergencyContactNo2;

	@ManyToOne
	@JoinColumn(name = "marital_status", referencedColumnName = "id")
	private MaritalStatus maritalStatus;

	@Column(name = "aniversary_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date aniversaryDate;

	private double salary;

	@Column(name = "mobile_allowance")
	private double mobileAllowance;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role", referencedColumnName = "id")
	private Role role;

	@Column(name = "office_location")
	private String officeLocation;

	@ManyToOne
	@JoinColumn(name = "designation", referencedColumnName = "id")
	private Designation designation;

	@Column(name = "official_phone_number")
	private String officialPhoneNumber;

	@Column(name = "personal_phone_number")
	private String personalPhoneNumber;

	@Column(name = "profile_image")
	private String profileIimage;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "account_no")
	private String accountNo;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "user_signatures")
	private String userSignaturesUrl;

	@Column(name = "resume")
	private String resumeUrl;

	@Column(name = "pledge")
	private String pledgeUrl;

	@Column(name = "application_for_job")
	private String applicationForJobUrl;

	@Column(name = "nid")
	private String nidUrl;

	@Column(name = "birth_certificate")
	private String birthCertificateUrl;

	@Column(name = "ssc_equivalent")
	private String sscEquivalentUrl;

	@Column(name = "hsc_equivalent")
	private String hscEquivalentUrl;

	@Column(name = "bachelor_honours")
	private String bachelorHonoursUrl;

	@Column(name = "masters")
	private String mastersUrl;

	@Column(name = "ca_fca_cma")
	private String caFcaCmaUrl;

	@Column(name = "pf_ca_fca_cma")
	private String pfCaFcaCmaUrl;

	@Column(name = "diploma")
	private String diplomaUrl;

	@Column(name = "employment")
	private String employmentUrl;

	@Column(name = "nationality_certificate")
	private String nationalityCertificateUrl;

	@Column(name = "job_agreement")
	private String jobAgreementUrl;

	@Column(name = "security_deed")
	private String securityDeedUrl;

	@Column(name = "appointment_letter")
	private String appointmentLetterUrl;

	@Column(name = "field_verification")
	private String fieldVerificationUrl;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserAddress> userAddresses = new ArrayList<>();

	@Column(name = "passport_no")
	private String passportNo;

	@Column(name = "approval")
	private int approvalStatus;
	
	@Column(name = "update_approve")
	private int updateApproveStatus;

	@Column(name = "status")
	private int status;

	@Column(name = "contact_name_1")
	private String contactName1;

	@Column(name = "contact_name_2")
	private String contactName2;
	
	@Column(name = "authentication_status")
	private int authenticationStatus;

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
		
		for (Credential credential : credentials) {
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
