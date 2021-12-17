package com.usoit.api.mapper;

import java.util.List;
import java.util.Map;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.BankAccountUpdateInf;
import com.usoit.api.model.request.ReqBankAccount;
import com.usoit.api.model.request.ReqBankAccountUpdateInf;
import com.usoit.api.model.response.BankOption;
import com.usoit.api.model.response.RestBankAccount;
import com.usoit.api.model.response.RestBankAccountType;
import com.usoit.api.model.response.RestBankUpdatePending;
import com.usoit.api.model.response.RestEnBankAccount;
import com.usoit.api.model.response.SelectOption;

public interface BankAccountMapper {

	public RestBankAccountType mappBankAccountType(BankAccountType accountType);

	public RestBankAccount mappBankAccount(BankAccount account);

	public BankAccount mapReqBankAccount(ReqBankAccount reqAccount);

	public RestBankAccount mapRechargBankAccount(BankAccount account);

	public RestBankAccount mapRechargBankAccountViaAdd(BankAccount bankAccount);

	public RestBankAccount mapOnlyBankAccount(BankAccount bankAccount);

	public List<RestBankAccount> getAllRestBankAccounts(List<BankAccount> accounts);

	public List<BankOption> getBankNameOptions(List<BankAccount> allBankName);

	public List<BankOption> getBankBranchOptions(List<BankAccount> bankAccounts);

	public List<BankOption> getBankAccountNameOptions(List<BankAccount> bankAccounts);

	public List<BankOption> getBankAccountNoOptions(List<BankAccount> bankAccounts);

	public RestEnBankAccount mapBankAccountEndUser(BankAccount bankAccount);

	public BankAccountUpdateInf mapBankAccountUpdateInf(ReqBankAccountUpdateInf bankUpdateInf);

	public List<RestBankUpdatePending> getAllRestUpdatePendingBankAccounts(List<BankAccount> accounts);

	public List<RestBankAccountType> mapAllAccountTypesOnly(List<BankAccountType> types);

	public List<RestBankAccount> getRestBankAccountsOnly(List<BankAccount> accounts);
	
	public RestBankAccount mapBankAccountOnly(BankAccount bankAccount);
	
	public RestBankAccount mapBankAccountWitOutAmountOnly(BankAccount bankAccount);

	public Map<String, List<SelectOption>> getAccountOptionList(List<BankAccount> bankAccounts);


}
