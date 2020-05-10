package com.usoit.api.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqDestination implements Serializable{

	private static final long serialVersionUID = -4567610921785511236L;

	private int id;
	
	private ReqCountry country;
	
	
	private ReqPackage packages;
	
	
}
