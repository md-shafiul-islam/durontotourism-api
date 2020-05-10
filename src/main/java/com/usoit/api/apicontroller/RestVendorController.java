package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.vo.RestVendor;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.VendorServices;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins ="http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser"})
public class RestVendorController {
	
	@Autowired
	private HelperServices helperServices;

	@Autowired
	private VendorServices vendorServices;
	
	private List<Vendor> vendors;
	
	private List<RestVendor> restVendors;
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<?>> getAllVendors(Principal principal) {
		
		//System.out.println("V Person PID: " + helperServices.getRandomString(60));
		
		
		setRestVendors();
		
		return ResponseEntity.ok(restVendors);
	}

	private void setRestVendors() {
		
		setVendors();
		
		restVendors = DozerMapper.parseObjectList(vendors, RestVendor.class);
		System.out.println("Rest Vendor Size: " +restVendors.size());
		
	}

	private void setVendors() {
		
		if (vendors == null) {
			
			vendors = vendorServices.getAllVendors();
			
		}else {
			
			long size = vendors.size();
			long count = vendorServices.getCount();
			
			if (size != count) {
				
				vendors = vendorServices.getAllVendors();
			}
		}
		
	}
	
}
