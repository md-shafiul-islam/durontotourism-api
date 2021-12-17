package com.usoit.api.enduser.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.enduser.services.AgentServices;
import com.usoit.api.exception.InvalidLoginResponse;
import com.usoit.api.mapper.AgentMapper;
import com.usoit.api.model.Agent;
import com.usoit.api.model.AgentCompany;
import com.usoit.api.model.AgentOwner;
import com.usoit.api.model.request.ReqAgent;
import com.usoit.api.model.request.ReqAgentCompany;
import com.usoit.api.model.request.ReqAgentOwner;
import com.usoit.api.model.request.ReqLoginData;
import com.usoit.api.model.request.ReqRestMail;
import com.usoit.api.model.request.ReqRestPassword;
import com.usoit.api.model.request.ReqRestPhone;
import com.usoit.api.model.request.ReqSubAgnet;
import com.usoit.api.model.response.ResSubAgent;
import com.usoit.api.model.response.RespAgent;
import com.usoit.api.payload.JWTLoginSucessReponse;
import com.usoit.api.security.config.JwtTokenProvider;
import com.usoit.api.security.config.SecurityConstants;
import com.usoit.api.services.HelperAuthenticationServices;

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

	@Autowired
	private HelperAuthenticationServices helperAuthenticationServices;

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> name(@RequestBody ReqLoginData loginDate, HttpServletRequest httpServletRequest) {

		String jwt = null;
		if (loginDate != null) {
			Agent agent = agentServices.getAgentByUsername(loginDate.getUsername());
			log.info("Current Agent " + agent);
			if (agent != null) {
				log.info("Agent Application Name, " + agent.getApplicantName());
				log.info("Current Password, " + loginDate.getPassword() + " DB Pass " + agent.getPassword());
				if (passwordEncoder.matches(loginDate.getPassword(), agent.getPassword())) {
					log.info("Password Match :)");
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(agent,
							null, Collections.emptyList());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateCustomerToken(authentication);

				} else {
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

		if (agent != null) {

			if (agentServices.addAgent(agent)) {
				map.put("message", "Agent Added ");
				map.put("status", true);
				map.put("data", agent.getPublicId());
			}
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/current")
	public ResponseEntity<?> getLoginAgentAction() {

		log.info("Get Agent Login run ... ");

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Authorised Agent not Found ");
		map.put("status", false);
		map.put("data", null);

		Agent agent = helperAuthenticationServices.getCurrentAgnet();

		if (agent != null) {

			RespAgent respAgent = agentMapper.getRespAgent(agent);

			if (respAgent != null) {
				map.put("message", "Authorised Agent Found ");
				map.put("status", true);
				map.put("data", respAgent);
			}
		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/company", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddCompanyAction(@RequestBody ReqAgentCompany reqCompany) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Agent Company update ...");
		map.put("status", false);
		map.put("data", null);
		Agent agent = helperAuthenticationServices.getCurrentAgnet();
		if (agent != null) {
			AgentCompany agentCompany = agentMapper.getAgentCompany(reqCompany);
			boolean status = agentServices.updateAgentCompanyInfo(agentCompany, agent);
			map.put("message", "Agent Company update failed");
			if (status) {
				map.put("message", "Agent Company updated");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/owners", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUpdateAgentOwnerAction(@RequestBody ReqAgentOwner reqOwner) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Agent Owner update ...");
		map.put("status", false);
		map.put("data", null);
		Agent agent = helperAuthenticationServices.getCurrentAgnet();
		if (agent != null) {
			map.put("message", "Agent Owner update Unauthorized Access");
			if (!agent.isSubAgent()) {

				log.info("Agent Owner Befor Update Method ....");

				AgentOwner agentOwner = agentMapper.getAgentOwner(reqOwner);
				boolean status = agentServices.updateAgentOwnerInfo(agentOwner, agent);
				log.info("Agent Owner After  Update Method ....");
				map.put("message", "Agent Owner update failed !!");
				if (status) {
					map.put("message", "Agent Owner updated");
					map.put("status", true);
					map.put("data", null);
				}
			}

		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/owners", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddAgentOwnerAction(@RequestBody ReqAgentOwner reqOwner) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Agent Owner Add ...");
		map.put("status", false);
		map.put("data", null);
		Agent agent = helperAuthenticationServices.getCurrentAgnet();
		if (agent != null && reqOwner != null) {
			map.put("message", "Agent Owner update Unauthorized Access");
			if (!agent.isSubAgent() && isNullOrEmpty(reqOwner.getId())) {
				AgentOwner agentOwner = agentMapper.getAgentOwner(reqOwner);
				boolean status = agentServices.addAgentOwnerInfo(agentOwner, agent);
				map.put("message", "Agent Owner Add failed");
				if (status) {
					map.put("message", "Agent Owner Added");
					map.put("status", true);
					map.put("data", null);
				}
			}

		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/sub-agents", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddSubagentAction(@RequestBody ReqSubAgnet subAgent) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Sub agent add failed");
		map.put("status", false);
		map.put("data", null);
		Agent cAgent = helperAuthenticationServices.getCurrentAgnet();
		if (subAgent != null && cAgent != null) {
			Agent agent = agentMapper.mapSubAgent(subAgent);

			boolean status = agentServices.createSubAgent(agent, cAgent);

			if (status) {
				map.put("message", "Sub agent creates or added");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/sub-agents")
	public ResponseEntity<?> getSubAgentsAction() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Sub agent add failed");
		map.put("status", false);
		map.put("data", null);
		Agent cAgent = helperAuthenticationServices.getCurrentAgnet();
		List<ResSubAgent> subAgents = agentMapper.getResSubAgents(agentServices.getSubAagents(cAgent));

		if (subAgents != null) {
			map.put("message", "Sub agent Found");
			map.put("status", true);
			map.put("data", subAgents);
		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/rest-pass", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAgentRestPasswordAction(@RequestBody ReqRestPassword restPass) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "User Password change failed");
		map.put("status", false);
		map.put("data", null);
		Agent cAgent = helperAuthenticationServices.getCurrentAgnet();
		if (restPass != null && cAgent != null) {

			log.info("Old Password " + restPass.getPrevPassword() + " PWD " + restPass.getPwd());
			
			map.put("message", "User Password field can't be empty");
			if (!isNullOrEmpty(restPass.getPrevPassword()) && !isNullOrEmpty(restPass.getPwd())) {
				
				map.put("message", "User Password not match");
				
				if (passwordEncoder.matches(restPass.getPrevPassword(), cAgent.getPassword())
						&& restPass.getPwd() != null) {
					boolean status = agentServices.restAgnetPassword(restPass, cAgent);

					if (status) {
						map.put("message", "User Password change successful");
						map.put("status", true);
						map.put("data", null);
					}
				}
			}

		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/change-mail", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAgentRestMailAction(@RequestBody ReqRestMail restMail) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Agent Mail change failed");
		map.put("status", false);
		map.put("data", null);
		Agent cAgent = helperAuthenticationServices.getCurrentAgnet();
		if (restMail != null && cAgent != null) {
			map.put("message", "Agent Mail field can't be empty");
			if (!isNullOrEmpty(restMail.getEmail())) {
				boolean status = agentServices.changeAgnetMail(restMail, cAgent);

				if (status) {
					map.put("message", "Agent Mail change successful");
					map.put("status", true);
					map.put("data", null);
				}
			}

		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/change-phone", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAgentRestPhoneAction(@RequestBody ReqRestPhone restPhone) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Change agnet phone failed");
		map.put("status", false);
		map.put("data", null);
		Agent cAgent = helperAuthenticationServices.getCurrentAgnet();
		if (restPhone != null && cAgent != null) {
			map.put("message", "Change agnet phone fields can't be empty");
			if (!isNullOrEmpty(restPhone.getCode()) && !isNullOrEmpty(restPhone.getCode())) {
				boolean status = agentServices.changeAgnetPhone(restPhone, cAgent);

				if (status) {
					map.put("message", "Change agnet phone successful");
					map.put("status", true);
					map.put("data", null);
				}
			}

		}

		return ResponseEntity.ok(map);
	}

	private boolean isNullOrEmpty(String str) {

		if (str != null) {
			if (!str.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}
