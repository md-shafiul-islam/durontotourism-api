package com.usoit.api.data.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTypeAPI {

	private int id;
	
	private List<AccessAPI> accesses = new ArrayList<>();
	
	private String name;
	
	private String value;
	
	private int numValue;
	
	
}
