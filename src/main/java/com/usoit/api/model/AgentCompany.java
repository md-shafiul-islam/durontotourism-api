package com.usoit.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="agent_company")
public class AgentCompany {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="agent", referencedColumnName = "id")
	private Agent agent;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="code")
	private String code;
	
	@Column(name="email")
	private String email;
	
	@Column(name="house_or_village")
	private String houseOrVillage;
	
	@Column(name="roadname_orno")
	private String roadNameOrNo;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@Column(name="police_station")
	private String policeStation;
	
	@Column(name="district_city")
	private String districtOrCity;
	
	@Column(name="trade_liceseno")
	private String tradeLiceseno;
	
	@Column(name="trade_attach")
	private String tradeAttach;
	
	@Column(name="tin_certificate_no")
	private String tinCertificateNo;
	
	@Column(name="tin_attach")
	private String tinAttach;
	
	@Column(name="bin_certificate_no")
	private String binCertificateNo;
	
	@Column(name="bin_attach")
	private String binAttach;
	
	@Column(name="company_logo_attach")
	private String companyLogoAttach;
	
	@OneToOne
	@JoinColumn(name="country")
	private Country country;
}
