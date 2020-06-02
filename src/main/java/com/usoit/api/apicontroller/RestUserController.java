package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.UserMapper;
import com.usoit.api.data.model.User;
import com.usoit.api.data.model.UserAddress;
import com.usoit.api.data.model.UserTemp;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.data.vo.RestUser;
import com.usoit.api.data.vo.RestUserDetails;
import com.usoit.api.model.request.ReqLoginData;
import com.usoit.api.model.request.ReqUpdateUser;
import com.usoit.api.model.request.ReqUser;
import com.usoit.api.payload.JWTLoginSucessReponse;
import com.usoit.api.security.config.JwtTokenProvider;
import com.usoit.api.security.config.SecurityConstants;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.TempUserServices;
import com.usoit.api.services.UserServices;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser" })
public class RestUserController {

	@Autowired
	private UserServices userServices;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private UserMapper userMapper;

	private List<User> users;

	private List<RestUser> restUserList;

	private List<RestUser> updateApprovePendinRestUsers;

	private List<User> updatePandingUsers;

	private List<User> updateableUsers;

	private List<RestUser> updateableUserList;

	private User authUser;

	@Autowired
	private TempUserServices tempUserServices;

	private List<User> userAddPandingUsers;

	private List<RestUser> userRestApprovalPanding;

	private List<RestUser> restRejectedUsers;

	private List<User> rejectedUsers;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllUsers(Principal principal, HttpServletRequest httpServletRequest) {

		User user = null;
		Map<String, RestAccessUser> access = new HashMap<>();
		user = helperServices.getUserByPrincipal(principal);

		if (user != null) {

			access = helperServices.getRestAccessByUser(user);
		}

		RestAccessUser accessUser = access.get("user");

		if (accessUser != null) {

			if (accessUser.getNoAccess() != 1 || accessUser.getAll() == 1) {
				String msg = "";
				setRestUsers(msg);
				return ResponseEntity.ok(restUserList);
			}
		}

		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserLoginAction(@RequestBody ReqLoginData loginData, BindingResult bindingResult) {

		System.out.println("Run User Controller User Login");
		// ResponseEntity<?>

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
	}

	@RequestMapping(value = "/user/access/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllAccessByUserId(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {

		System.out.println("Access Run by Request");

		if (principal != null) {

			System.out.println("Principal: " + principal.getName());

			if (helperServices.isValidAndLenghtCheck(pubId, 30)) {

				User user = userServices.getUserByPublicID(pubId);

				if (user != null) {

					Map<String, RestAccessUser> userAccessMap = helperServices.getRestAccessByUser(user);

					if (userAccessMap != null) {

						return ResponseEntity.ok(userAccessMap);
					}
				}
			}
		}

		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserAddAction(Principal principal, HttpServletRequest request,
			@RequestBody ReqUser reqUser) {

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);

		User user = userMapper.getUser(reqUser);

		if (accessMap != null) {

			RestAccessUser accessUser = accessMap.get("user");
			if (accessUser == null) {
				return ResponseEntity.ok("Login And try Again ");
			} else {

				if (accessUser.getAdd() == 1 || accessUser.getAll() == 1) {
					
				}else {
					return ResponseEntity.ok("You cann't Access this options Please contact Administartor ");
				}
			}
		}

		// Set Access controls End

		if (user.getUserAddresses() != null) {

			for (UserAddress userAddress : user.getUserAddresses()) {
				System.out.println("Address Title: " + userAddress.getTitle() + " Country Name: "
						+ userAddress.getCountry().getName());
			}
		}

		if (userServices.save(user)) {
			return ResponseEntity.ok("Data Recive & Save!!");
		}

		return ResponseEntity.ok("Data Recive");
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserForEditByPublicId(Principal principal, @PathVariable("id") String pubId,
			HttpServletRequest request) {

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				return ResponseEntity.ok("Login And try Again ");
			} else {

				if (accessUser.getEdit() == 1 || accessUser.getAll() == 1) {
					
				}else {
					return ResponseEntity.ok("You cann't Access this options Please contact Administartor ");
				}
			}
		}

		// Set Access controls End

		if (pubId != null) {

			if (pubId.length() == 30) {

				User user = userServices.getUserByPublicID(pubId);

				ReqUpdateUser reqUser = null;
				if (user.getAuthenticationStatus() == 0) {

					reqUser = userMapper.getReqUser(user);
				} else {

					if (accessMap != null) {

						if (user.getId() == authUser.getId() || accessUser.getAll() == 1) {
							reqUser = userMapper.getReqUser(user);
						} else {
							return ResponseEntity.accepted().body(
									"Access denied, You can't Access this account update & you Access Lavel Not applicable !!");
						}

					} else {

						return ResponseEntity.accepted().body("Access denied, You can't Access this account update");

					}

				}

				if (reqUser != null) {

					return ResponseEntity.ok(reqUser);
				}
			}
		}

		return ResponseEntity.accepted().body("User Not Found by given Id");
	}

	@RequestMapping(value = "/edit-users", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getEditableUsers(Principal principal, HttpServletRequest request) {

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getEdit() == 1 || accessUser.getAll() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		List<String> errors = new ArrayList<>();

		setUpdateableRestUsers(errors);

		if (updateableUserList != null) {

			return ResponseEntity.ok(updateableUserList);
		}

		return ResponseEntity.accepted().body(errors);
	}

	@RequestMapping(value = "/user/reject/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getAddRejectAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String publicId) {

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getAdd() == 1 || accessUser.getAll() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		String msg = "You can't Access this option Or Services";

		if (helperServices.isValidAndLenghtCheck(publicId, 30)) {

			if (userServices.addRejectUserByPublicId(publicId, msg)) {

				msg = "User Rejected Success !!";

			}

		}

		return ResponseEntity.ok(("You can't Access this option Or Services"));
	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllRejectedUsers(Principal principal, HttpServletRequest request) {

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getAdd() == 1 || accessUser.getAll() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		List<String> msg = new ArrayList<>();

		setRejectedRestUsers(msg);

		if (restRejectedUsers != null) {

			return ResponseEntity.ok(restRejectedUsers);
		}

		return ResponseEntity.accepted().body(msg);
	}

	@RequestMapping(value = "/user/approve/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getUserAddApproveAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getApprove() == 1 || accessUser.getAll() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		String msg = "You can't access this option, Please contact administrator. Thanks";

		if (helperServices.isValidAndLenghtCheck(pubId, 30)) {

			if (userServices.addApproveAction(pubId, msg)) {

				msg = "User Approve Success";
			}
		}

		return ResponseEntity.accepted().body(msg);
	}

	@RequestMapping(value = "/user/inactive/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getUserInactiveAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {
		
		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getApprove() == 1 || accessUser.getAll() == 1 || accessUser.getUpdateApproval() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		String msg = "You can't access this option, Please contact administrator. Thanks";

		if (helperServices.isValidAndLenghtCheck(pubId, 30)) {

			if (userServices.getInactiveActionByPubId(pubId, msg)) {

				ResponseEntity.ok("User Iactive Success");
			}
		}

		return ResponseEntity.accepted().body(msg);
	}

	@RequestMapping(value = "/user/active/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getUserActiveAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {
		
		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getApprove() == 1 || accessUser.getAll() == 1 || accessUser.getUpdateApproval() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		String msg = "You can't access this option, Please contact administrator. Thanks";

		if (helperServices.isValidAndLenghtCheck(pubId, 30)) {

			if (userServices.getActiveActionByPubId(pubId, msg)) {

				ResponseEntity.ok("User Active Success");
			}
		}

		return ResponseEntity.accepted().body(msg);
	}

	private void setUpdateableRestUsers(List<String> errors) {

		setUpdateUsers(errors);

		if (updateableUsers != null) {

			updateableUserList = userMapper.getRestUsers(updateableUsers);

			if (updateableUserList != null) {
				errors.add("Updgradeable User Mapping Failed");
			}
		} else {
			errors.add("Upgradeable User Not found !!");
		}

	}

	private void setUpdateUsers(List<String> errors) {

		updateableUsers = userServices.getAllActiveUser();

	}

	@RequestMapping(value = "/add-approvepanding", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllPandingUsers(Principal principal, HttpServletRequest request) {
		
		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getApprove() == 1 || accessUser.getAll() == 1 ) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End
		
		List<String> msg = new ArrayList<>();

		setAllAddPandingUsers(msg);

		if (userRestApprovalPanding != null) {

			return ResponseEntity.ok(userRestApprovalPanding);
		} else {
			msg.add("User Can't Mapp Contact Administrator !! ");
		}

		return ResponseEntity.accepted().body(msg);
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserUpdateAction(Principal principal, @RequestBody UserTemp userTemp,
			HttpServletRequest request) {

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getAll() == 1 || accessUser.getEdit() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		if (userTemp != null) {

			System.out.println("User Temp Not Null !!");
			if (!userTemp.getPublicId().isEmpty()) {

				String publicId = userTemp.getPublicId();

				if (userServices.updateUserAddTempUser(publicId)) {

					if (tempUserServices.saveTemUser(userTemp)) {
						return ResponseEntity.ok(new String("User Update Request Taken :) "));
					} else {

						if (userServices.reverseTempUpdate(publicId)) {

							return ResponseEntity.ok(new String("User DB Update Save Failed Temp User !!! "));
						}

						return ResponseEntity.ok(new String("User DB Update Save Failed Temp User :) "));

					}

				} else {
					return ResponseEntity.accepted().body("Data Recive But Save & Update Failed !!");
				}
			}

		}

		return ResponseEntity.accepted().body("Data Receive & User Null Or Data Miss Match Error!!");
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserForDetailsView(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {
		
		
		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;
		
		User authUser = userServices.getUserByPersonalEmail(principal.getName());

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {
				
				String pIdDb = authUser.getPublicId();
				String reqId = pubId;

				if (accessUser.getAll() == 1 || pIdDb.equals(reqId) ) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		if (pubId != null) {

			if (pubId.length() == 30) {

				User user = userServices.getUserByPublicID(pubId);

				if (user != null) {

					RestUserDetails restUser = userMapper.getRestUserDetails(user);

					if (restUser != null) {

						return ResponseEntity.ok(restUser);

					} else {
						return ResponseEntity.accepted().body("User Data Can't Mapp !! :)");
					}
				}
			}
		}

		return ResponseEntity.accepted().body("User Not found by given ID: ");
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllUpdatePandingUsers(Principal principal, HttpServletRequest request) {


		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getAll() == 1 || accessUser.getUpdateApproval() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End
		
		List<String> error = new ArrayList<>();

		setRestUpdateUsers();

		if (updateApprovePendinRestUsers != null) {

			return ResponseEntity.ok(updateApprovePendinRestUsers);
		}

		error.add(new String("Update Approval Panding User(s) not Found "));
		error.add("true");

		return ResponseEntity.ok(error);
	}

	@RequestMapping(value = "/usertemp/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTempUserByPublicId(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {
		

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getAll() == 1 || accessUser.getEdit() == 1 || accessUser.getUpdateApproval() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		if (pubId != null) {

			if (pubId.length() == 30) {

				UserTemp tempUser = tempUserServices.getUserTempByPubIdAlive(pubId);

				if (tempUser != null) {

					RestUserDetails restUser = userMapper.getRestUserByTempUser(tempUser);

					if (restUser != null) {

						return ResponseEntity.ok(restUser);
					} else {
						return ResponseEntity.accepted().body("User Temp Mapping Error!");
					}
				} else {
					return ResponseEntity.accepted().body("User Temp Not Found");
				}
			}

		}

		return ResponseEntity.accepted().body("User Temp Not fond by given ID:");
	}

	@RequestMapping(value = "/update/approve/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getUpdateUserApproveAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String publicId) {
		

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getAll() == 1 || accessUser.getUpdateApproval() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		if (publicId != null) {

			if (publicId.length() == 30) {

				UserTemp tempUser = tempUserServices.getUserTempByPubId(publicId);

				if (tempUser == null) {
					return ResponseEntity.accepted().body("Update Failed Requerment Data Not Found");
				}

				User user = userMapper.getUserByTempUser(tempUser);

				if (user != null) {

					if (userServices.updateUserByTempUser(user)) {

						if (tempUserServices.updateUserData(tempUser)) {
							return ResponseEntity.ok("Update Success");
						}

					} else {
						return ResponseEntity.accepted().body("Update Failed Requerment Not match");
					}
				}
			}
		}

		return ResponseEntity.accepted().body("Given Id not match!!");
	}

	@RequestMapping(value = "/update/reject/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getRejectAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String publicId) {
		

		List<String> msgList = new ArrayList<>();

		// Set Access controls Start
		Map<String, RestAccessUser> accessMap = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser accessUser = null;

		if (accessMap != null) {

			accessUser = accessMap.get("user");
			if (accessUser == null) {
				msgList.add(new String("Login And try Again "));
				return ResponseEntity.ok(msgList);
			} else {

				if (accessUser.getAll() == 1 || accessUser.getEdit() == 1 || accessUser.getAdd() == 1 || accessUser.getApprove() == 1 || accessUser.getUpdateApproval() == 1) {
					
				}else {
					msgList.add(new String("You cann't Access this options Please contact Administartor "));
					return ResponseEntity.ok(msgList);
				}
			}
		}

		// Set Access controls End

		if (publicId != null) {

			if (publicId.length() == 30) {

				if (userServices.getUpdateUserReject(publicId)) {

					if (tempUserServices.getRejectedRequest(publicId)) {

						ResponseEntity.ok("Status Updated Done!! ");

					} else {
						ResponseEntity.accepted().body("Status Updated !!");
					}

				} else {
					ResponseEntity.accepted().body("Status Update Failed!!");
				}
			}
		}

		return ResponseEntity.accepted().body("Given Id not match!!");
	}

	private void setRejectedRestUsers(List<String> msg) {

		setRejectedUsers(msg);

		if (rejectedUsers != null) {

			restRejectedUsers = userMapper.getRestUsers(rejectedUsers);
		} else {
			msg.add("Rejected User not set yet!!");

		}

	}

	private void setRejectedUsers(List<String> msg) {

		rejectedUsers = userServices.getAllRejectedUser();

	}

	private void setAllAddPandingUsers(List<String> msg) {

		userAddPandingUsers = userServices.getAllPendingUser();

		if (userAddPandingUsers != null) {

			System.out.println("Approve Panding Size: " + userAddPandingUsers.size());
			userRestApprovalPanding = userMapper.getRestUsers(userAddPandingUsers);

		} else {

			msg.add("Approval Pending user not found");
		}

	}

	private void setRestUpdateUsers() {

		setUpdateUsers();

		if (updatePandingUsers != null) {

			System.out.println("Update Panding User Size: " + updatePandingUsers.size());
			updateApprovePendinRestUsers = userMapper.getRestUsers(updatePandingUsers);
			System.out.println("Update Panding Rest User Size: " + updateApprovePendinRestUsers.size());

		} else {
			System.out.println("Update User's Not Found");
		}

	}

	private void setUpdateUsers() {

		if (updatePandingUsers == null) {

			updatePandingUsers = userServices.getUpdatePandingUser();

		} else {

			long size = updatePandingUsers.size();
			long count = userServices.getCount();

			if (size != count) {

				updatePandingUsers = userServices.getUpdatePandingUser();
			}
		}

	}

	private void setRestUsers(String msg) {

		setUsers(msg);

		System.out.println("User Mapper Func");
		restUserList = userMapper.getRestUsers(users);

		System.out.println("Rest user Size: " + restUserList.size());

	}

	private void setUsers(String msg) {

		users = userServices.getAllConfrimUsers(msg);

	}

}
