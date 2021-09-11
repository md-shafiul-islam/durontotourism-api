package com.usoit.api.model;

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
@Entity
@Table(name = "marital_status")
public class MaritalStatus {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "maritalStatus")
	private List<User> users = new ArrayList<>();
	
	private String name;
	
	private String value;
	
	
	
}
