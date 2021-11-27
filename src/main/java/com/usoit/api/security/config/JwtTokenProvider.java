package com.usoit.api.security.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.usoit.api.model.Customer;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.User;
import com.usoit.api.services.TravelerServices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	// Generate the token
	
	@Autowired
	private TravelerServices travelerServices;
	
	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());

		Date expiryDate = new Date(now.getTime() + SecurityConstants.EXP_TIME);

		String role = "";
		if (user.getRole() != null) {
			role = user.getRole().getName();

		} else {
			role = "User";
		}
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", (user.getPublicId()));
		claims.put("username", user.getUsername());
		claims.put("fullName", user.getName());
		claims.put("role", role);

		return Jwts.builder().setSubject(user.getPublicId()).setClaims(claims).setIssuedAt(now)
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET).compact();
	}

	public String generateCustomerToken(Authentication authentication) {
		Customer user = (Customer) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());

		Date expiryDate = new Date(now.getTime() + SecurityConstants.EXP_TIME);

		String role = "";
		if (user != null) {
			role = user.getClientType();

		} else {
			role = "User";
		}
		
		Traveler traveler = travelerServices.getPrimaryTravelerUsingCustomerID(user.getId());
		
		
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", (user.getPublicId()));
		claims.put("username", user.getUsername());
		claims.put("fullName", user.getFullName());
		claims.put("role", role);
		
		if(traveler != null) {
			claims.put("country", traveler.getNationality());
		}

		return Jwts.builder().setSubject(user.getPublicId()).setClaims(claims).setIssuedAt(now)
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET).compact();

	}

	// Validate the token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			log.info("Invalid JWT Signature");
		} catch (MalformedJwtException ex) {
			log.info("Invalid JWT Token");
		} catch (ExpiredJwtException ex) {
			log.info("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.info("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims string is empty");
		}
		return false;
	}

	// Get user Id from token

	public String getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
		String id = (String) claims.get("id");

		return id;
	}
}
