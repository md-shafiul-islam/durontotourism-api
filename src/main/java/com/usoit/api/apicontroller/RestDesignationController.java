package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
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
import com.usoit.api.data.vo.RestDesignation;
import com.usoit.api.model.Designation;
import com.usoit.api.model.request.ReqDesignation;
import com.usoit.api.model.response.SelectOption;
import com.usoit.api.services.DesignationServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/designations")
@CrossOrigin(origins = "http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser" })
public class RestDesignationController {

	@Autowired
	private DesignationServices designationServices;

	@Autowired
	private HelperServices helperServices;

	private List<Designation> designations;

	private List<RestDesignation> restDesignations;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllDesignationsAction(Principal principal, HttpServletRequest httpServletRequest) {

		setRestDepartments();

		return ResponseEntity.ok(restDesignations);
	}

	@RequestMapping(value = "/designation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddDesignationAction(Principal principal, HttpServletRequest httpServletRequest,
			@RequestBody ReqDesignation designation) {

		if (designation != null) {

			if (!designation.getName().isEmpty()) {

				Designation designation2 = new Designation();

				designation2.setDescription(designation.getDescription());
				designation2.setName(designation.getName());

				if (designationServices.save(designation2)) {

					return ResponseEntity.ok("Designation Saved !!");
				}
			}
		}

		return ResponseEntity.badRequest().body("Sending data not match requerment !!");
	}

	@RequestMapping(value = "/designation", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUpdateDesignationAction(Principal principal, HttpServletRequest httpServletRequest,
			@RequestBody ReqDesignation designation) {

		if (designation != null) {

			if (!designation.getName().isEmpty() && designation.getId() > 0) {

				Designation designation2 = new Designation();

				designation2.setDescription(designation.getDescription());
				designation2.setName(designation.getName());
				designation2.setId(designation.getId());

				if (designationServices.update(designation2)) {

					return ResponseEntity.ok("Designation Saved !!");
				}
			}
		}

		return ResponseEntity.badRequest().body("Sending data not match requerment !!");
	}

	@RequestMapping(value = "/designation/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getDesignationByIdAction(Principal principal, HttpServletRequest httpServletRequest,
			@PathVariable("id") int id) {

		if (id > 0) {

			Designation designation = designationServices.getDesignationById(id);
			
			RestDesignation restDesignation = DozerMapper.parseObject(designation, RestDesignation.class);
			
			

			if (restDesignation != null) {

				return ResponseEntity.ok(restDesignation);
			}
		}

		return ResponseEntity.badRequest().body("Sending data not match requerment !!");
	}

	@RequestMapping(value = "/options", method = RequestMethod.GET)
	public ResponseEntity<?> getDesignationOptionsAction(Principal principal, HttpServletRequest httpServletRequest) {

		List<SelectOption> options = new ArrayList<>();
		List<Designation> designations = designationServices.getAllDesignations();
		for (Designation designation : designations) {
			
			SelectOption option = new SelectOption();
			option.setLabel(designation.getName());
			option.setValue(Integer.toString(designation.getId()));
			options.add(option);
		}
		return ResponseEntity.ok(options);
	}
	private void setRestDepartments() {

		setDepartments();

		restDesignations = DozerMapper.parseObjectList(designations, RestDesignation.class);

		System.out.println("Departments Size: " + restDesignations.size());

	}

	private void setDepartments() {
		designations = designationServices.getAllDesignations();
		
	}

}
