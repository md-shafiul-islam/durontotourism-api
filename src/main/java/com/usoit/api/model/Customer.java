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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "client_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "customer")
public class Customer implements UserDetails {

	// DTC-00-0000-0000
	// Customer Gen ID DTA(MN)(YN)(RND4N)
	// Company name 2 digit, User Category 1 digit, Month 2 Digit, Year 4 Digit,
	// Random number 4 digit

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	@OneToMany(mappedBy = "customer")
	private List<Traveler> travelers; 
	
	@OneToMany(mappedBy = "customer")
	private List<ProfileImage> profileImages;

	@Column(name = "public_id")
	private String publicId;

	@Column(name = "genareted_id")
	private String genId;

	@Column(name = "primary_email")
	private String primaryEmail;

	@Column(name = "phone_code")
	private String phoneCode;

	@Column(name = "phone_no")
	private String phoneNo;

	private String photo;

	@Column(name = "client_type", insertable = false, updatable = false)
	private String clientType;

	@OneToOne(mappedBy = "customer")
	private Wallet wallet;

	@OneToMany(mappedBy = "customer")
	private List<WalletWithDraw> walletWithDraws;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<CustomerCredential> credentials = new ArrayList<>();

	@Column(name = "account_non_exp")
	private boolean accountNonExpired;

	@Column(name = "account_non_lock")
	private boolean accountNonLocked;

	@Column(name = "credentials_non_exp")
	private boolean credentialsNonExpired;

	@Column(name = "enable")
	private boolean enabled;

	@Column(name = "create_date")
	private Date date;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "date_group")
	private Date dateGroup;
	
	private boolean verified;
	
	@Column(name="email_verified")
	private boolean emailVerified;
	
	@Column(name="phone_verified")
	private boolean phoneVerified;
	

	@Transient
	private String pwd;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new ClientAuthority(clientType));
		return authorities;
	}

	@Override
	public String getPassword() {

		for (CustomerCredential credential : credentials) {
			if (credential.isActive()) {
				return credential.getPassword();
			}
		}
		return null;
	}

	@Override
	public String getUsername() {

		return primaryEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
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

	public String getFullName() {

		String fullName = "";
		Traveler tvlr = null;
		if(travelers != null) {			
			for (Traveler traveler : travelers) {
				if(traveler.isPrimary()) {
					tvlr = traveler;
				}
			}
		}
		if(tvlr != null) {
			fullName = tvlr.getFirstName() != null ? tvlr.getFirstName() : "";
			fullName = tvlr.getLastName() != null ? fullName += " " + tvlr.getLastName() : "";
		}	
		

		return fullName;
	}

}
