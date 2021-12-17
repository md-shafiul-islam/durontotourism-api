package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAgentCompany {

	private String id;
	
	private String companyName;

	private String phone;

	private String code;

	private String email;

	private String houseOrVillage;

	private String roadNameOrNo;

	private String postalCode;

	private String policeStation;

	private String districtOrCity;

	private String tradeLiceseno;

	private String tradeAttach;

	private String tinCertificateNo;

	private String tinAttach;

	private String binCertificateNo;

	private String binAttach;

	private String companyLogoAttach;

	private String country;
}
