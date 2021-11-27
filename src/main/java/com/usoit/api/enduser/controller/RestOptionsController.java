package com.usoit.api.enduser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.CountryMapper;
import com.usoit.api.model.response.CountryOption;
import com.usoit.api.model.response.SelectOption;
import com.usoit.api.services.CountryServices;

@RestController
@RequestMapping(value = "/api/enu/v1/option")
public class RestOptionsController {

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private CountryMapper countryMapper;

	@GetMapping(value = "/countries-phone")
	public ResponseEntity<?> getCountryOptionsAction() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("countries", null);

		List<CountryOption> list = countryServices.getCountryOptions();

		if (list != null) {
			map.put("message", "Countries option found " + list.size());
			map.put("status", true);
			map.put("countries", list);
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/countries")
	public ResponseEntity<?> getOnlyCountryOptionsAction() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("countries", null);
		List<SelectOption> options = countryMapper.getCountryOption(countryServices.getAllCountries());
		if (options != null) {

			map.put("message", "Countries option found " + options.size());
			map.put("status", true);
			map.put("countries", options);
		}

		return ResponseEntity.ok(map);
	}

}
