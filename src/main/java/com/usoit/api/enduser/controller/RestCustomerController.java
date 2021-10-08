package com.usoit.api.enduser.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eno/v1/customers")
public class RestCustomerController {

		@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> name(@RequestBody String test) {

			Map<String, Object> map = new HashMap<>();

			map.put("message", "Api End point Open world ");
			map.put("status", false);
			map.put("data", null);

			return ResponseEntity.ok(map);
		}
		
		@GetMapping(value = {"/{id}"})
		public ResponseEntity<?> getCustomer(@PathVariable("id") String customerId) {

			Map<String, Object> map = new HashMap<>();

			map.put("message", "Not found by Customer ID "+customerId);
			map.put("status", false);
			map.put("data", null);

			return ResponseEntity.ok(map);
		}
}
