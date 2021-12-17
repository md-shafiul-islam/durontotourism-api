package com.usoit.api.enduser.controller.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.mapper.BankWithDrawMapper;
import com.usoit.api.mapper.BankWithDrawServeice;
import com.usoit.api.model.BankWithDraw;
import com.usoit.api.model.request.ReqBankWithdraw;
import com.usoit.api.model.response.RestBankWithDraw;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/enu/v1/bankwithdraws")
public class RestEuBankWithDrawController {

	@Autowired
	private BankWithDrawMapper bankWithDrawMapper;
	
	@Autowired
	private BankWithDrawServeice bankWithDrawServeice;
	
	@GetMapping
	public ResponseEntity<?> getBankWithDrawals() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);
		
		List<RestBankWithDraw> bankWithDraws = bankWithDrawMapper.mapRestBankWithDarws(bankWithDrawServeice.getAllBankWithDarw());
		
		if(bankWithDraws != null) {
			map.put("message", Integer.toString(bankWithDraws.size())+" BankWithdarw (s) found");
			map.put("status", true);
			map.put("data", bankWithDraws);
		}
		
		return ResponseEntity.ok(map);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> name(@RequestBody ReqBankWithdraw bankWithDraw) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);
		
		if(bankWithDraw != null) {
			
			BankWithDraw bWithDraw = bankWithDrawMapper.mapBankWithDraw(bankWithDraw);
			log.info("After Bank Withdraw Mapping !!");
			if(bankWithDrawServeice.addBankWithdarwViaWallet(bWithDraw)) {			
								
				map.put("message", "Bank Withdarw Added ");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}
}
