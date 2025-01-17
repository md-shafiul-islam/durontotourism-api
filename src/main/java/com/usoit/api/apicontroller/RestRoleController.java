package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.loader.plan.exec.process.spi.ReturnReader;
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
import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.AccessType;
import com.usoit.api.data.model.Role;
import com.usoit.api.data.model.User;
import com.usoit.api.data.vo.RestAccessTypeUser;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.data.vo.RestRole;
import com.usoit.api.data.vo.RestRoleOption;
import com.usoit.api.model.request.ReqAccess;
import com.usoit.api.model.request.ReqAccessType;
import com.usoit.api.model.request.ReqRole;
import com.usoit.api.services.AccessServices;
import com.usoit.api.services.AccessTypeServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.RoleServices;
import com.usoit.api.services.UserServices;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestRoleController {
	
	@Autowired
	private RoleServices roleServices;
	
	@Autowired
	private AccessTypeServices accessTypeServices;
	
	@Autowired
	private AccessServices accessServices;
	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private UserServices userServices;
	
	private User cUser;
	
	private List<Role> roles;
	
	private List<RestRoleOption> restRoles;
	
	private List<AccessType> accessTypes;
	
	private List<RestAccessTypeUser> restAccessTypes;

	private List<ReqAccessType> reqAccessTypes;

	private List<Role> generalRoleList;

	private List<RestRoleOption> restGenRole;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllRole(Principal principal, HttpServletRequest httpServletRequest) {
		
		cUser = userServices.getUerById(2);
		Access access = helperServices.getAccessByUser(cUser, "user", 3);
		
		if (access.getAll() == 1) {
			setRestRoles();
			
			System.out.println("Role Size: " + restRoles.size());
			return ResponseEntity.ok(restRoles);
		}else {
			
			setGeneralRole();
			System.out.println("Role Gen Size: " + restGenRole.size());
			return ResponseEntity.ok(restGenRole);
		}
		
	}
	
	

	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getRoleById(Principal principal, HttpServletRequest request, @PathVariable("id") String publicId) {
		
		
		RestRole restRole = new RestRole();
		
		if (publicId != null) {
			
			System.out.println("Public ID Lenght: " + publicId.length());
			
			if (publicId.length() == 35) {
				System.out.println("Pass Data: ");
				
				restRole = roleServices.getRestRoleByPublicId(publicId);
			}
		}
		
		
		return ResponseEntity.ok(restRole);
	}
	
	@RequestMapping(value = "/access/add", method = RequestMethod.GET)
	public ResponseEntity<?> getRoleForAdd(Principal principal, HttpServletRequest httpServletRequest) {
		
		System.out.println("Role Add Action: !!" );
		setReqAccessTypes();
		
		ReqRole role = new ReqRole();
		role.setAuthStatus(false);

		role.setAccesses(new ArrayList<>());

		for (ReqAccessType accessType : reqAccessTypes) {

			ReqAccess access = new ReqAccess();
			access.setAccessType(accessType);
			role.getAccesses().add(access);

			// role.getAccessTypes().add(accessType);
		}
		
		return ResponseEntity.ok(role);
	}
	
	@RequestMapping(value = "/access-tyeps", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllAccesstypes(Principal principal, HttpServletRequest httpServletRequest) {
		
		setReqAccessTypes();
		
		return ResponseEntity.ok(reqAccessTypes);
	}
	
	@RequestMapping(value = "/role", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUpdateRoleAction(@RequestBody ReqRole reqRole, Principal principal, HttpServletRequest request) {
		
		if (reqRole != null) {
			
			if(reqRole.getId() > 0) {
				
				Role role = DozerMapper.parseObject(reqRole, Role.class);
				
				if (role != null) {
					
					System.out.println("Role Mapping Done: " + role.getId() + " Name: " + role.getName());
					
					if(role.getAccesses().get(0) != null) {
						System.out.println("Proper Mapping Done" + role.getAccesses().get(0).getId());
					}else {
						System.out.println("Proper Mapping Error ");
					}
					
					if (role.getId() > 0) {
						
						if (roleServices.update(role)) {
							
							return ResponseEntity.ok("Role Updated !!");
						}
					}
				}
				
			}else {
				return ResponseEntity.badRequest().body("Given Data mismatch . Please try again later !!");
			}
		}
		
		
		return ResponseEntity.badRequest().body("You Cann't Access this option. Please contact Administartor !!");
	}
	
	@RequestMapping(value = "/role", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSaveRoleAction(@RequestBody ReqRole reqRole, Principal principal, HttpServletRequest request) {
		
		if (reqRole != null) {
			
			if(reqRole.getId() > 0) {
				
				Role role = DozerMapper.parseObject(reqRole, Role.class);
				
				if (role != null) {
					
					System.out.println("Role Mapping Done: " + role.getId() + " Name: " + role.getName());
					
					if(role.getAccesses().get(0) != null) {
						System.out.println("Proper Mapping Done" + role.getAccesses().get(0).getId());
					}else {
						System.out.println("Proper Mapping Error ");
					}
					
					if (role.getId() > 0) {
						
						role.setPublicId(helperServices.getRandomString(35));
						
						if (roleServices.save(role)) {
							
							return ResponseEntity.ok("Role Save !!");
						}
					}
				}
				
			}else {
				return ResponseEntity.badRequest().body("Given Data missmatch . Please try again later !!");
			}
		}
		
		
		return ResponseEntity.badRequest().body("You Cann't Access this option. Please contact Administartor !!");
	}
	
	private void setGeneralRole() {
		
		generalRoleList = roleServices.getAllGeneralRole();
		
		if (generalRoleList != null) {
			
			restGenRole = DozerMapper.parseObjectList(generalRoleList, RestRoleOption.class);
		}
		
	}
	
	private void setReqAccessTypes() {
		
		setAllAccessType();
		
		reqAccessTypes = DozerMapper.parseObjectList(accessTypes, ReqAccessType.class);
		
		System.out.println(" Access Type Dozer Converter: " + reqAccessTypes.size());
		
	}


	private void setAllAccessType() {
		
		if (accessTypes == null) {
			
			accessTypes = accessTypeServices.getAllAccessType();
			
		}else {
			
			long size = accessTypes.size();
			long count = accessTypeServices.getCount();
			
			if (size != count) {
				
				accessTypes = accessTypeServices.getAllAccessType();
			}
		}
		
	}


	private void setRestRoles() {
		
		setRole();
		
		restRoles = DozerMapper.parseObjectList(roles, RestRoleOption.class);
		
		System.out.println("Rest Roel Size: " + restRoles.size());
		
	}

	private void setRole() {
	
		if (roles == null) {
			roles = roleServices.getAllRoles();
			
			
		}else {
			 long size = roles.size();
			 long count = roleServices.getCount();
			 
			 if (size != count) {
				
				 roles = roleServices.getAllRoles();
			} 
			
		}
		
	}

}
