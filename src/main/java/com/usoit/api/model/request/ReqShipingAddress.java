package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqShipingAddress {

	private String district;	

	private String policeStation;

	private String roadNo;

	private String village;
}
