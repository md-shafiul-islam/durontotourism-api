package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JacksonInject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqHotel implements Serializable{

	private static final long serialVersionUID = -3884242154394893216L;

	private int id;
	
	private String location;
	
	private String name;
	
	private ReqCountry country;
	
	
	private ReqCategory category;
	
	private double price;
	
	private String day;
	
	private String night;
	

	
	

}
