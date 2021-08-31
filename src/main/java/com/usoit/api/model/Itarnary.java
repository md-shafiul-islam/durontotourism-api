package com.usoit.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.catalina.valves.Constants.AccessLog;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "itarnary")
public class Itarnary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5176067859671158366L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "public_id")
	private String publicId;

	@JsonManagedReference("itn_pack")
	@ManyToOne
	@JoinColumn(name = "package", referencedColumnName = "id")
	private Packages packages;

	@JsonProperty("country")
	@ManyToOne
	@JoinColumn(name = "country", referencedColumnName = "id")
	private Country country;

	@JsonProperty("city")
	@Column(name = "city")
	private String city;

	@JsonProperty("dayOrDurations")
	@Column(name = "w_day")
	private int dayOrDurations;

	@JsonProperty("vendor")
	@JsonManagedReference("vendor_itn")
	@ManyToOne
	@JoinColumn(name = "vendor", referencedColumnName = "id")
	private Vendor vendor;

	@JsonProperty("sourceInfo")
	@Column(name = "source_info")
	private String sourceInfo;

	@JsonProperty("heading")
	@Column(name = "heading")
	private String heading;

	@JsonProperty("description")
	@Column(name = "description")
	private String description;

	@JsonProperty("hightLightText")
	@Column(name = "hightlight_text")
	private String hightLightText;

	@JsonProperty("hotelText")
	@Column(name = "hotel_text")
	private String hotelText;

	@JsonProperty("includedText")
	@Column(name = "included_text")
	private String includedText;

	@JsonProperty("excludedText")
	@Column(name = "excluded_text")
	private String excludedText;

	@JsonProperty("category")
	@ManyToOne
	@JoinColumn(name = "category", referencedColumnName = "id")
	private Category category;

	@JsonProperty("file")
	@Transient
	private MultipartFile file;

	@JsonProperty("fil2")
	@Transient
	private MultipartFile fil2;

	@JsonProperty("sourceUrl")
	@Column(name = "source_url")
	private String sourceUrl;

	@JsonProperty("sourceUrl2")
	@Column(name = "source_url2")
	private String sourceUrl2;

	@JsonProperty("expDate")
	@Column(name = "exp_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expDate;

	@JsonProperty("date")
	private Date date;

	@JsonProperty("dateGroup")
	@Column(name = "date_group")
	private Date dateGroup;


}
