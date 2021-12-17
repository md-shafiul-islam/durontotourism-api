package com.usoit.api.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResSubAgent {
	
	private String id;
	
    private String name;
    
    private String email;
    
    private String code; 
    
    private String phone;   
    
    private boolean active;
    
    private Date date;
}
