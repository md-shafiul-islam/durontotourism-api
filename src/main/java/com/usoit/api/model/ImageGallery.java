package com.usoit.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ManyToAny;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "image_gallery")
public class ImageGallery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JsonManagedReference("pack_img")
	@ManyToOne
	@JoinColumn(name = "pack", referencedColumnName = "id")
	private Packages packages;
	
	@JsonProperty("srcUrl")
	@Column(name = "src_url")
	private String srcUrl;
	
	@JsonProperty("name")
	@Column(name = "name")
	private String name;
	
	@JsonProperty("altTag")
	@Column(name = "alt_tag")
	private String altTag;
	
	@JsonProperty("location")
	@Column(name = "location")
	private String location;
	
	@JsonProperty("file")
	@Transient
	private MultipartFile file;
	
	private Date date;
	
	
}
