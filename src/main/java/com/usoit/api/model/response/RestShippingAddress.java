package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestShippingAddress {

	private int id;
	
	private String roadNo;

	private String village;

	private String district;

	private String policeStation;
}
