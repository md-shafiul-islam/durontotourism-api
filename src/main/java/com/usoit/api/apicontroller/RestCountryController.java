package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.Country;
import com.usoit.api.data.model.User;
import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins ="*", allowedHeaders = "/**")
@SessionAttributes(names = { "currentUser" })
public class RestCountryController {

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private HelperServices helperServices;

	private User cUser;
	
	private List<RestCountry> restCountries;
	
	private List<Country> countries;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> getIndexCat(HttpSession httpSession, Principal principal) {
		System.out.println("API Request Receive");
		
		setCountriesList();
		
		
		return ResponseEntity.ok(restCountries);
	}
	
	

	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCategoryAction(HttpSession httpSession, @PathVariable("id") int id) {
		
		// Access Start
		cUser = helperServices.checkUserAccess(httpSession, "package", 2);
		Access access = helperServices.getCurrentAccess();

		if (access == null) {

			return null;

		} else {
			if (access.getNoAccess() == 1) {

				System.out.println("No Access: " + access.getNoAccess() + "Add Access: " + access.getAdd()
						+ " All Access: " + access.getAll());
				return null;

			} else {

				System.out.println("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAdd() == 1 || access.getAll() == 1) {

					System.out.println("Access Get Add Pass & All Access !!");

				} else {
					return null;
				}

			}
		}
		
		
		// Access Start
		return ResponseEntity.ok(new Category());
		
	}

	
	private void setCountriesList() {
		setAllCountries();
		
		restCountries = DozerMapper.parseObjectList(countries, RestCountry.class);
		
	}
	
	
	private void setAllCountries() {
		
		if (countries == null) {
			countries = countryServices.getAllCountries();
		}else {
			long size = countries.size();
			long count = countryServices.getCount();
			
			if (size != count) {
				countries = countryServices.getAllCountries();
			}
		}
		
	}

}
