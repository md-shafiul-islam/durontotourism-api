package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestCategory;
import com.usoit.api.model.Category;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.HelperServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class RestTestController {

	
	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private HelperServices helperServices;

	private List<RestCategory> categories;

	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllCategories(HttpSession httpSession, Principal principal) {
		
		List<String> msgs = new ArrayList<>();
		if (principal != null) {
			
			log.info("Current User Name: " + principal.getName());
			msgs.add("Test Principal Found ");
		}
		
		setCategory();
		
		if (categories != null) {
			return ResponseEntity.ok(categories);
		}else {
			msgs.add("Categories Not Found ");
			 return ResponseEntity.ok(msgs);
		}
		
		
		
	}


	private void setCategory() {
		
		List<Category> cats = categoryServices.getAllCats();
		
		categories = DozerMapper.parseObjectList(cats, RestCategory.class);
		
	}
	
}
