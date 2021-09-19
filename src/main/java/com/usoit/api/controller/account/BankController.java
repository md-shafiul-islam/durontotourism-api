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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.BankMapper;
import com.usoit.api.model.Bank;
import com.usoit.api.model.request.ReqBank;
import com.usoit.api.model.response.BankOption;
import com.usoit.api.model.response.RestBank;
import com.usoit.api.services.BankServices;

@RestController
@RequestMapping(value = "/api/banks")
public class BankController {

	@Autowired
	private BankServices bankServices;

	@Autowired
	private BankMapper bankMapper;

	@GetMapping
	public ResponseEntity<?> getAllBank() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Bank not found");
		map.put("status", false);
		map.put("data", null);

		List<Bank> banks = bankServices.getAllBank();

		if (banks != null) {
			List<RestBank> restBanks = bankMapper.mapAllBank(banks);

			map.put("message", Integer.toString(restBanks.size()) + " Bank (s) found");
			map.put("status", true);
			map.put("data", restBanks);
		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBankAddAction(@RequestBody ReqBank reqBank) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Bank Add Faild");
		map.put("status", false);
		map.put("data", null);

		if (reqBank != null) {

			Bank bank = bankMapper.mapBank(reqBank);

			if (bank != null) {
				if (bankServices.addBak(bank)) {

					RestBank restBank = bankMapper.maoRestBank(bank);
					map.put("message", "Bank Add ");
					map.put("status", true);
					map.put("data", restBank);
				}
			}
		}

		return ResponseEntity.ok(map);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getBankByID(@PathVariable("publicId") String id) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);
		
		if(id != null) {
			
			if(id.equals("options")) {
				
				map.put("message", "Bank Options Found");
				map.put("status", true);
				map.put("data", getBankNameOption());
				
			}else {
				Bank bank = bankServices.getBanByPublicId(id);
				
				if(bank != null) {
					
					RestBank restBank = bankMapper.maoRestBank(bank);
					
					if(restBank != null) {
						map.put("message", "Bank Found");
						map.put("status", true);
						map.put("data", restBank);
					}
				}
			}
			
			
		}

		return ResponseEntity.ok(map);
	}


	public List<BankOption> getBankNameOption() {		
		
		return bankMapper.getAllBankOption(bankServices.getAllBank());
	}
}
