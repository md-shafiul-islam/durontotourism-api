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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
public @Data class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO )
	private int id;
	
	@JsonProperty("itarnaries")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Itarnary> itarnaries = new ArrayList<>();
	
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	
	
	
}
