package com.usoit.api.data.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelAPI {
	
	private int id;
	
	private String location;
	
	private String name;
	
	private RestCountry country;
	
	
	private RestCategory category;
	
	private List<RestItarnary> itarnaries = new ArrayList<>();
	
	private double price;
	
	private String duration;
	

	
	

}
