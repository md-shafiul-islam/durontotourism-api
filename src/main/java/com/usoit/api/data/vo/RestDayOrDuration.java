package com.usoit.api.data.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestDayOrDuration {
	
	private int id;
	
	private RestItarnary itarnary; 
	
	private String name;
	
	private double value;
		

}
