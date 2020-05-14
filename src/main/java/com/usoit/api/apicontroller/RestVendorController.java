package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
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
import com.usoit.api.data.vo.RestAddress;
import com.usoit.api.data.vo.RestVendor;
import com.usoit.api.data.vo.RestVendorUserId;
import com.usoit.api.model.request.ReqAddress;
import com.usoit.api.model.request.ReqContactPerson;
import com.usoit.api.model.request.ReqPaymentInfo;
import com.usoit.api.model.request.ReqVendor;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.VendorMapper;
import com.usoit.api.services.VendorServices;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "http://localhost:5000", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser" })
public class RestVendorController {

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private VendorServices vendorServices;

	private List<Vendor> vendors;

	private List<RestVendor> restVendors;

	@Autowired
	private VendorMapper vendorMapper;

	private List<RestVendorUserId> restVendorList;
	
	@RequestMapping("/vendor/add")
	public ResponseEntity<?> getVendorAddData(Principal principal){
		
		ReqVendor vendor = new ReqVendor();
		
		ReqPaymentInfo info = new ReqPaymentInfo();
		List<ReqPaymentInfo> infos = new ArrayList<ReqPaymentInfo>();
		infos.add(info);
		
		List<ReqContactPerson> contactPersons = new ArrayList<ReqContactPerson>();
		contactPersons.add(new ReqContactPerson());
		vendor.setContactPersons(contactPersons);
		vendor.setPaymentInfos(infos);
		
		List<ReqAddress> addresses = new ArrayList<>();
		addresses.add(new ReqAddress());
		vendor.setAddresses(addresses);
		
		
		
		return ResponseEntity.ok(vendor);
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<?>> getAllVendors(Principal principal) {

		// System.out.println("V Person PID: " + helperServices.getRandomString(60));

		setRestVendors();

		return ResponseEntity.ok(restVendors);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<?>> getAllVendorsListview(Principal principal) {

		setRestVendorsWUserId();

		return ResponseEntity.ok(restVendorList);
	}

	private void setRestVendorsWUserId() {

		setVendors();

		if (vendors != null) {

			restVendorList = vendorMapper.getRestVendorsUID(vendors);

			System.out.println("Rest Vendor user ID: " + restVendorList.size());
		}

	}

	private void setRestVendors() {

		setVendors();

		restVendors = DozerMapper.parseObjectList(vendors, RestVendor.class);
		System.out.println("Rest Vendor Size: " + restVendors.size());

	}

	private void setVendors() {

		vendors = vendorServices.getAllVendors();

	}

}
