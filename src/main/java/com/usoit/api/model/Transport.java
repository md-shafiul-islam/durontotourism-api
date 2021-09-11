package com.usoit.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transport")
public class Transport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "itarnary", referencedColumnName = "id")
	private Itarnary itarnarie;

	private String name;
	
	private String description;
	
	private String price;
	
	private Date date;
	
	
}
