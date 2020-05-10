package com.usoit.api.data.model;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "package_category")
public class PackageCat implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1317606092417176597L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JsonBackReference("pack_c")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "packageCat")
	private List<Packages> packages = new ArrayList<>();
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	
	
}
