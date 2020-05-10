package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.Department;
import com.usoit.api.data.model.Designation;
import com.usoit.api.data.vo.RestDepartment;
import com.usoit.api.data.vo.RestDesignation;
import com.usoit.api.model.request.ReqDepartment;
import com.usoit.api.model.request.ReqDesignation;
import com.usoit.api.services.DepartmentServices;
import com.usoit.api.services.DesignationServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/designations")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestDesignationController {
	
	@Autowired
	private DesignationServices designationServices;
	
	@Autowired
	private HelperServices helperServices;
	
	private List<Designation> designations;
	
	private List<RestDesignation> restDesignations;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllDepartmentsAction(Principal principal, HttpServletRequest httpServletRequest) {
		
		setRestDepartments();
		
		return ResponseEntity.ok(restDesignations);
	}
	
	@RequestMapping(value = "/designations", method = RequestMethod.POST)
	public ResponseEntity<?> getAddDepartmentAction(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ReqDesignation designation) {
		
		return ResponseEntity.ok(designation);
	}

	private void setRestDepartments() {
		
		setDepartments();
		
		restDesignations = DozerMapper.parseObjectList(designations, RestDesignation.class);
		
		System.out.println("Departments Size: " + restDesignations.size());
		
	}

	private void setDepartments() {
		
		if (designations == null) {
			
			designations = designationServices.getAllDesignations();
		}else {
			
			long size = designations.size();
			long count = designationServices.getCount();
			
			if (size != count) {
				designations = designationServices.getAllDesignations();
			}
		}
		
	}
	
	

}
