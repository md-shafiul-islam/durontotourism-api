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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.BankAccountMapper;
import com.usoit.api.mapper.BankMapper;
import com.usoit.api.mapper.RechargeMapper;
import com.usoit.api.model.BankAccount;
import com.usoit.api.model.Recharge;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqRecharge;
import com.usoit.api.model.request.ReqRechargeApprove;
import com.usoit.api.model.request.ReqRechargeReject;
import com.usoit.api.model.response.RestBankAccount;
import com.usoit.api.model.response.RestRecharge;
import com.usoit.api.services.BankAccountServices;
import com.usoit.api.services.HelperAuthenticationServices;
import com.usoit.api.services.RechargeServices;
import com.usoit.api.services.UserServices;

@RestController
@RequestMapping(value = "/api/recharges")
public class RechargeController {

	@Autowired
	private RechargeMapper rechargeMapper;

	@Autowired
	private UserServices userServices;

	@Autowired
	private RechargeServices rechargeServices;

	@Autowired
	private HelperAuthenticationServices hAuthServices;

	@Autowired
	private BankAccountServices bankAccountServices;

	@Autowired
	private BankAccountMapper bankAccountMapper;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRechargeAddAction(@RequestBody ReqRecharge reqRecharge) {

		System.out.println("Post Rescharge Action Run !!");

		User user = hAuthServices.getCurrentUser();

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Recharge not added");
		map.put("status", false);
		map.put("data", null);

		if (reqRecharge != null) {

			Recharge recharge = rechargeMapper.mappRecharge(reqRecharge);

			if (recharge != null) {
				if (rechargeServices.addRecharge(recharge, user)) {
					map.put("status", true);
					map.put("message", "Recharg Request success");
					map.put("data", rechargeMapper.mapRestRechargeViaAdd(recharge));
				}
			}
		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getApprovedAction(@RequestBody ReqRechargeApprove rechargeApprove) {

		System.out.println("Recharge Approve Action Run :) ");

		Map<String, Object> map = new HashMap<>();

		User user = hAuthServices.getCurrentUser();

		map.put("message", "Recharge Approved Failed");
		map.put("status", false);
		map.put("data", null);

		if (rechargeApprove != null) {
			System.out.println("Charge Amount " + rechargeApprove.getChargeAmount());
			if (rechargeServices.approveRecharge(rechargeApprove, user)) {
				map.put("message", "Recharge Approved");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/reject")
	public ResponseEntity<?> getRejectAction() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Rejected Recharge not found");
		map.put("status", false);
		map.put("data", null);

		List<RestRecharge> rechargesReject = rechargeMapper.mapRestRechargesOnly(rechargeServices.getRejectRecharges());
		if (rechargesReject != null) {

			map.put("message", rechargesReject.size() + " (s) Reject Recharge Found");
			map.put("status", true);
			map.put("data", rechargesReject);
		}

		return ResponseEntity.ok(map);
	}

	@PutMapping(value = "/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRejectAction(@RequestBody ReqRechargeReject rechargeReject) {

		System.out.println("Rejected Update Action :) ");

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Recharge Rejected Failed");
		map.put("status", false);
		map.put("data", null);

		if (rechargeReject != null) {
			if (rechargeServices.rejectRecharge(rechargeReject)) {
				map.put("message", "Recharge Approved");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping
	public ResponseEntity<?> getRecharges() {

		User user = hAuthServices.getCurrentUser();

		System.out.println("Rechages Get Action :) ");

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Recharge(s) not found");
		map.put("status", false);
		map.put("data", null);

		List<Recharge> recharges = rechargeServices.getAllRecharges();

		if (recharges != null) {

			System.out.println("Recharge Size " + recharges.size());

			map.put("message", "Total " + Integer.toString(recharges.size()) + " Recharge Items found(s)");
			map.put("status", true);
			map.put("data", rechargeMapper.mapRestRecharges(recharges));

		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/pending")
	public ResponseEntity<?> getPendingRecharges() {

		User user = hAuthServices.getCurrentUser();

		System.out.println("Rechages Get Action :) ");

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Pending Recharge not found");
		map.put("status", false);
		map.put("data", null);

		List<Recharge> recharges = rechargeServices.getAllPendingRecharges();

		if (recharges != null) {

			System.out.println("Recharge Size " + recharges.size());

			map.put("message", "Total " + Integer.toString(recharges.size()) + " Recharge Items found(s)");
			map.put("status", true);
			map.put("data", rechargeMapper.mapRestRechargesOnly(recharges));

		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getRecharge(@PathVariable("id") String publicId) {

		User user = hAuthServices.getCurrentUser();

		System.out.println("Current User " + user.getUsername());

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Recharge not found by given ID");
		map.put("status", false);
		map.put("data", null);

		Recharge recharge = rechargeServices.getRechargeByPublicId(publicId);

		if (recharge != null) {

			map.put("message", "Total " + " Recharge Item found");
			map.put("status", true);
			map.put("data", rechargeMapper.mapRestRechargeOnly(recharge));

		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/bankaccounts") //, params = { "type"}
	public ResponseEntity<?> getRechargeBankAccounts(@RequestParam(name = "type", defaultValue = "genarel_banking") String type) {

		User user = hAuthServices.getCurrentUser();
		
		System.out.println("Get Bank Accounts, Using Type "+ type);
		
		Map<String, Object> map = new HashMap<>();

		map.put("message", "Bank account not found by type "+type);
		map.put("status", false);
		map.put("data", null);
		List<BankAccount> accounts = null;
		
		if(type.equals("all")) {
			accounts = bankAccountServices.getActiveWalletBankAccounts();
		}else {
			accounts = bankAccountServices.getActiveWalletBankAccountsByType(type);
		}
		
		System.out.println("After Get Banks ");
		if (accounts != null) {

			List<RestBankAccount> bankAccounts = bankAccountMapper.getRestBankAccountsOnly(accounts);

			map.put("message", Integer.toString(bankAccounts.size()) + " Bank(s) Account found");
			map.put("status", true);
			map.put("data", bankAccounts);
		}

		return ResponseEntity.ok(map);

	}

}
