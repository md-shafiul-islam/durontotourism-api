package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqRole implements Serializable {
	
	private static final long serialVersionUID = 6613240993480665651L;

	private int id;
	
	private String genId;
	
	private String publicId;
	
	private String name;
	
	private String description;
	
	private List<ReqAccess> accesses = new ArrayList<>();
	
	@JsonProperty("authStatus")
	private boolean authStatus;
	
	
	

}
