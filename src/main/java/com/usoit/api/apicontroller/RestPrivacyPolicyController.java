package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.PrivacyPolicy;
import com.usoit.api.data.vo.RestPrivacyPolicy;
import com.usoit.api.model.request.ReqPrivacyPolicy;
import com.usoit.api.model.request.ReqTermsAndConds;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.PrivacyPolicyServices;

@RestController
@RequestMapping("/api/privacy-policies")
public class RestPrivacyPolicyController {

	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private PrivacyPolicyServices privacyPolicyServices;
	
	private List<PrivacyPolicy> privacyPolicy;
	
	private List<RestPrivacyPolicy> restPrivacyPolicy;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllPrivacyPolicy(Principal principal, HttpServletRequest request) {
		
		List<String> msg = new ArrayList<>();
		
		setRestPrivacyPolicy(msg);
		
		if (restPrivacyPolicy != null) {
			
			System.out.println("Rest Term Size: " + restPrivacyPolicy.size());
			
			return ResponseEntity.ok(restPrivacyPolicy);
			
		}else {
			
			System.out.println("Terms Mapping Failed !!");
		}
		
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/policy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPrivacyPolicyAddAction(Principal principal, HttpServletRequest request, @RequestBody ReqPrivacyPolicy reqPrivacyPolicy) {
		
		String msg = "PrivacyPolicy Data not Set Or not match requerment ";
		
		if(reqPrivacyPolicy != null) {
			
			if (reqPrivacyPolicy.getName() != null && reqPrivacyPolicy.getDescription() != null) {
				
				PrivacyPolicy privacyPolicy = new PrivacyPolicy();
				
				privacyPolicy.setName(reqPrivacyPolicy.getName());;
				privacyPolicy.setDescription(reqPrivacyPolicy.getDescription());
				privacyPolicy.setPublicId(helperServices.getRandomString(105));
				
				if (privacyPolicyServices.save(privacyPolicy)) {
					
					System.out.println("PrivacyPolicy Save!!");
					
					return ResponseEntity.ok("reqPrivacyPolicy Save ");
				}
			}
		}
		
		return ResponseEntity.accepted().body(msg);
	}

	
	@RequestMapping(value = "/policy/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPrivacyPolicyByPublicId(Principal principal, HttpServletRequest request, @PathVariable("id") String pubId) {
		
		String msg = "";
		
		if (helperServices.isValidAndLenghtCheck(pubId, 105)) {
			
			PrivacyPolicy PrivacyPolicy = privacyPolicyServices.getPrivacyPolicyByPubId(pubId);
			
			if (PrivacyPolicy != null) {
				
				ReqTermsAndConds reqTermsAndConds = DozerMapper.parseObject(PrivacyPolicy, ReqTermsAndConds.class);
				
				if (reqTermsAndConds != null) {
					
					return ResponseEntity.ok(reqTermsAndConds);
				}
			}
		}
		
		return ResponseEntity.accepted().body(msg);
	}

	@RequestMapping(value = "/policy", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPrivacyPolicyUpdateAction(Principal principal, HttpServletRequest request, @RequestBody ReqPrivacyPolicy reqPrivacyPolicy) {
		
		String msg = "Terms Data not Set Or not match requerment ";
		
		if(reqPrivacyPolicy != null) {
			
			if (reqPrivacyPolicy.getName() != null && reqPrivacyPolicy.getDescription() != null && reqPrivacyPolicy.getPublicId() != null) {
				
				PrivacyPolicy privacyPolicy = new PrivacyPolicy();
				
				privacyPolicy.setName(reqPrivacyPolicy.getName());;
				privacyPolicy.setDescription(reqPrivacyPolicy.getDescription());
				privacyPolicy.setPublicId(reqPrivacyPolicy.getPublicId());
				
				if (privacyPolicyServices.update(privacyPolicy)) {
					
					System.out.println("Terms Updated !!");
					
					return ResponseEntity.ok("T&C Update ");
				}
			}
		}
		
		return ResponseEntity.accepted().body(msg);
	}
	

	
	private void setRestPrivacyPolicy(List<String> msg) {
		
		setPrivacyPolicy();
		
		if (privacyPolicy != null) {
			
			System.out.println("terms Size: " + privacyPolicy.size());
			
			restPrivacyPolicy = DozerMapper.parseObjectList(privacyPolicy, RestPrivacyPolicy.class);
			
			
		}else {
			msg.add("Terms Mapping Failed !!");
			System.out.println("Terms Null !!");
		}
		
	}


	private void setPrivacyPolicy() {
		
		if (privacyPolicy == null) {
			
			privacyPolicy = privacyPolicyServices.getAllPrivacyPolicy();
		}else {
			
			long size = privacyPolicy.size();
			long count = privacyPolicyServices.getCount();
			
			if (size != count) {
				
				privacyPolicy = privacyPolicyServices.getAllPrivacyPolicy();
			}
		}
		
	}
}
