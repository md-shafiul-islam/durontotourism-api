package com.usoit.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqWalletStatusUpdate {

	private String publicId;
	
	private int active;
	
	private int approve;
}