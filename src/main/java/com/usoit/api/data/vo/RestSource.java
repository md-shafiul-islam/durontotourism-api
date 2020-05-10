package com.usoit.api.data.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestSource {
	
	private int id;
	
	private RestItarnary itarnary;
	
		
	private String sourceUrl;
	
	private String sourceUrl2;
	
	private Date expDate;

	
}
