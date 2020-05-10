package com.usoit.api.data.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPassword {

	private String oldPass;
	
	private String nPass;
	
	private String cPass;
	
	private int userId;
	
	
}
