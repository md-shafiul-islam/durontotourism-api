package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.User;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.vo.RestAddress;
import com.usoit.api.data.vo.RestVendor;
import com.usoit.api.data.vo.RestVendorUserId;
import com.usoit.api.model.request.ReqAddress;
import com.usoit.api.model.request.ReqContactPerson;
import com.usoit.api.model.request.ReqPaymentInfo;
import com.usoit.api.model.request.ReqVendor;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.UserServices;
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

	@Autowired
	private UserServices userServices;

	private User cUser;

	private List<RestVendorUserId> restPandingVendors;
	
	private List<Vendor> pandingVendors;

	@RequestMapping("/vendor/{id}")
	public ResponseEntity<?> getVendorAddData(Principal principal, HttpServletRequest request, @PathVariable("id") String pubId) {
		
		if (helperServices.isValidAndLenghtCheck(pubId, 75)) {
			
			Vendor vendor = vendorServices.getVendorByPublicId(pubId);
			
			if (vendor != null) {
				
				ReqVendor reqVendor = vendorMapper.getReqVendor(vendor);
				
				if (reqVendor != null) {
					
					return ResponseEntity.ok(reqVendor);
				}
			}
		}
		

		return ResponseEntity.notFound().build();
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
	
	@RequestMapping(value = "/panding", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getPandingVaendorList(Principal principal, HttpServletRequest request){
		
		setRestPandingVendor();
		
		if (restPandingVendors != null) {
			
			return ResponseEntity.ok(restPandingVendors);
		}
		
		return null;
	}
	


	@RequestMapping(value = "/vendor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVendorAddAction(Principal principal, @RequestBody ReqVendor reqVendor,
			HttpServletRequest request) {

		cUser = userServices.getUerById(1);

		System.out.println("Run Vendor Add!!");

		if (reqVendor != null) {

			System.out.println("Vendor Not Null !!");

			Vendor vendor = vendorMapper.getVendor(reqVendor);

			if (vendor != null) {

				System.out.println("Vendor Category Name: " + vendor.getVendorCategory().getName());
				vendor.setUser(cUser);
				vendor.setPublicId(helperServices.getRandomString(75));
				if (vendorServices.save(vendor)) {
					ResponseEntity.ok(true);
				}
			}
		}

		return null;
	}
	
	@RequestMapping(value = "/vendor", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getVendorUpdateRequestAction(Principal principal, @RequestBody ReqVendor reqVendor,
			HttpServletRequest request) {

		cUser = userServices.getUerById(1);

		System.out.println("Run Vendor Add!!");

		if (reqVendor != null) {

			System.out.println("Vendor Update Req Not Null !!");
			
			if (reqVendor.getPublicId() != null) {
				
				if (reqVendor.getPublicId().length() == 75) {
					
					// Set Tem  Vendor Save Action
					
					
					
					
				}
			}

		}

		return null;
	}

	@RequestMapping(value = "/vendor/approval/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getVendorApproveAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {

		if (helperServices.isValidAndLenghtCheck(pubId, 75)) {

			if (vendorServices.approveVendorByPublicID(pubId)) {

				return ResponseEntity.ok("Approve");
			}
		}

		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/vendor/reject/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getVendorRejectAction(Principal principal, HttpServletRequest request,
			@PathVariable("id") String pubId) {

		if (helperServices.isValidAndLenghtCheck(pubId, 75)) {

			if (vendorServices.rejectVendorByPublicID(pubId)) {

				return ResponseEntity.ok("Rejected");
			}
		}

		return ResponseEntity.notFound().build();
	}
	
	private void setRestPandingVendor() {
		
		setPandingVendors();
		
		if (pandingVendors != null) {
			
			restPandingVendors = vendorMapper.getRestVendorsUID(pandingVendors);
		}
		
	}

	private void setPandingVendors() {
		
		pandingVendors = vendorServices.getAllPanding();
		
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
