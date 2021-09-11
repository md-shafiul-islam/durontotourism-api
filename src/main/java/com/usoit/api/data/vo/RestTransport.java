package com.usoit.api.data.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestTransport {

	private int id;
	
	private RestItarnary itarnarie;

	private String name;
	
	private String description;
	
	private String price;
	
	private Date date;
		
}
