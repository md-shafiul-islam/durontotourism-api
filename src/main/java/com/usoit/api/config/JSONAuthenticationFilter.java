package com.usoit.api.config;

import java.io.BufferedReader;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JSONAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	protected String obtainPassword(JsonObject obj) {
		return obj.getString(getPasswordParameter());
	}

	protected String obtainUsername(JsonObject obj) {
		return obj.getString(getUsernameParameter());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (!"application/json".equals(request.getContentType())) {
			// be aware that objtainPassword and Username in
			// UsernamePasswordAuthenticationFilter
			// have a different method signature
			return super.attemptAuthentication(request, response);
		}

		try (BufferedReader reader = request.getReader()) {

			// json transformation using javax.json.Json
			JsonObject obj = Json.createReader(reader).readObject();
			String username = obtainUsername(obj);
			String password = obtainPassword(obj);
			
			System.out.println("userName: " + username);
			System.out.println("Password: " + password);

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
					password);

			return this.getAuthenticationManager().authenticate(authRequest);
		} catch (IOException ex) {
			throw new AuthenticationServiceException("Parsing Request failed", ex);
		}
	}
}
