package com.usoit.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
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
//@MappedSuperclass
@Entity
@Table(name="user")
@Inheritance
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User implements UserDetails {

	
	protected static final long serialVersionUID = 1110136015410098457L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id;

	@Column(name = "user_gen_id")
	protected String userGemId;
	
	@Column(name = "pb_gen_id")
	protected String publicId;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "user")
	protected List<Packages> packages = new ArrayList<Packages>();
	
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "approveUser")
	protected List<Packages> approvePackages = new ArrayList<>();
	
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "updateUser")
	protected List<Packages> updatePackages = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "department", referencedColumnName = "id")
	protected Department department;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	protected List<Credential> credentials = new ArrayList<>();

	protected String name;

	@Column(name = "father_name")
	protected String fatherName;

	@Column(name = "mother_name")
	protected String motherName;

	@Column(name = "husband_name")
	protected String husbandName;

	@Column(name = "official_email")
	protected String officialEmail;

	@Column(name = "personal_email")
	protected String personalEmail;

	@ManyToOne
	@JoinColumn(name = "gender", referencedColumnName = "id")
	protected Gender gender;

	@Column(name = "national_Id_no")
	protected String nationalIdNo;

	@Column(name = "tin_no")
	protected String tinNno;

	@Column(name = "date_of_birth")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date dateOfBirth;

	@Column(name = "joining_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date joiningDate;

	@Column(name = "emergency_contact_no1")
	protected String emergencyContactNo1;

	@Column(name = "emergency_contact_no2")
	protected String emergencyContactNo2;

	@ManyToOne
	@JoinColumn(name = "marital_status", referencedColumnName = "id")
	protected MaritalStatus maritalStatus;

	@Column(name = "aniversary_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected Date aniversaryDate;

	protected double salary;

	@Column(name = "mobile_allowance")
	protected double mobileAllowance;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "id")
	protected Role role;

	@Column(name = "office_location")
	protected String officeLocation;

	@ManyToOne
	@JoinColumn(name = "designation", referencedColumnName = "id")
	protected Designation designation;

	@Column(name = "official_phone_number")
	protected String officialPhoneNumber;

	@Column(name = "personal_phone_number")
	protected String personalPhoneNumber;

	@Column(name = "profile_image")
	protected String profileIimage;

	@Column(name = "bank_name")
	protected String bankName;

	@Column(name = "account_name")
	protected String accountName;

	@Column(name = "account_no")
	protected String accountNo;

	@Column(name = "branch_name")
	protected String branchName;

	@Column(name = "user_signatures")
	protected String userSignaturesUrl;

	@Column(name = "resume")
	protected String resumeUrl;

	@Column(name = "pledge")
	protected String pledgeUrl;

	@Column(name = "application_for_job")
	protected String applicationForJobUrl;

	@Column(name = "nid")
	protected String nidUrl;

	@Column(name = "birth_certificate")
	protected String birthCertificateUrl;

	@Column(name = "ssc_equivalent")
	protected String sscEquivalentUrl;

	@Column(name = "hsc_equivalent")
	protected String hscEquivalentUrl;

	@Column(name = "bachelor_honours")
	protected String bachelorHonoursUrl;

	@Column(name = "masters")
	protected String mastersUrl;

	@Column(name = "ca_fca_cma")
	protected String caFcaCmaUrl;

	@Column(name = "pf_ca_fca_cma")
	protected String pfCaFcaCmaUrl;

	@Column(name = "diploma")
	protected String diplomaUrl;

	@Column(name = "employment")
	protected String employmentUrl;

	@Column(name = "nationality_certificate")
	protected String nationalityCertificateUrl;

	@Column(name = "job_agreement")
	protected String jobAgreementUrl;

	@Column(name = "security_deed")
	protected String securityDeedUrl;

	@Column(name = "appointment_letter")
	protected String appointmentLetterUrl;

	@Column(name = "field_verification")
	protected String fieldVerificationUrl;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	protected List<UserAddress> userAddresses = new ArrayList<>();

	@Column(name = "passport_no")
	protected String passportNo;

	@Column(name = "approval")
	protected int approvalStatus;
	
	@Column(name = "update_approve")
	protected int updateApproveStatus;

	@Column(name = "status")
	protected int status;

	@Column(name = "contact_name_1")
	protected String contactName1;

	@Column(name = "contact_name_2")
	protected String contactName2;
	
	@Column(name = "authentication_status")
	protected int authenticationStatus;
	

	@Column(name="account_non_lock") //Default value true
	protected boolean accountNonLocked;

	@Column(name="credentials_non_exp") //Default true
	protected boolean credentialsNonExpired;

	@Column(name="enabled") //Default True
	protected boolean enabled;

	@Column(name="account_non_exp") //Default True
	protected boolean accountNonExpired;

	@Transient
	protected MultipartFile pfImageFile;

	@Transient
	protected MultipartFile signImgFile;

	@Transient
	protected MultipartFile resumeFile;

	@Transient
	protected MultipartFile pledgeFile;

	@Transient
	protected MultipartFile applicationForJobFile;

	@Transient
	protected MultipartFile nidFile;

	@Transient
	protected MultipartFile sscEquivalentFile;

	@Transient
	protected MultipartFile hscEquivalentFile;

	@Transient
	protected MultipartFile bachelorHonoursFile;

	@Transient
	protected MultipartFile mastersFile;

	@Transient
	protected MultipartFile birthCertificateFile;

	@Transient
	protected MultipartFile caFcaCmaFile;

	@Transient
	protected MultipartFile pfCaFcaCmaFile;

	@Transient
	protected MultipartFile diplomaFile;

	@Transient
	protected MultipartFile employmentFile;

	@Transient
	protected MultipartFile nationalityCertificateFile;

	@Transient
	protected MultipartFile jobAgreementFile;

	@Transient
	protected MultipartFile securityDeedFile;

	@Transient
	protected MultipartFile appointmentLetterFile;

	@Transient
	protected MultipartFile fieldVerificationFile;



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new Authority(role.getName()));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
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