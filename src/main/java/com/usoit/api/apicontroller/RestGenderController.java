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
import com.usoit.api.data.vo.RestGender;
import com.usoit.api.model.Gender;
import com.usoit.api.services.GenderServices;

@RestController
@RequestMapping("/api/genders")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestGenderController {
	
	@Autowired
	private GenderServices genderServices;
	
	private List<Gender> genders;
	
	private List<RestGender> restGenders;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllGender(Principal principal, HttpServletRequest httpServletRequest) {
		
		setRestGender();
		
		return ResponseEntity.ok(restGenders);
	}

	private void setRestGender() {
		
		setGender();
		
		restGenders = DozerMapper.parseObjectList(genders, RestGender.class);
		
	}

	private void setGender() {
		
		if (genders == null) {
			
			genders = genderServices.getAllGenders();
		}else {
			
			long size = genders.size();
			long count = genderServices.getCount();
			
			if (size != count) {
				
				genders = genderServices.getAllGenders();
				
			}
		}
		
	}
	
	

}
