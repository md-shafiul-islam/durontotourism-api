package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqIncluded implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5918494985668345722L;

	private int id;
	
	private String name;
	
	private String description;
	
	private String value;
		

	
}
