package com.usoit.api.controller.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.RechargeMapper;
import com.usoit.api.model.Recharge;
import com.usoit.api.model.request.ReqRecharge;
import com.usoit.api.services.RechargeServices;

@RestController
@RequestMapping(value = "/api/recharges")
public class RechargeController {
	
	
	@Autowired
	private RechargeMapper rechargeMapper;
	
	@Autowired
	private RechargeServices rechargeServices;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRechargeAddAction(@RequestBody ReqRecharge reqRecharge) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);
		
		if(reqRecharge != null) {
			
			Recharge recharge = rechargeMapper.mappRecharge(reqRecharge);
			
			if(recharge != null) {
				if(rechargeServices.addRecharge(recharge)) {
					map.put("status", true);
					map.put("message", "Recharg Request success");
					map.put("data", rechargeMapper.mapRestRecharge(recharge));
				}
			}
		}

		return ResponseEntity.ok(map);
	}

}
