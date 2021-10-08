package com.usoit.api.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.usoit.api.model.Access;
import com.usoit.api.model.Country;
import com.usoit.api.model.Credential;
import com.usoit.api.model.Department;
import com.usoit.api.model.Designation;
import com.usoit.api.model.Gender;
import com.usoit.api.model.LoginData;
import com.usoit.api.model.MaritalStatus;
import com.usoit.api.model.ReceivePass;
import com.usoit.api.model.Role;
import com.usoit.api.model.User;
import com.usoit.api.model.UserAddress;
import com.usoit.api.model.ValidPass;
import com.usoit.api.model.response.RestPassword;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.DepartmentServices;
import com.usoit.api.services.DesignationServices;
import com.usoit.api.services.EnStringServices;
import com.usoit.api.services.GenderServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.MaritalStatusServices;
import com.usoit.api.services.RoleServices;
import com.usoit.api.services.UserServices;

@Controller
@RequestMapping("/user")
@SessionAttributes(names = { "currentUser" })
public class UserController {

	@Autowired
	private UserServices userServices;

	@Autowired
	private RoleServices roleServices;

	@Autowired
	private DesignationServices designationServices;

	@Autowired
	private DepartmentServices departmentServices;

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private MaritalStatusServices maritalStatusServices;

	@Autowired
	private GenderServices genderServices;

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private EnStringServices enStringServices;

	private List<User> users;

	private List<User> pendingUsers;

	private List<Role> roles;

	private List<MaritalStatus> maritalStatus;

	private List<Gender> genders;

	private List<Department> departments;

	private List<Designation> designations;

	private User cUser;

	private List<Country> countries;

	private static Credential currentCredential;

	@RequestMapping("")
	public String getUserIndexPage(Model model, HttpSession httpSession) {

		return "redirect:/user/view?page=0";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET, params = { "page" })
	public String getUserViewPage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "page") int page) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1 || access.getApprove() == 1 || access.getEdit() == 1
						|| access.getUpdateApproval() == 1 || access.getView() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/";
				}

			}
		}
		// Access Start

		setUserList();

		if (users == null) {
			users = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > users.size()) {

			totalPage = (users.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), users.size());

		model.addAttribute("users", users.subList(fIndex, tIndex));

		model.addAttribute("cPage", page);
		model.addAttribute("totalPAge", totalPage);

		return "/pages/user/view";
	}

	@RequestMapping(value = "/logout")
	public String getUserLogOutAction(Model model, HttpSession httpSession, SessionStatus sessionStatus,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		AuthenticateAction authenticateAction = (AuthenticateAction) SecurityContextHolder.getContext()
				.getAuthentication();

		if (authenticateAction != null) {
			new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse,
					(Authentication) authenticateAction);
		}

		return "redirect:/user/login";
	}

	@RequestMapping(value = "/p-view", method = RequestMethod.GET, params = { "page" })
	public String getPendingUserViewPage(Model model, HttpSession httpSession,
			@RequestParam(defaultValue = "0", value = "page") int page) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1 || access.getApprove() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		setPendingUserList();

		if (pendingUsers == null) {
			pendingUsers = new ArrayList<>();
		}

		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();

		if (pageSize > pendingUsers.size()) {

			totalPage = (pendingUsers.size() / pageSize);
		}

		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), pendingUsers.size());

		model.addAttribute("users", pendingUsers.subList(fIndex, tIndex));

		model.addAttribute("cPage", page);
		model.addAttribute("totalPAge", totalPage);

		return "/pages/user/pending-view";
	}

	@RequestMapping("/add")
	public String getUserAddPage(Model model, HttpSession httpSession) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/user";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		initVariable();

		if (genders == null) {

			genders = new ArrayList<>();
		}

		if (maritalStatus == null) {

			maritalStatus = new ArrayList<>();
		}

		if (departments == null) {
			departments = new ArrayList<>();
		}

		if (designations == null) {
			designations = new ArrayList<>();
		}

		if (roles == null) {
			roles = new ArrayList<>();
		}

		if (countries == null) {

			countries = new ArrayList<>();
		}

		User preSetUser = new User();
		preSetUser.setUserAddresses(new ArrayList<>());

		UserAddress address1 = new UserAddress();
		address1.setTitle("Present Address");

		UserAddress address2 = new UserAddress();
		address2.setTitle("Permanent Address");

		preSetUser.getUserAddresses().add(address1);
		preSetUser.getUserAddresses().add(address2);

		model.addAttribute("genders", genders);
		model.addAttribute("maritalStatus", maritalStatus);
		model.addAttribute("departments", departments);
		model.addAttribute("designations", designations);
		model.addAttribute("roles", roles);

		model.addAttribute("user", preSetUser);

		model.addAttribute("countries", countries);

		model.addAttribute("fUser", new User());

		return "/pages/user/add";
	}

	@RequestMapping(value = "/add-data", method = RequestMethod.POST)
	public String getAddUserAction(Model model, HttpSession httpSession, @ModelAttribute("user") User user) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/user";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		if (user != null) {

			user.setProfileIimage(uploadAndSaveImage(user.getPfImageFile(), "prfile"));
			user.setUserSignaturesUrl(uploadAndSaveImage(user.getSignImgFile(), "signature"));
			user.setApplicationForJobUrl(uploadAndSaveImage(user.getApplicationForJobFile(), "appforjob"));
			user.setAppointmentLetterUrl(uploadAndSaveImage(user.getAppointmentLetterFile(), "appointletter"));
			user.setBachelorHonoursUrl(uploadAndSaveImage(user.getBachelorHonoursFile(), "bachelor"));
			user.setBirthCertificateUrl(uploadAndSaveImage(user.getBirthCertificateFile(), "birth"));
			user.setCaFcaCmaUrl(uploadAndSaveImage(user.getCaFcaCmaFile(), "cafcacma"));
			user.setDiplomaUrl(uploadAndSaveImage(user.getDiplomaFile(), "diploma"));
			user.setEmploymentUrl(uploadAndSaveImage(user.getEmploymentFile(), "employee"));

			user.setFieldVerificationUrl(uploadAndSaveImage(user.getFieldVerificationFile(), "field_ver"));
			user.setHscEquivalentUrl(uploadAndSaveImage(user.getHscEquivalentFile(), "hsc"));
			user.setJobAgreementUrl(uploadAndSaveImage(user.getJobAgreementFile(), "job_agre"));
			user.setMastersUrl(uploadAndSaveImage(user.getMastersFile(), "master"));
			user.setNationalityCertificateUrl(uploadAndSaveImage(user.getNationalityCertificateFile(), "national"));
			user.setNidUrl(uploadAndSaveImage(user.getNidFile(), "nid"));
			user.setPfCaFcaCmaUrl(uploadAndSaveImage(user.getPfCaFcaCmaFile(), "pfcafcma"));
			user.setPledgeUrl(uploadAndSaveImage(user.getPledgeFile(), "pledge"));
			user.setResumeUrl(uploadAndSaveImage(user.getResumeFile(), "resume"));
			user.setSecurityDeedUrl(uploadAndSaveImage(user.getSecurityDeedFile(), "security_deed"));
			user.setSscEquivalentUrl(uploadAndSaveImage(user.getSscEquivalentFile(), "sscequ"));

			if (user.getUserAddresses() != null) {

				for (UserAddress address : user.getUserAddresses()) {

					address.setUser(user);

				}
			}

			if (!userServices.save(user)) {
				System.out.println("User Save Faild!!");
				return "redirect:/failure/ns";
			} else {
				System.out.println("User Save Done !!");
			}
		}

		return "redirect:/user/view?page=0";
	}

	@RequestMapping(value = "/login")
	public String getUserLoginPage(Model model, HttpSession httpSession, HttpServletRequest servletRequest) {

		model.addAttribute("loginData", new LoginData());
		return "/pages/user/login";
	}

	/*
	 * @RequestMapping(value = "/login-submit", method = RequestMethod.POST) public
	 * String getLoginActionPage(Model model, HttpSession httpSession,
	 * 
	 * @ModelAttribute("login-submit") LoginData loginData) {
	 * 
	 * User user = null;
	 * 
	 * System.out.println("User Login Submit run");
	 * 
	 * if (loginData != null) {
	 * 
	 * System.out.println("Login Data not null");
	 * 
	 * if (loginData.getEmail() != null && loginData.getPassword() != null) {
	 * 
	 * String hasPassword = enStringServices.getEncData(loginData.getPassword());
	 * 
	 * System.out.println("Login Data field not null");
	 * 
	 * user = userServices.getUserByPersonalEmail(loginData.getEmail());
	 * 
	 * if (user == null) {
	 * 
	 * System.out.println("Pesonal Email Data not found");
	 * 
	 * user = userServices.getUserByOfficialEmail(loginData.getEmail());
	 * 
	 * if (user == null) { System.out.println("Official Email Data not found"); user
	 * = userServices.getUsrByPersonalPhone(loginData.getEmail());
	 * 
	 * if (user == null) {
	 * 
	 * System.out.println("Office Phone Data not found");
	 * 
	 * user = userServices.getUserByOfficePhoneNo(loginData.getEmail());
	 * 
	 * if (user == null) { System.out.println("Pesonal Phone Data not found");
	 * 
	 * return "redirect:/user/login"; } else {// Official Phone
	 * 
	 * System.out.println("Official Phone Data Found");
	 * 
	 * user = userServices.chekUserValid(user, hasPassword);
	 * 
	 * if (user != null) {
	 * 
	 * System.out.println("check user Not null"); model.addAttribute("currentUser",
	 * user); }
	 * 
	 * }
	 * 
	 * } else {// Personal Phone System.out.println("Personal Phone Data Found");
	 * 
	 * user = userServices.chekUserValid(user, hasPassword);
	 * 
	 * if (user != null) {
	 * 
	 * System.out.println("check user Not null");
	 * 
	 * model.addAttribute("currentUser", user); } }
	 * 
	 * } else { // Official Email
	 * 
	 * user = userServices.chekUserValid(user, hasPassword);
	 * System.out.println("Official Email Data Found"); if (user != null) {
	 * System.out.println("check user Not null"); model.addAttribute("currentUser",
	 * user); } }
	 * 
	 * } else { // Personal Email
	 * 
	 * user = userServices.chekUserValid(user, hasPassword);
	 * System.out.println("Personal Email Data Found"); if (user != null) {
	 * System.out.println("check user Not null"); model.addAttribute("currentUser",
	 * user); }
	 * 
	 * } } }
	 * 
	 * return "redirect:/"; }
	 */

	@RequestMapping(value = "/details", method = RequestMethod.GET, params = { "id" })
	public String getUserDetailPage(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {

			System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

			if (access.getAdd() == 1 || access.getAll() == 1 || access.getApprove() == 1 || access.getEdit() == 1
					|| access.getUpdateApproval() == 1 || access.getView() == 1 || id == cUser.getId()) {

				System.out.println("Access Get Add Pass & All Access !!");

			} else {
				return "redirect:/user";
			}

		}
		// Access Start

		User user = userServices.getUerById(id);

		if (user == null) {
			return "redirect:/user/view?page=0";
		}

		model.addAttribute("user", user);

		return "/pages/user/user-detail";
	}

	@RequestMapping(value = "/set-pass", method = RequestMethod.GET, params = { "uid" })
	public String getChangePasswordPage(Model model, HttpSession httpSession, @RequestParam("uid") int uId) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/user";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getView() == 1 || access.getApprove() == 1 || access.getAll() == 1 || uId == cUser.getId()) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		if (uId > 0) {

			Credential acCredential = null;
			User user = userServices.getUerById(uId);

			if (user != null) {

				if (user.getCredentials() != null) {

					for (Credential credential : user.getCredentials()) {

						if (!credential.getPassword().isEmpty() && credential.getStatus() == 1) {
							acCredential = credential;

							currentCredential = credential;

							System.out.println("Current pass: " + acCredential.getPassword());
						}

					}
				}

				System.out.println("Pass: " + acCredential.getPassword());

				model.addAttribute("fRestPass", new RestPassword());

				model.addAttribute("activePassSet", acCredential);

				model.addAttribute("slUser", user);
			} else {

				return "redirect:/user/view?page=0";
			}
		}

		return "/pages/user/set-pass";
	}

	@RequestMapping(value = "/credential", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCredential(@Valid @RequestBody ReceivePass receivePass, Errors errors,
			HttpSession httpSession) {

		boolean isMatch = false;

		ValidPass result = new ValidPass();

		if (receivePass != null && currentCredential != null) {

			String oPass = enStringServices.getEncData(receivePass.getOldpass());
			String cPass = currentCredential.getPassword();

			System.out.println("Old Pass: " + oPass);

			System.out.println("C Pass: " + cPass);

			if (cPass.equals(oPass)) {

				isMatch = true;

				System.out.println("Data match !!");
			} else {
				System.out.println("not Match !!");
				isMatch = false;
			}

		}

		result.setMatch(isMatch);

		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/reset-update", method = RequestMethod.POST)
	public String getSetPasswordAction(Model model, HttpSession httpSession,
			@ModelAttribute("restPass") RestPassword restPassword) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/user";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (cUser.getId() == restPassword.getUserId()) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		if (cUser.getId() != restPassword.getUserId()) {
			return "redirect:/user";
		}

		String patternString = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";

		Pattern pattern = Pattern.compile(patternString);

		Matcher matcher = pattern.matcher(restPassword.getNPass());

		if (restPassword != null) {

			if (currentCredential.getUser().getId() == restPassword.getUserId()) {

				System.out.println("Usr Id Match !!");

				System.out.println("N Pass: " + restPassword.getNPass());

				System.out.println("C Pass: " + restPassword.getCPass());

				String nPass = restPassword.getNPass();
				String cPass = restPassword.getCPass();

				if (nPass.equals(cPass)) {
					System.out.println("test Pass!!!!");

					if (nPass.matches(patternString)) {

						System.out.println("Pass Reg EXP!!!!");
					} else {
						System.out.println("Reg EXP else !!!!");
					}
				}

				if (restPassword.getNPass().equals(restPassword.getCPass())) {

					System.out.println("Password Match Confrim!!");

					if (!matcher.matches()) {
						System.out.println("Pattern not  match !!");
						return "redirect:/user/set-pass?uid" + restPassword.getUserId();
					} else {

						System.out.println("Pattern  match !!");
					}

					Credential credential = new Credential();
					credential.setPassword(restPassword.getNPass());
					credential.setStatus(1);
					credential.setUser(currentCredential.getUser());

					if (!userServices.updatePassword(credential)) {

						return "redirect:/failure/pu";
					}
				} else {

					System.out.println("Password not Match !!");
				}

			} else {

				System.out.println("Password Match not  Confrim!!");
			}

		}

		return "redirect:/user/";
	}

	@RequestMapping(value = "/approve", method = RequestMethod.GET, params = { "id" })
	public String getApprovePendinUserAction(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "user", 3);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/user";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getApprove() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		if (id > 0) {

			if (!userServices.updateApprove(id)) {

				return "redirect:/user/p-view?page=0";
			}
		}

		return "redirect:/user/";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = { "id" })
	public String getUserUpdatePage(Model model, HttpSession httpSession, @RequestParam("id") int id) {

		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return "redirect:/user";

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return "redirect:/user";

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getView() == 1 || access.getEdit() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return "redirect:/user";
				}

			}
		}
		// Access Start

		return "redirect:/user";
	}

	private void initVariable() {

		setGenderList();
		setDepartmentList();
		setDesignationList();
		setRoleList();
		setMaritalStatusList();
		setCountries();

	}

	private void setCountries() {

		if (countries == null) {

			countries = countryServices.getAllCountries();
		} else {
			long size = countries.size();
			long count = countryServices.getCount();

			if (size != count) {

				countries = countryServices.getAllCountries();
			}
		}

	}

	private void setMaritalStatusList() {

		if (maritalStatus == null) {

			maritalStatus = maritalStatusServices.getAllMaritalStatus();
		} else {
			long size = maritalStatus.size();
			long count = maritalStatusServices.getCount();

			if (size != count) {

				maritalStatus = maritalStatusServices.getAllMaritalStatus();
			}
		}

	}

	private void setRoleList() {

		if (roles == null) {

			roles = roleServices.getAllRoles();
		} else {
			long size = roles.size();
			long count = maritalStatusServices.getCount();

			if (size != count) {

				roles = roleServices.getAllRoles();
			}
		}

	}

	private void setDesignationList() {

		if (designations == null) {

			designations = designationServices.getAllDesignations();
		} else {
			long size = designations.size();
			long count = designationServices.getCount();

			if (size != count) {

				designations = designationServices.getAllDesignations();
			}
		}

	}

	private void setDepartmentList() {

		if (departments == null) {

			departments = departmentServices.getAllDepartments();
		} else {
			long size = departments.size();
			long count = departmentServices.getCount();

			if (size != count) {

				departments = departmentServices.getAllDepartments();
			}
		}

	}

	private void setGenderList() {

		if (genders == null) {

			genders = genderServices.getAllGenders();
		} else {
			long size = genders.size();
			long count = genderServices.getCount();

			if (size != count) {

				genders = genderServices.getAllGenders();
			}
		}

	}

	private void setUserList() {

		if (users == null) {

			users = userServices.getAllUser();
		} else {

			long size = users.size();
			long count = userServices.getCount();

			if (size != count) {

				users = userServices.getAllUser();
			}
		}

	}

	private String uploadAndSaveImage(MultipartFile mFile, String path) {

		return helperServices.uploadImageAndGetUrl(mFile, path);
	}

	private void setPendingUserList() {

		if (pendingUsers == null) {

			pendingUsers = userServices.getAllPendingUser();
		} else {

			long size = pendingUsers.size();
			long count = userServices.getCount();

			if (size != count) {
				pendingUsers = userServices.getAllPendingUser();
			}
		}

	}

}
