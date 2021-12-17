package com.usoit.api.enduser.controller.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.request.ReqWalletStatusUpdate;
import com.usoit.api.services.WalletServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/enu/v1/wallets")
public class RestEuWalletController {

	@Autowired
	private WalletServices walletServices;
	
	@GetMapping
	public ResponseEntity<?> getWallets() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		return ResponseEntity.ok(map);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getWallet(@PathVariable("id") String publicId) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		return ResponseEntity.ok(map);
	}
	
	@PostMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWalletApproveAction(@RequestBody ReqWalletStatusUpdate walletStatus) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		return ResponseEntity.ok(map);
	}
}
