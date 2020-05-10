package com.usoit.api.controller.designation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.model.Designation;
import com.usoit.api.services.DesignationServices;
import com.usoit.api.services.HelperServices;

@Controller
@RequestMapping("/designation")
@SessionAttributes(names = {"currentUser"})
public class DesignationController {

	@Autowired
	private DesignationServices designationServices;
	
	@Autowired
	private HelperServices helperServices;
	
	private List<Designation> designations;
	
	@RequestMapping("")
	public String getDesignationIndexPage(Model model, HttpSession httpSession) {
		
		return "redirect:/designation/view?page=0";
	}
	
	
	@RequestMapping(value = "/view", method = RequestMethod.GET, params = {"page"})
	public String getDesignationViewPage(Model model, HttpSession httpSession, @RequestParam(defaultValue = "0", value = "page") int page) {
		
		setDesignationList();
		
		if (designations == null) {
			designations = new ArrayList<>();
		}
		
		int fIndex = 0;
		int tIndex = 0;
		int totalPage = 0;
		int pageSize = helperServices.getPageSize();
		
		if (pageSize > designations.size()) {
			
			totalPage = (designations.size() / pageSize);
		}
		
		fIndex = (pageSize * page);
		tIndex = Math.min((fIndex + pageSize), designations.size());
		
		model.addAttribute("designations", designations.subList(fIndex, tIndex));
		
		model.addAttribute("cPage", page);
		model.addAttribute("totalPAge", totalPage);
		
		return "/pages/designation/view";
	}

	
	@RequestMapping("/add")
	public String getDesignationAddPage(Model model, HttpSession httpSession) {
		
		model.addAttribute("fDesignation", new Designation());
		
		return "/pages/designation/add";
	}
	

	
	@RequestMapping(value = "add-data", method = RequestMethod.POST)
	public String getAddActionPage(Model model, HttpSession httpSession, @ModelAttribute("designation") Designation designation) {
		
		if (designation != null) {
			
			if (designation.getName() != null) {
				
				System.out.println(" *   ******************    *");
				System.out.println("Description: TS" + designation.getDescription().toString() + " Row: " + designation.getDescription());
				System.out.println("* ******************* ******** *****************  **************");
				designation.setDescription(designation.getDescription().toString());
				
				if (!designationServices.save(designation)) {
					
					return "redirect:/failure/ns";
				}
			}
		}
		
		return "redirect:/designation/view?page=0";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = {"id"})
	public String getDesignationEditPage(Model model, HttpSession httpSession, @RequestParam("id") int id) {
		
		Designation designation = null;
		
		if (id > 0) {
			
			designation = designationServices.getDesignationById(id);
		}
		
		if (designation != null) {
			model.addAttribute("fDesignation", new Designation());
			model.addAttribute("pDesignation", designation);
		}else {
			return "redirect:/failure/nf?id="+id;
		}
		
		
		return "/pages/designation/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String getUpdateActionPage(Model model, HttpSession httpSession, @ModelAttribute("designation") Designation designation) {
		
		if (designation != null) {
			
			if (designation.getName() != null) {
				
				if (!designationServices.update(designation)) {
					
					return "redirect:/failure/ud?id="+designation.getId();
				}else {
					refreshDesignationList();
				}
			}
		}
		
		return "redirect:/designation/view?page=0";
	}
	
	
	private void refreshDesignationList() {
		
		designations = designationServices.getAllDesignations();
		
	}


	private void setDesignationList() {
		
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
