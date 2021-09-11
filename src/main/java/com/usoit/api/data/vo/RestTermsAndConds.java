package com.usoit.api.data.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestTermsAndConds {

	
	private String publicId;
	
	private String name;
	
	private String discription;
	
	private Date date;
}
