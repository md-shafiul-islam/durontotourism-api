package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAgentOwner {
	
	private String id;
	
	private String name;
	
	private String phone;
	
    private String code;
    
	private String email;
	
    private String houseNoOrVillage;
	
    private String roadNameOrNo;
	
    private String postalCode;
	
    private String policeStation;
	
    private String district;

    private String country;
	
    private String nationalIdNo;
		
    private String passportNo;
	
    private String passportAttach;
	
    private String nationalIdAttach;
	
    private String ownerImage;
}
