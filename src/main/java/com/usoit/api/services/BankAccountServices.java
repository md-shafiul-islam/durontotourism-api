package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.BankAccountUpdateInf;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqBankAccountUApprove;
import com.usoit.api.model.request.ReqBankApprove;
import com.usoit.api.model.request.ReqBankReject;
import com.usoit.api.model.response.BankAccountTypeOption;

public interface BankAccountServices {

	public List<BankAccount> getBankAccounts();

	public BankAccountType getBankAccountType(String id);

	public BankAccount getBankAccountByPublicID(String id);

	public boolean addBankAcount(BankAccount bankAccount, User user);

	public boolean isKeyExist(String key);

	public boolean approveBankAccount(ReqBankApprove bankAApprove, User user);

	public boolean rejectBankAccount(ReqBankReject bankReject, User user);

	public List<BankAccount> getActiveBankAccounts();

	public List<BankAccount> getUpdatePendingBankAccounts();

	public List<BankAccount> getAllBankName();

	public List<BankAccount> getAllBankByName(String name);

	public BankAccount getBankAccountByAccountNumber(String acNo);

	public boolean updateBankAccountInf(BankAccountUpdateInf bAccountUpdateInf);


	public BankAccountUpdateInf getBankUpdateInfByBankId(String publicId);

	public boolean approveUpdateBankAccount(ReqBankAccountUApprove approveReq, User user);

	public List<BankAccount> getActiveWalletBankAccounts();

	public List<BankAccount> getActiveWalletBankAccountsByType(String accountType);

	public List<BankAccount> getBankAccountByNameAndBranch(String name, String branch);

}
