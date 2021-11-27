package com.usoit.api.enduser.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.enduser.services.CustomerServices;
import com.usoit.api.model.Customer;
import com.usoit.api.model.MailVerifiedToken;
import com.usoit.api.model.PhoneVerificationToken;
import com.usoit.api.model.request.ReqResendVerify;
import com.usoit.api.model.request.ReqVerify;
import com.usoit.api.services.HelperAuthenticationServices;
import com.usoit.api.services.MailSenderServices;
import com.usoit.api.services.SMSSenderServices;
import com.usoit.api.services.VerificationServices;
import com.usoit.api.servicesimpl.MailSenderServicesImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/enu/v1/verify")
public class RestVerifyContoller {

	@Autowired
	private CustomerServices customerServices;

	@Autowired
	private VerificationServices verificationServices;

	@Autowired
	private HelperAuthenticationServices authHelperServices;
	
	@Autowired
	private MailSenderServices mailSenderServices;
	
	@Autowired
	private SMSSenderServices smsSenderServices;

	@RequestMapping(value = "/mail-token", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMailVerifyUsingLinkAction(@RequestBody ReqVerify reqVerify) {

		log.info("Mail Verify ...");
		if (reqVerify != null) {
			log.info("Verify ID: " + reqVerify.getId());
		}
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Mail Verify Link ...");
		map.put("status", false);

		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/sms", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSMSVerifyAction(@RequestBody ReqVerify reqVerify) {

		Map<String, Object> map = new HashMap<>();
		map.put("message", "SMS Verified failed");
		map.put("status", false);

		log.info("SMS Verify  ...");
		if (reqVerify != null) {
			log.info("Verify ID: " + reqVerify.getId());

			Customer customer = customerServices.getCustomerByPublicId(reqVerify.getId());

			if (customer != null) {

				PhoneVerificationToken token = verificationServices.getPhoneVerificationTokenById(reqVerify.getId());

				if (token != null) {
					log.info("Digital code " + token.getCode());
					log.info("Request Digital code " + reqVerify.getDc());
					if (token.getCode().equals(reqVerify.getDc())) {
						log.info("Code Match :) ");
						if (customerServices.verifingCustomerPhone(customer.getId())) {

							map.put("message", "SMS Verified Success");
							map.put("status", true);
						}
					}
				}
			}
		}

		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/mail", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMailVerifyCodeAction(@RequestBody ReqVerify reqVerify) {

		log.info("Mail Verify Via Code...");
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Mail Verify ...");
		map.put("status", true);

		if (reqVerify != null) {

			log.info("Verify ID: " + reqVerify.getId());

			Customer customer = customerServices.getCustomerByPublicId(reqVerify.getId());

			if (customer != null) {

				MailVerifiedToken token = verificationServices.getMailVerificationTokenById(reqVerify.getId());

				if (token != null) {
					log.info("Digital code " + token.getDigitCode());
					log.info("Request Digital code " + reqVerify.getDc());
					if (token.getDigitCode().equals(reqVerify.getDc())) {
						log.info("Code Match :) ");
						if (customerServices.verifingCustomerMail(customer.getId())) {

							map.put("message", "SMS Verified Success");
							map.put("status", true);
						}
					}
				}
			}

		}

		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/resend", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVerifyResendAction(@RequestBody ReqResendVerify resendVerify) {

		log.info("Mail Verify Via Code...");
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Re Send Verify By Type ...");
		map.put("status", false);

		Customer customer = authHelperServices.getCurrentCustomer();
		if (resendVerify != null && customer != null) {
			log.info("Verify ID: " + resendVerify.getId());

			if (customer.getPublicId().equals(resendVerify.getId())) {

				if (resendVerify.getType() == 1) {
					MailVerifiedToken mailToken = customerServices.updateCustomerVerificationMail(customer.getPublicId());
					if (mailToken != null) {
						if(mailSenderServices.sendMailWithInlineResources(customer, "OTP Verification Mail ", mailToken, null)) {
							map.put("message", "Re Send Verification Mail");
							map.put("status", true);
						}
						
					}
				} else if (resendVerify.getType() == 2) {
					PhoneVerificationToken phoneToken = customerServices.updateCustomerVerificationSMS(customer.getPublicId());
					if (phoneToken != null) {
						
						if(smsSenderServices.sendSMS(phoneToken)) {
							map.put("message", "Re Send Verification SMS");
							map.put("status", true);
						}
						
					}
				}

			}

		}

		return ResponseEntity.ok(map);
	}
}
