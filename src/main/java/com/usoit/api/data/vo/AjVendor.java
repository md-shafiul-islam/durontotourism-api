package com.usoit.api.data.vo;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.usoit.api.model.Country;

public class AjVendor {

	String msg;

	private int vId;

	private String vGenId;

	private String companyName;

	private String email;

	private String webSite;

	private String perName;

	private String perEmail;

	private String perPhone;

	private String perPhone2;

	private String adHouse;

	private String adVillage;

	private String adStreet;

	private String adZipCode;

	private String adCity;

	private String adCountryName;

	private String adCuntryCode;

	public AjVendor() {
	}
	
	public AjVendor(String msg, int vId, String vGenId, String companyName, String email, String webSite,
			String perName, String perEmail, String perPhone, String perPhone2, String adHouse, String adVillage,
			String adStreet, String adZipCode, String adCity, String adCountryName, String adCuntryCode) {
		this.msg = msg;
		this.vId = vId;
		this.vGenId = vGenId;
		this.companyName = companyName;
		this.email = email;
		this.webSite = webSite;
		this.perName = perName;
		this.perEmail = perEmail;
		this.perPhone = perPhone;
		this.perPhone2 = perPhone2;
		this.adHouse = adHouse;
		this.adVillage = adVillage;
		this.adStreet = adStreet;
		this.adZipCode = adZipCode;
		this.adCity = adCity;
		this.adCountryName = adCountryName;
		this.adCuntryCode = adCuntryCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getvId() {
		return vId;
	}

	public void setvId(int vId) {
		this.vId = vId;
	}

	public String getvGenId() {
		return vGenId;
	}

	public void setvGenId(String vGenId) {
		this.vGenId = vGenId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getPerEmail() {
		return perEmail;
	}

	public void setPerEmail(String perEmail) {
		this.perEmail = perEmail;
	}

	public String getPerPhone() {
		return perPhone;
	}

	public void setPerPhone(String perPhone) {
		this.perPhone = perPhone;
	}

	public String getPerPhone2() {
		return perPhone2;
	}

	public void setPerPhone2(String perPhone2) {
		this.perPhone2 = perPhone2;
	}

	public String getAdHouse() {
		return adHouse;
	}

	public void setAdHouse(String adHouse) {
		this.adHouse = adHouse;
	}

	public String getAdVillage() {
		return adVillage;
	}

	public void setAdVillage(String adVillage) {
		this.adVillage = adVillage;
	}

	public String getAdStreet() {
		return adStreet;
	}

	public void setAdStreet(String adStreet) {
		this.adStreet = adStreet;
	}

	public String getAdZipCode() {
		return adZipCode;
	}

	public void setAdZipCode(String adZipCode) {
		this.adZipCode = adZipCode;
	}

	public String getAdCity() {
		return adCity;
	}

	public void setAdCity(String adCity) {
		this.adCity = adCity;
	}

	public String getAdCountryName() {
		return adCountryName;
	}

	public void setAdCountryName(String adCountryName) {
		this.adCountryName = adCountryName;
	}

	public String getAdCuntryCode() {
		return adCuntryCode;
	}

	public void setAdCuntryCode(String adCuntryCode) {
		this.adCuntryCode = adCuntryCode;
	}
	
	

}
