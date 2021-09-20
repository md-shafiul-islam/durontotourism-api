package com.usoit.api.controller.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.WalletWithDrawMapper;
import com.usoit.api.model.User;
import com.usoit.api.model.WalletWithDraw;
import com.usoit.api.model.request.ReqWalletApprove;
import com.usoit.api.model.request.ReqWalletWithdraw;
import com.usoit.api.model.request.RestWalletWithDraw;
import com.usoit.api.services.HelperAuthenticationServices;
import com.usoit.api.services.WalletWithDrawServeice;

@RestController
@RequestMapping(value = "/api/wallet-withdraws")
public class WalletWithdrawrController {
	
	@Autowired
	private WalletWithDrawMapper walletWithDrawMapper;
	
	@Autowired
	private WalletWithDrawServeice walletWithDrawServeice;
	
	@Autowired
	private HelperAuthenticationServices hAuthenticationServices;
	
	@GetMapping
	public ResponseEntity<?> getWalletWithDraw() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);
		
		List<RestWalletWithDraw> walletWithDraws = walletWithDrawMapper.mapRestWalletWithDraws(walletWithDrawServeice.getAllWalletWithDraws());
		
		if(walletWithDraws != null) {
			
			map.put("message", "Not found");
			map.put("status", true);
			map.put("data", walletWithDraws);
		}
		
		return ResponseEntity.ok(map);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddWithDrawAction(@RequestBody ReqWalletWithdraw withDraw) {

		User user = hAuthenticationServices.getCurrentUser();
		
		Map<String, Object> map = new HashMap<>();

		map.put("message", "Add Bank Withdraw Failed ");
		map.put("status", false);
		map.put("data", null);
		
		if(withDraw != null) {
			
			WalletWithDraw walletWithDraw = walletWithDrawMapper.mapReqWalletWithDraw(withDraw);
			
			if(walletWithDraw != null) {
				System.out.println("Wallet With Darw After Mapper");
				if(walletWithDrawServeice.addWalletWithdarwViaClient(walletWithDraw, user)) {
					System.out.println("After Save WalletWithDraw :) ");
					RestWalletWithDraw draw = walletWithDrawMapper.mapRestWalletWithDarw(walletWithDraw);
					
					map.put("message", "Add Bank Withdraw Success");
					map.put("status", true);
					map.put("data", draw);
					
				}
			}
			
		}

		return ResponseEntity.ok(map);
	}
	
	
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddWithDrawAction(@RequestBody ReqWalletApprove withDrawApprove) {

		User user = hAuthenticationServices.getCurrentUser();
		
		Map<String, Object> map = new HashMap<>();

		map.put("message", "Add Bank Withdraw Failed ");
		map.put("status", false);
		map.put("data", null);
		
		if(withDrawApprove != null) {
			
			WalletWithDraw bankWithDraw = walletWithDrawServeice.approveWalletWithDraw(withDrawApprove, user);
			
			if(bankWithDraw != null) {
				
				RestWalletWithDraw restWalletWithDraw = walletWithDrawMapper.mapRestWalletWithDarw(bankWithDraw);
				
				if(restWalletWithDraw != null) {
					
					map.put("message", "Add Bank Withdraw Failed ");
					map.put("status", true);
					map.put("data", restWalletWithDraw);
					
				}
			}
			
		}

		return ResponseEntity.ok(map);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getBankWithDarwById(@PathVariable("id") String id)  {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Wallet Withdarw not found by given id");
		map.put("status", false);
		map.put("data", null);
		
		if(id != null) {
			
			WalletWithDraw withDraw = walletWithDrawServeice.getWalletWithByPublicId(id);
			
			if(withDraw != null) {
				
				RestWalletWithDraw restWalletWithdarw = walletWithDrawMapper.mapRestWalletWithDarwOnly(withDraw);
				
				map.put("message", "Wallet Withdarw found");
				map.put("status", true);
				map.put("data", restWalletWithdarw);
			}
		}

		return ResponseEntity.ok(map);
	}

}
