package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestVendorCategory;
import com.usoit.api.model.VendorCategory;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.UserServices;
import com.usoit.api.services.VendorCategoryServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/vendor-cats")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestVendorCategoryController {

	@Autowired
	private VendorCategoryServices vendorCategoryServices;
	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private UserServices userServices;
	
	List<RestVendorCategory> restVendorCats;
	
	List<VendorCategory> vendorCats;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getAllVendorCategories(Principal principal, HttpServletRequest request) {
		
		setRestVendorCategories();
		
		if (restVendorCats != null) {
			return ResponseEntity.ok(restVendorCats);
		}
		
		return null;
	}

	private void setRestVendorCategories() {
		
		setVendorCats();
		
		if (vendorCats != null) {
			
			restVendorCats = DozerMapper.parseObjectList(vendorCats, RestVendorCategory.class);
		}
		
	}

	private void setVendorCats() {
		
		vendorCats = vendorCategoryServices.getAllVendorCats();
		
	}
}
