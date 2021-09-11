package com.usoit.api.model.request;

import java.io.Serializable;
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
public class ReqGender implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -66124954821670111L;


	private int id;
	
	
	private String name;
	
	private String value;
		
	
}
