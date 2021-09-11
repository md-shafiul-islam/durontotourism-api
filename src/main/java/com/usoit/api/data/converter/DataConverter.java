package com.usoit.api.data.converter;

import org.springframework.stereotype.Component;

@Component
public class DataConverter {

	
	public Double getStringToDouble(String stringVal) {
		
		if (stringVal != null) {
			
			if (stringVal.matches(".*\\d.*")) {
				
				try {
					return Double.parseDouble(stringVal);
				} catch (NumberFormatException e) {
					return 0D;
				}
				
			}
			
		}
		
		return 0D;
	}
}
