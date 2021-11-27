package com.usoit.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="agent_owner")
public class AgentOwner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="public_id")
	private String publicId;
	
	@ManyToOne
	@JoinColumn(name="agent", referencedColumnName = "id")
	private Agent agent;
	
	@Column(name="name")
	private String name;
	
	@Column(name="phone_no")
	private String phone;
	
	@Column(name="phone_code")
    private String code;
    
	@Column(name="email")
	private String email;
	
	@Column(name="house_village_no")
    private String houseNoOrVillage;
	
	@Column(name="roadname_orno")
    private String roadNameOrNo;
	
	@Column(name="postal_code")
    private String postalCode;
	
	@Column(name="police_station")
    private String policeStation;
	
	@Column(name="district")
    private String district;
	
	@OneToOne
	@JoinColumn(name="country", referencedColumnName = "id")
    private Country country;
	
	@Column(name="national_id_no")
    private String nationalIdNo;
		
	@Column(name="passport_no")
    private String passportNo;
	
	@Column(name="passpor_url")
    private String passportAttach;
	
	@Column(name="national_url")
    private String nationalIdAttach;
	
	@Column(name="owner_image")
    private String ownerImage;
}
