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
import com.usoit.api.data.vo.RestDepartment;
import com.usoit.api.model.request.ReqDepartment;
import com.usoit.api.services.DepartmentServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestDepartmentController {
	
	@Autowired
	private DepartmentServices departmentServices;
	
	@Autowired
	private HelperServices helperServices;
	
	private List<Department> departments;
	
	private List<RestDepartment> restDepartments;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllDepartmentsAction(Principal principal, HttpServletRequest httpServletRequest) {
		
		setRestDepartments();
		
		return ResponseEntity.ok(restDepartments);
	}
	
	@RequestMapping(value = "/department", method = RequestMethod.POST)
	public ResponseEntity<?> getAddDepartmentAction(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ReqDepartment department) {
		
		return ResponseEntity.ok(department);
	}

	private void setRestDepartments() {
		
		setDepartments();
		
		restDepartments = DozerMapper.parseObjectList(departments, RestDepartment.class);
		
		System.out.println("Departments Size: " + restDepartments.size());
		
	}

	private void setDepartments() {
		
		if (departments == null) {
			
			departments = departmentServices.getAllDepartments();
		}else {
			
			long size = departments.size();
			long count = departmentServices.getCount();
			
			if (size != count) {
				departments = departmentServices.getAllDepartments();
			}
		}
		
	}
	
	

}
