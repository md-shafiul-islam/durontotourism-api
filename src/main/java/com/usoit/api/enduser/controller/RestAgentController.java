package com.usoit.api.enduser.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.enduser.services.AgentServices;
import com.usoit.api.exception.InvalidLoginResponse;
import com.usoit.api.mapper.AgentMapper;
import com.usoit.api.model.Agent;
import com.usoit.api.model.request.ReqAgent;
import com.usoit.api.model.request.ReqLoginData;
import com.usoit.api.payload.JWTLoginSucessReponse;
import com.usoit.api.security.config.JwtTokenProvider;
import com.usoit.api.security.config.SecurityConstants;
import com.usoit.api.servicesimpl.MailSenderServicesImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/enu/v1/agents")
public class RestAgentController {
	
	@Autowired
	private AgentServices agentServices;
	
	@Autowired
	private AgentMapper agentMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider; 
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> name(@RequestBody ReqLoginData loginDate, HttpServletRequest httpServletRequest) {

		String jwt = null;
		if (loginDate != null) {
			Agent agent = agentServices.getAgentByUsername(loginDate.getUsername());
			log.info("Current Agent "+ agent);
			if (agent != null) {
				log.info("Agent Application Name, "+ agent.getApplicantName()+ " "+agent.getEmail());
				log.info("Current Password, "+ loginDate.getPassword()+ " DB Pass "+agent.getPassword());
				if (passwordEncoder.matches(loginDate.getPassword(), agent.getPassword())) {
					log.info("Passeord Match :)");
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							agent, null, Collections.emptyList());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateCustomerToken(authentication);

				}else {
					log.info("Passeord not Match  !!");
					return ResponseEntity.ok(new InvalidLoginResponse());
				}

			}
		}

		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
	}
	
	
	@PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addAgentAction(@RequestBody ReqAgent reqAgent) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Agent Add Failed !!");
		map.put("status", false);
		map.put("data", null);
		
		Agent agent = agentMapper.mapAgent(reqAgent);
		
		if(agent != null) {
			
			if(agentServices.addAgent(agent)) {
				map.put("message", "Agent Added ");
				map.put("status", true);
				map.put("data", agent.getPublicId());
			}
		}
		
		
		return ResponseEntity.ok(map);
	}
}
