package com.usoit.api.enduser.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.enduser.services.CustomerServices;
import com.usoit.api.exception.InvalidLoginResponse;
import com.usoit.api.mapper.CustomerMapper;
import com.usoit.api.mapper.MailMapper;
import com.usoit.api.mapper.TravelerMapper;
import com.usoit.api.model.Customer;
import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.model.Passport;
import com.usoit.api.model.Traveler;
import com.usoit.api.model.request.ReqCustomerSignUp;
import com.usoit.api.model.request.ReqLoginData;
import com.usoit.api.model.request.ReqMailSender;
import com.usoit.api.model.request.ReqProfileImage;
import com.usoit.api.model.request.ReqTraveler;
import com.usoit.api.model.request.ReqVerifiyMail;
import com.usoit.api.model.response.ResEsCustomer;
import com.usoit.api.model.response.RespTraveler;
import com.usoit.api.model.response.RestEsCustomer;
import com.usoit.api.model.response.RestPrfileInfo;
import com.usoit.api.payload.JWTLoginSucessReponse;
import com.usoit.api.security.config.JwtTokenProvider;
import com.usoit.api.security.config.SecurityConstants;
import com.usoit.api.services.HelperAuthenticationServices;
import com.usoit.api.services.MailSenderServices;
import com.usoit.api.services.MailVerificationServices;
import com.usoit.api.services.PassportServices;
import com.usoit.api.services.TravelerServices;
import com.usoit.api.services.UtilsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/enu/v1/customers")
public class RestCustomerController {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerServices customerServices;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private HelperAuthenticationServices helperAuthenticationServices;

	@Autowired
	private UtilsServices utilsServices;

	@Autowired
	private TravelerMapper travelerMapper;

	@Autowired
	private MailVerificationServices mailVerificationServices;

	@Autowired
	private MailSenderServices mailSenderServices;

	@Autowired
	private MailMapper mailMapper;

	@Autowired
	private TravelerServices travelerServices;

	@Autowired
	private PassportServices passportServices;

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLoginAction(@RequestBody ReqLoginData loginDate,
			HttpServletRequest httpServletRequest) {

		String jwt = null;
		if (loginDate != null) {
			Customer customer = customerServices.getCustomerByUsername(loginDate.getUsername());

			if (customer != null) {
				if (passwordEncoder.matches(loginDate.getPassword(), customer.getPassword())) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							customer, null, Collections.emptyList());

					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

					log.info("Login Authentication Name, " + authentication.getName());

					jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateCustomerToken(authentication);

				} else {
					return ResponseEntity.ok(new InvalidLoginResponse());
				}

			}
		}

		return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
	}

	@PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSignUpAction(@RequestBody ReqCustomerSignUp customerSignUp,
			HttpServletRequest httpServletRequest) {

		log.info("Customer Sign Up Run ... ");

		Map<String, Object> map = new HashMap<>();
		map.put("status", false);
		map.put("message", "User Added failed");
		map.put("customer", customerSignUp);

		if (customerSignUp != null) {
			Customer customer = customerServices.getCustomerUsernameIsExist(customerSignUp.getEmail());

			// Customer Not Exist Create User Or Send To Login Page
			if (customer == null) {

				Customer nCustomer = customerMapper.mapCustomerSignUp(customerSignUp);

				if (nCustomer != null) {

					if (customerServices.addCustomer(nCustomer)) {
						RestEsCustomer respCustomer = customerMapper.mapSignUpRestCustomer(nCustomer);

						map.put("status", true);
						map.put("message", "Customer Added");
						map.put("customer", respCustomer);
					}
				}

			} else {
				map.put("message", "User Exist");
			}
		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/verify/mail", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSendVerifyMailAction(@RequestBody ReqVerifiyMail verify) {

		Map<String, Object> map = new HashMap<>();
		map.put("message", "Verify Mail Send failed");
		map.put("status", false);
		Customer customer = helperAuthenticationServices.getCurrentCustomer();

		if (customer != null) {

			log.info("Mail Verify customer " + customer.getPrimaryEmail());
			log.info("Customer ID " + customer.getPublicId());
			log.info("Verify ID   " + verify.getId());

			if (customer.getPublicId().equals(verify.getId())) {
				MailVerifiedToken token = mailVerificationServices
						.getVerificationTokenByCustomerID(customer.getPublicId());
				mailSenderServices.sendMailWithInlineResources(customer, "Verification Mail", token, null);
				map.put("message", "Mail send :) ");
				map.put("status", true);
			}

		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/send-mail", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMailSenderAction(@RequestBody ReqMailSender mailSender) {

		Map<String, Object> map = new HashMap<>();
		map.put("message", "Mail Send failed");
		map.put("status", false);
		MailVerifiedToken mailVerifiedToken = mailVerificationServices
				.getVerificationTokenByCustomerID(mailSender.getId());

		log.info("Mil Sender ID " + mailSender.getId());
		Customer customer = customerServices.getCustomerByPublicId(mailSender.getId());

		if (mailVerifiedToken != null && customer != null) {
			log.info("Mil Sender Token " + mailVerifiedToken.getDigitCode());
			if (mailSenderServices.sendMailWithInlineResources(customer, mailSender.getSubject(), mailVerifiedToken,
					null)) {
				map.put("message", "Mail Send");
				map.put("status", true);
			}
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping("/unicid")
	public ResponseEntity<?> getUnicIdTest() {

		String unicId = "";

		unicId = utilsServices.getUnicId();
		utilsServices.getCustomerGenaretedId();
		log.info("User Generated Unic ID: " + unicId);
		unicId = unicId + "   " + Integer.toString(unicId.length());
		mailSenderServices.getTestMailSendLink();
		return ResponseEntity.ok(unicId);
	}

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getCustomer(@PathVariable("id") String customerId) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found by Customer ID " + customerId);
		map.put("status", false);
		map.put("data", null);

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = { "/current" })
	public ResponseEntity<?> getCustomer() {

		log.info("Geting Customer ...");
		Map<String, Object> map = new HashMap<>();

		map.put("message", "It work :) ");
		map.put("status", false);
		map.put("customer", null);
		Customer customer = helperAuthenticationServices.getCurrentCustomer();
		log.info("Current Customer " + customer);
		if (customer != null) {
			log.info("Customer Found, " + customer.getPrimaryEmail());
			ResEsCustomer resEsCustomer = customerMapper.getEsCustomer(customer);
			map.put("customer", resEsCustomer);
			map.put("status", true);
		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/travelers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTravelUpdateAction(@RequestBody ReqTraveler travelerInf) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Update Customer Information");
		map.put("status", false);
		map.put("data", null);

		if (travelerInf != null) {
			log.info("Customer Traveler Info Update ... ");
			if (travelerInf.getStatus() == 1) {
				log.info("Customer Traveler Primary Info Update ... ");
				updateCustomerPersonalInformation(travelerInf, map);
			} else {
				log.info("Customer Traveler Info Update ... ");
				if (travelerServices.updateTraveler(travelerInf)) {
					map.put("message", "Update Traveler");
					map.put("status", true);
					map.put("data", null);
				}
			}
		}

		return ResponseEntity.ok(map);
	}
	
	@PostMapping(value = "/travelers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTravelAddAction(@RequestBody ReqTraveler travelerInf) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Create Customer Traveler Information");
		map.put("status", false);
		map.put("data", null);
		
		if (travelerInf != null) {
			log.info("Customer Traveler Info Add ... ");
			Customer customer = helperAuthenticationServices.getCurrentCustomer();
			
			if(travelerServices.createGuestTraveler(travelerInf, customer)) {
				map.put("message", "Create Traveler Success");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}
	
	@GetMapping(value = "/travelers")
	public ResponseEntity<?> getTravelAddAction() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Create Customer Traveler Information");
		map.put("status", false);
		map.put("travelers", null);
		Customer customer = helperAuthenticationServices.getCurrentCustomer();
		List<RespTraveler> travelers = travelerMapper.getRespTravelersWithActivePassport(travelerServices.getTravelersByCustomerId(customer.getId()));
		
		if (travelers != null) {
			log.info("Get Customer Travelers Info ... ");
						
			map.put("message", travelers.size()+ " Traveler (s) Found");
			map.put("status", true);
			map.put("travelers", travelers);
		}

		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/information")
	public ResponseEntity<?> getCustomerPesonalInforamtionAction() {

		log.info("Geting Customer ...");
		Map<String, Object> map = new HashMap<>();

		map.put("message", "It work :) ");
		map.put("status", false);
		map.put("customer", null);
		Customer customer = helperAuthenticationServices.getCurrentCustomer();
		log.info("Current Customer " + customer);
		if (customer != null) {
			log.info("Customer Found, " + customer.getPrimaryEmail());
			
			Traveler traveler = travelerServices.getPrimaryTravelerUsingCustomerID(customer.getId());
			log.info("Customer Travel information " + traveler.getFirstName());
			if(traveler != null) {
				Passport passport = passportServices.getActivePassportUsingTravelerID(traveler.getId());
				
				RestPrfileInfo restPrfileInfo = customerMapper.getCustomerPersonalInfo(customer, traveler, passport); 
				map.put("customer", restPrfileInfo);
				map.put("status", true);
			}
			
		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/profile-image", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProfileImageAddAction(@RequestBody ReqProfileImage image) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Profile Image added failed");
		map.put("status", false);
		map.put("data", null);
		Customer customer = helperAuthenticationServices.getCurrentCustomer();
		if(image != null && customer != null) {
			
			boolean status = customerServices.addOrUpdateProfileImage(customer ,image);
			
			if(status) {
				map.put("message", "Profile Image added");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}
	
	@PutMapping(value = "/profile-image", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProfileImageUpdateAction(@RequestBody ReqProfileImage image) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Profile Image Update failed");
		map.put("status", false);
		map.put("data", null);
		Customer customer = helperAuthenticationServices.getCurrentCustomer();
		if(image != null && customer != null) {
			
			boolean status = customerServices.addOrUpdateProfileImage(customer ,image);
			
			if(status) {
				map.put("message", "Profile Image updated");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}
	
	private void updateCustomerPersonalInformation(ReqTraveler travelerInf, Map<String, Object> map) {
		Customer customer = helperAuthenticationServices.getCurrentCustomer();

		if (customer != null) {
			log.info("Customer ID: " + customer.getId());
			Traveler traveler = customerServices.getCustomerInformation(customer.getId());

			if (traveler != null) {
				log.info("Customer Traveler Update began ...");
				if (travelerServices.updatePrimaryTraveler(traveler.getId(), travelerInf)) {
					map.put("message", "Update customer Personal Information");
					map.put("status", true);
					map.put("data", null);
				}
			} else {
				log.info("Customer Traveler Update began ...");
				traveler = travelerMapper.mapTraveler(travelerInf);
				traveler.setPrimary(true);
				traveler.setCustomer(customer);
				boolean status = travelerServices.addTraveler(traveler);
				log.info("Create Customer Traveler Information "+ status);
				if (status) {

					map.put("message", "Create customer Personal Information");
					map.put("status", true);
					map.put("data", null);
				}
			}

		}
	}

}
