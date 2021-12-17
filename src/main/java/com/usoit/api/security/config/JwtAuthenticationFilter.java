package com.usoit.api.security.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.usoit.api.enduser.services.CustomerServices;
import com.usoit.api.model.Customer;
import com.usoit.api.model.User;
import com.usoit.api.services.UserServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserServices userService;

	@Autowired
	private CustomerServices customerServices;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		try {
			
			String cPath = httpServletRequest.getRequestURI();			

			List<String> pathSplits = Arrays.asList(cPath.split("/"));
			
			
			String jwt = getJWTFromRequest(httpServletRequest);
			log.info("Split Path "+pathSplits );

			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				String userId = tokenProvider.getUserIdFromJWT(jwt);
				
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null);
				
				if(pathSplits.contains("enu") && pathSplits.contains("api")) {
					

					log.debug("Customer EndPoint " + cPath);
					Customer customer = customerServices.getCustomerByPublicId(userId);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customer, null,
							Collections.emptyList());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					
					log.debug("Cutomer Authentication Using Filter, "+ authentication.getName());
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}else if(!pathSplits.contains("enu") && pathSplits.contains("api")) {
					log.debug("Admin User EndPoint " + cPath);
					User user = userService.getUserByPublicID(userId);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				
			}

		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}

	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}

		return null;
	}
}
