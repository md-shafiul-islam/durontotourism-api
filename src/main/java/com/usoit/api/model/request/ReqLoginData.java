package com.usoit.api.model.request;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqLoginData implements Serializable{
	
	private static final long serialVersionUID = -2450502259232617771L;

	@JsonProperty("username")
	private String username;
	
	@JsonProperty("password")
	private String password;
	
	
		
}
