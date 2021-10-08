package com.usoit.api.model;

import org.springframework.security.core.GrantedAuthority;

public class ClientAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String authority;

	public ClientAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}

}
