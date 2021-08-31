package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestMaritalStatus;
import com.usoit.api.model.MaritalStatus;
import com.usoit.api.services.MaritalStatusServices;

@RestController
@RequestMapping("/api/marital-status")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestMaritalStatusController {
	
	@Autowired
	private MaritalStatusServices maritalStatusServices;
	
	private List<MaritalStatus> maritalStatusList;
	
	private List<RestMaritalStatus> restMaritalStatus;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllMaritalStatus(Principal principal, HttpServletRequest request) {
		
		setRestMaritalStatusList();
		
		return ResponseEntity.ok(restMaritalStatus);
	}

	private void setRestMaritalStatusList() {
		
		setMaritalStatus();
		
		restMaritalStatus = DozerMapper.parseObjectList(maritalStatusList, RestMaritalStatus.class);
		
	}

	private void setMaritalStatus() {
		
		if (maritalStatusList == null) {
			
			maritalStatusList = maritalStatusServices.getAllMaritalStatus();
		}else {
			
			long size = maritalStatusList.size();
			long count = maritalStatusServices.getCount();
			
			if (size != count) {
				
				maritalStatusList = maritalStatusServices.getAllMaritalStatus();
			} 
		
		}
		
	}

}
