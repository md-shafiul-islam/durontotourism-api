package com.usoit.api.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.services.UserServices;

@Controller
@RequestMapping("/failure")
@SessionAttributes(names = {"currentUser"})
public class FailureController {

	@Autowired
	private UserServices userServices;
	
	@RequestMapping(value = "/ns")
	public String getSaveFailed(Model model, HttpSession httpSession) {
		
		return "/pages/failure/not-save";
	}
	
	@RequestMapping(value = "/nf", method = RequestMethod.GET)
	public String getSaveFound(Model model, HttpSession httpSession, @RequestParam(defaultValue = "0", value = "id")int id) {
		
		return "/pages/failure/not-found";
	}
	
	@RequestMapping(value = "/apf")
	public String getApproveFailed(Model Model, HttpSession httpSession) {
		
		return "/pages/failure/approve-failed";
	}
	
	@RequestMapping(value = "/rjf")
	public String getRejectFailed(Model Model, HttpSession httpSession) {
		
		return "/pages/failure/reject-failed";
	}
	
	@RequestMapping(value = "/nud", method = RequestMethod.GET)
	public String getUpdateFailed(Model Model, HttpSession httpSession, @RequestParam(defaultValue = "0", value = "id")int id) {
		
		return "/pages/failure/update-failed";
	}
}
