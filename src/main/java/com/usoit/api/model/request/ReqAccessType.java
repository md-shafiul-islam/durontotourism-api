package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAccessType {

	private int id;
	
	private String name;
	
	private String value;
	
	private int numValue;
	
	
}
