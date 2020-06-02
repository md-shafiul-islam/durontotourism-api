package com.usoit.api.data.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestRole {
	
	private int id;
	
	private String publicId;
	
	private String genId;
	
	private List<RestAccessUser> accesses = new ArrayList<>();
	
	private List<RestUserRole> users = new ArrayList<>();
	
	private String name;
	
	private String description;
	
	private Date date;
	
	private Date dateGroupe;
	
	private int authStatus;

	

}
