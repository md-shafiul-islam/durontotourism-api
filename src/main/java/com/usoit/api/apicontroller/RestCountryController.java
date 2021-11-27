package com.usoit.api.apicontroller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.model.Country;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqCountry;
import com.usoit.api.model.response.CountryOption;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "*", allowedHeaders = "/**")
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
		log.info("API Request Receive");

		setCountriesList();

		return ResponseEntity.ok(restCountries);
	}

	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCountryAction(Principal principal, HttpSession httpSession,
			@PathVariable("id") int id) {

		Map<String, Object> returnData = new HashMap<>();
		RestCountry restCountry = null;
		returnData.put("country", restCountry);
		// Access Start
		Map<String, RestAccessUser> accessAll = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser access = accessAll != null ? accessAll.get("countries") : null;

		if (access == null) {
			returnData.put("msg", "Please Login and try again !!");
			returnData.put("status", false);
			return ResponseEntity.accepted().body(returnData);

		} else {
			if (access.getNoAccess() == 1) {

				returnData.put("msg", "You can't Access this option!!");
				returnData.put("status", false);
				return ResponseEntity.accepted().body(returnData);

			} else {

				log.info("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1) {

					log.info("Access Get Add Pass & All Access !!");

				} else {

					returnData.put("msg", "You can't Access this option!!");
					returnData.put("status", false);
					return ResponseEntity.accepted().body(returnData);
				}

			}
		}

		if (id > 0) {

			Country country = countryServices.getCountryById(id);

			if (country != null) {

				restCountry = DozerMapper.parseObject(country, RestCountry.class);

				if (restCountry != null) {

					returnData.put("msg", "Country Found");
					returnData.put("status", true);
					returnData.put("country", restCountry);
					return ResponseEntity.ok(returnData);
				} else {

					returnData.put("msg", "Country Mapping Error. Please, Contact System Administrator.");
					returnData.put("status", false);
					return ResponseEntity.accepted().body(returnData);
				}
			}
		}

		returnData.put("msg", "Country Not Found");
		returnData.put("status", false);
		return ResponseEntity.accepted().body(returnData);

	}

	@RequestMapping(value = "/country", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCountryAction(Principal principal, HttpSession httpSession,
			@RequestBody ReqCountry reqCountry) {

		Map<String, Object> returnData = new HashMap<>();

		// Access Start
		Map<String, RestAccessUser> accessAll = helperServices.getAccessMapByPrincipal(principal);
		RestAccessUser access = accessAll != null ? accessAll.get("countries") : null;

		if (access == null) {
			returnData.put("msg", "Please Login and try again !!");
			returnData.put("status", false);
			return ResponseEntity.accepted().body(returnData);

		} else {
			if (access.getNoAccess() == 1) {

				returnData.put("msg", "You can't Access this option!!");
				returnData.put("status", false);
				return ResponseEntity.accepted().body(returnData);

			} else {

				log.info("Out side if: Add Access: " + access.getAdd() + " All Access: " + access.getAll());

				if (access.getAll() == 1) {

					log.info("Access Get Add Pass & All Access !!");

				} else {

					returnData.put("msg", "You can't Access this option!!");
					returnData.put("status", false);
					return ResponseEntity.accepted().body(returnData);
				}

			}
		}

		if (reqCountry != null) {

			Country country = DozerMapper.parseObject(reqCountry, Country.class);

			if (country != null) {
				
				if (countryServices.update(country)) {
					returnData.put("msg", "Country Updated Successfully");
					returnData.put("status", true);
					refReshCountry();
					return ResponseEntity.ok(returnData);
				}

				returnData.put("msg", "Country Mapping Error, Kindly Contact System Administrator");
				returnData.put("status", false);
				
				return ResponseEntity.ok(returnData);
			} else {

				returnData.put("msg", "Country Mapping Error. Please, Contact System Administrator.");
				returnData.put("status", false);
				return ResponseEntity.accepted().body(returnData);
			}

		}

		returnData.put("msg", "Country Not Found");
		returnData.put("status", false);
		return ResponseEntity.accepted().body(returnData);

	}
	
	@GetMapping(value = "/options")
	public ResponseEntity<?> getCountriesOptions() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);
		
		List<CountryOption> countryOptions = countryServices.getCountryOptions();
		
		if(countryOptions.size() > 0) {
			map.put("message", Integer.toString(countryOptions.size())+"(s) Country found");
			map.put("status", true);
			map.put("data", countryOptions);
		}

		return ResponseEntity.ok(map);
	}

	private void refReshCountry() {
		countries = countryServices.getAllCountries();
		
	}

	private void setCountriesList() {
		setAllCountries();

		restCountries = DozerMapper.parseObjectList(countries, RestCountry.class);

	}

	private void setAllCountries() {

		if (countries == null) {
			countries = countryServices.getAllCountries();
		} else {
			long size = countries.size();
			long count = countryServices.getCount();

			if (size != count) {
				countries = countryServices.getAllCountries();
			}
		}

	}

}