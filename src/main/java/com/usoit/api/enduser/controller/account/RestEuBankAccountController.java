package com.usoit.api.enduser.controller.account;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usoit.api.mapper.BankAccountMapper;
import com.usoit.api.mapper.RechargeMapper;
import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.BankAccountUpdateInf;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqBankAccount;
import com.usoit.api.model.request.ReqBankAccountUApprove;
import com.usoit.api.model.request.ReqBankAccountUpdateInf;
import com.usoit.api.model.request.ReqBankApprove;
import com.usoit.api.model.request.ReqBankReject;
import com.usoit.api.model.request.ReqQueryBankAccount;
import com.usoit.api.model.response.BankAccountTypeOption;
import com.usoit.api.model.response.BankOption;
import com.usoit.api.model.response.RestBankAccount;
import com.usoit.api.model.response.RestBankAccountType;
import com.usoit.api.model.response.RestBankUpdatePending;
import com.usoit.api.model.response.RestEnBankAccount;
import com.usoit.api.model.response.SelectOption;
import com.usoit.api.services.BankAccountServices;
import com.usoit.api.services.BankAccountTypeServices;
import com.usoit.api.services.HelperAuthenticationServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/enu/v1/bankaccounts")
public class RestEuBankAccountController {

	@Autowired
	private BankAccountServices bankAccountServices;

	@Autowired
	private BankAccountTypeServices bankAccountTypeServices;

	@Autowired
	private BankAccountMapper bankAccountMapper;

	@Autowired
	private RechargeMapper rechargeMapper;

	@Autowired
	private HelperAuthenticationServices hAuthServices;

	@GetMapping
	private ResponseEntity<?> getBankApprovedAccounts() {

		Map<String, Object> map = new HashMap<>();
		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		List<BankAccount> accounts = bankAccountServices.getActiveBankAccounts();
		if (accounts != null) {
			List<RestBankAccount> restBankAccounts = bankAccountMapper.getAllRestBankAccounts(accounts);
			map.put("message", Integer.toString(accounts.size()) + "(s) Account found");
			map.put("status", true);
			map.put("data", restBankAccounts);
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/bank/name")
	public ResponseEntity<?> getBankAccountUsingBankNameOptions() {

		log.info("L 86. Bank Account By Bank Name ... ");

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		List<BankOption> bankOptions = bankAccountMapper.getBankNameOptions(bankAccountServices.getAllBankName());

		if (bankOptions != null) {

			map.put("message", Integer.toString(bankOptions.size()) + " (s) Banks Name found");
			map.put("status", true);
			map.put("data", bankOptions);

		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/", params = "{acno}")
	public ResponseEntity<?> getBanckAccountByACNo(@RequestParam("acno") String acNo) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		if (acNo != null) {

			RestEnBankAccount bankAccount = bankAccountMapper
					.mapBankAccountEndUser(bankAccountServices.getBankAccountByAccountNumber(acNo));

			if (bankAccount != null) {
				map.put("message", "Bank Account found by AC/No. ");
				map.put("status", true);
				map.put("data", bankAccount);
			}
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/bankname/{name}")
	public ResponseEntity<?> getBankAccountsByName(@PathVariable("name") String name) {
		log.info("Bank Account searching .... Name " + name);

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Bank Account Not found using bank name " + name);
		map.put("status", false);
		map.put("data", null);

		log.debug("Bannk Name Param " + name);

		if (name != null) {
			List<BankAccount> bankAccounts = bankAccountServices.getAllBankByName(name);

			log.info("Bank Account Found By Bank Name " + bankAccounts.size());

			if (bankAccounts != null) {
				Map<String, List<SelectOption>> optionMap = bankAccountMapper.getAccountOptionList(bankAccounts);

				map.put("message", Integer.toString(bankAccounts.size()) + " (s) Banks Name found");
				map.put("status", true);
				map.put("banksAccountBranch", optionMap.get("branch"));
				map.put("banksAccountName", optionMap.get("accountName"));
				map.put("banksAccountNo", optionMap.get("accountNo"));
			}

		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/query")
	public ResponseEntity<?> getBankAccountsByBankNameAndBranchName(@RequestBody ReqQueryBankAccount query) {
		log.info("Post Query Bank Account searching ...");

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Bank Account Not found provided query ");
		map.put("status", false);
		map.put("data", null);
		if (query != null) {
			log.info("Bank Account searching .... Query Bank Name " + query.getBankname() + " Branch "
					+ query.getBranchName() + " Account Name " + query.getAccountName());

			List<BankAccount> bankAccounts = bankAccountServices.getBankAccountByNameAndBranch(query.getBankname(), query.getBranchName());

			log.info("Bank Account Found By Bank Name " + bankAccounts.size());

			if (bankAccounts != null) {
				Map<String, List<SelectOption>> optionMap = bankAccountMapper.getAccountOptionList(bankAccounts);

				map.put("message", Integer.toString(bankAccounts.size()) + " (s) Banks Name found");
				map.put("status", true);
				map.put("banksAccountBranch", optionMap.get("branch"));
				map.put("banksAccountName", optionMap.get("accountName"));
				map.put("banksAccountNo", optionMap.get("accountNo"));
			}

		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddBankAccount(@RequestBody ReqBankAccount reqBankAccount) {

		User user = hAuthServices.getCurrentUser();

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		if (reqBankAccount != null && user != null) {
			BankAccount bankAccount = bankAccountMapper.mapReqBankAccount(reqBankAccount);

			if (bankAccount != null) {
				if (bankAccountServices.addBankAcount(bankAccount, user)) {
					map.put("message", "Account Added");
					map.put("status", true);
					map.put("data", bankAccountMapper.mapRechargBankAccountViaAdd(bankAccount));
				}
			}
		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBankAccountUpdateAction(@RequestBody ReqBankAccountUpdateInf bankUpdateInf) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		if (bankUpdateInf != null) {

			BankAccountUpdateInf bAccountUpdateInf = bankAccountMapper.mapBankAccountUpdateInf(bankUpdateInf);

			if (bankAccountServices.updateBankAccountInf(bAccountUpdateInf)) {

				map.put("message", "Account Update prossecing...");
				map.put("status", true);
				map.put("data", null);
			}

		}
		return ResponseEntity.ok(map);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBankApproveAction(@RequestBody ReqBankApprove bankAApprove) {

		Map<String, Object> map = new HashMap<>();

		User user = hAuthServices.getCurrentUser();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		if (bankAApprove != null) {

			if (bankAccountServices.approveBankAccount(bankAApprove, user)) {
				map.put("message", "Account Approved");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBankRejectAction(@RequestBody ReqBankReject bankReject) {

		Map<String, Object> map = new HashMap<>();

		User user = hAuthServices.getCurrentUser();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		if (bankReject != null) {

			if (bankAccountServices.rejectBankAccount(bankReject, user)) {
				map.put("message", "Account Approved");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}

//	@GetMapping(value = "/account/{id}")
//	public ResponseEntity<?> getBankAccount(@PathVariable("id") String id) {
//
//		Map<String, Object> map = new HashMap<>();
//
//		map.put("message", "Account not found");
//		map.put("status", false);
//		map.put("data", null);
//
//		if (id != null) {
//
//			if (!id.isEmpty()) {
//				BankAccount account = bankAccountServices.getBankAccountByPublicID(id);
//
//				RestBankAccount restBankAccount = bankAccountMapper.mappBankAccount(account);
//
//				if (restBankAccount != null) {
//
//					map.put("message", "Account found");
//					map.put("status", true);
//					map.put("data", restBankAccount);
//				}
//
//			}
//		}
//
//		return ResponseEntity.ok(map);
//	}

	@GetMapping(value = "/types")
	public ResponseEntity<?> getAllBankAccountTypes() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		List<BankAccountType> types = bankAccountTypeServices.getAllAccountTypes();

		if (types != null) {
			List<RestBankAccountType> accountTypes = bankAccountMapper.mapAllAccountTypesOnly(types);

			if (accountTypes != null) {
				map.put("message", Integer.toString(accountTypes.size()) + "(s) Bank Account Type found");
				map.put("status", true);
				map.put("data", accountTypes);
			}
		}

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/types/{id}")
	public ResponseEntity<?> getAccountTypes(@PathVariable("id") String id) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		if (id != null) {
			if (!id.isEmpty()) {

				BankAccountType accountType = bankAccountServices.getBankAccountType(id);

				if (accountType != null) {

					RestBankAccountType restBankType = bankAccountMapper.mappBankAccountType(accountType);

					if (restBankType != null) {
						map.put("message", "Account Type found");
						map.put("status", true);
						map.put("data", restBankType);
					}
				}
			}
		}

		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = "/update/approve", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBankAccountUpdateApproveAction(@RequestBody ReqBankAccountUApprove approveReq) {

		User user = hAuthServices.getCurrentUser();

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);
		System.out.println("Banks Update Approves, " + approveReq.getAccountName());
		if (approveReq != null) {

			if (bankAccountServices.approveUpdateBankAccount(approveReq, user)) {

				map.put("message", "Bank Account Approved");
				map.put("status", true);
				map.put("data", null);
			}
		}

		return ResponseEntity.ok(map);
	}

	@PostMapping(value = "/types", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAddBankAccountTypeAction(@RequestBody ReqBankAccount ReqBankAccount) {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Account not found");
		map.put("status", false);
		map.put("data", null);

		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/types-options")
	public ResponseEntity<?> getBankTypeOptions() {

		Map<String, Object> map = new HashMap<>();

		map.put("message", "Not found");
		map.put("status", false);
		map.put("data", null);

		List<BankAccountTypeOption> typeOptions = bankAccountTypeServices.getAccountTypeOptions();
		if (typeOptions.size() > 0) {
			map.put("message", Integer.toString(typeOptions.size()) + "(s) Account Type found :) ");
			map.put("status", true);
			map.put("data", typeOptions);
		}
		return ResponseEntity.ok(map);
	}

}
