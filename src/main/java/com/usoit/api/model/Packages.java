package com.usoit.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "package")
public @Data class Packages implements Serializable{

	
	private static final long serialVersionUID = -3031383126647050845L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "public_id")
	private String publicId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("code")
	private String code;

	@JsonProperty("description")
	@Column(name = "description")
	private String description;

	@JsonProperty("packIncludedText")
	@Column(name = "included_text")
	private String packIncludedText;

	@JsonProperty("packExcludedText")
	@Column(name = "excluded_text")
	private String packExcludedText;

	@JsonProperty("packHightlightText")
	@Column(name = "hightlight_text")
	private String packHightlightText;
	
	@JsonProperty("duration")
	@ManyToOne
	@JoinColumn(name = "duration", referencedColumnName = "id")
	private Duration duration;

	@JsonProperty("packageCat")
	@ManyToOne
	@JoinColumn(name = "pack_cat", referencedColumnName = "id")
	private PackageCat packageCat;

	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "update_user", referencedColumnName = "id")
	private User updateUser;
	
	@ManyToOne
	@JoinColumn(name = "approve_user", referencedColumnName = "id")
	private User approveUser;

	@JsonProperty("itarnarys")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "packages")
	private List<Itarnary> itarnarys = new ArrayList<Itarnary>();

	@JsonProperty("imageGalleries")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "packages")
	private List<ImageGallery> imageGalleries = new ArrayList<>();

	
	@JsonProperty("price")
	@Column(name = "price")
	private double price;

	@JsonProperty("videoUrl")
	@Column(name = "video_url")
	private String videoUrl;

	@JsonProperty("date")
	private Date date;

	@JsonProperty("dateGroup")
	@Column(name = "date_group")
	private Date dateGroup;

	@JsonProperty("approvalStatus")
	@Column(name = "approval")
	private int approvalStatus;

	@JsonProperty("updateApproval")
	@Column(name = "update_approval")
	private int updateApproval;
	

	@JsonProperty("countries")
	@ManyToMany
	@JoinTable(name = "pack_country", joinColumns = {
			@JoinColumn(name = "pack_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "country_id", referencedColumnName = "id") })
	private List<Country> countries = new ArrayList<>();
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name="modyfi_status")
	private String modydiyStatus;
	
	@Column(name="price_4_person")
	private double price4Person;
	
	@Column(name="price_6_person")
	private double price6Person;
	
	@Column(name="price_8_person")
	private double price8Person;

	
	

}