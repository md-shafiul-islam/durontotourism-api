package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/department", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddDepartmentAction(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ReqDepartment department) {
		
		if (department != null) {
			
			if (department.getName() != null) {
				
				Department department2 = new Department();
				
				department2.setDescription(department.getDescription());
				department2.setName(department.getName());
				
				if (departmentServices.save(department2)) {
					
					return ResponseEntity.ok("Department save Success !! ");
				}
			}
		}
		
		return ResponseEntity.accepted().body("Data Recive save failed!!");
	}
	
	@RequestMapping(value = "/department", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUpdateDepartmentAction(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ReqDepartment department) {
		
		if (department != null) {
			
			if (department.getName() != null && department.getId() > 0) {
				
				Department department2 = new Department();
				
				department2.setDescription(department.getDescription());
				department2.setName(department.getName());
				department2.setId(department.getId());
				
				if (departmentServices.update(department2)) {
					
					return ResponseEntity.ok("Department save Success !! ");
				}
			}
		}
		
		return ResponseEntity.accepted().body("Data Recive save failed!!");
	}

	@RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDepartmentById(Principal principal, @PathVariable("id") int id, HttpServletRequest request) {
		
		if (id > 0) {
			
			Department department = departmentServices.getDepartmentById(id);
			
			if (department != null) {
				

				ReqDepartment reqDepartment = new ReqDepartment();
				
				reqDepartment.setId(department.getId());
				reqDepartment.setName(department.getName());
				reqDepartment.setDescription(department.getDescription());
				
				return ResponseEntity.ok(reqDepartment);
			}
		}
		
		return ResponseEntity.noContent().build();
	}
	
	private void setRestDepartments() {
		
		setDepartments();
		
		restDepartments = DozerMapper.parseObjectList(departments, RestDepartment.class);
		
		System.out.println("Departments Size: " + restDepartments.size());
		
	}

	private void setDepartments() {
			
		departments = departmentServices.getAllDepartments();
		
	}
	
	

}
