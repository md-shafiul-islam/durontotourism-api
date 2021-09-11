package com.usoit.api.config;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.usoit.api.model.request.ReqLoginData;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		
		System.out.println("AuthenticationFilter Init");
		
		this.authenticationManager = authenticationManager;
	}
	
	@Transactional
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("AuthenticationFilter attemptAuthentication");
		ReqLoginData loginData = new ReqLoginData();
		
		String userName = getUsernameParameter();
		String password = getPasswordParameter();
		
		String userNameValue = request.getParameter("username");
		String pass = request.getParameter("password");
		
		System.out.println("Param: "  + userName + " Pass: " + password);
		System.out.println("Value: "  + userNameValue + " Pass: " + pass);
		
		System.out.println("User Name: " + loginData.getUsername() + " Password: " + loginData.getPassword());
		
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userNameValue, pass, new ArrayList<>()));
		
	}
	
	/*
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		System.out.println("Login successful!!!");
		
		String userName = ((User)authResult.getPrincipal()).getUsername();
		
		String token = Jwts.builder().setSubject(userName)
						.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXP_TIME))
						.signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
						.compact();
		
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+token);
		
		//super.successfulAuthentication(request, response, chain, authResult);
	}
	*/
	
}
