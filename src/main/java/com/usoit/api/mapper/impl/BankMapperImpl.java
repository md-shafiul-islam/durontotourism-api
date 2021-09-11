package com.usoit.api.mapper.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.data.converter.UserMapper;
import com.usoit.api.mapper.BankAccountTypeMapper;
import com.usoit.api.mapper.BankMapper;
import com.usoit.api.mapper.CountryMapper;
import com.usoit.api.mapper.StatusMapper;
import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.BankAccountUpdateInf;
import com.usoit.api.model.Status;
import com.usoit.api.model.request.ReqBankAccount;
import com.usoit.api.model.request.ReqBankAccountUpdateInf;
import com.usoit.api.model.response.BankOption;
import com.usoit.api.model.response.RestBankAccount;
import com.usoit.api.model.response.RestBankAccountType;
import com.usoit.api.model.response.RestBankUpdatePending;
import com.usoit.api.model.response.RestEnBankAccount;
import com.usoit.api.model.response.RestStatus;
import com.usoit.api.model.response.RestUpdateInf;
import com.usoit.api.services.BankAccountTypeServices;
import com.usoit.api.services.BankServices;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.HelperServices;

@Service
public class BankMapperImpl implements BankMapper {

	@Autowired
	private CountryMapper countryMapper;

	@Autowired
	private BankAccountTypeMapper typeMapper;

	@Autowired
	private CountryServices countryServices;

	@Autowired
	private StatusMapper statusMapper;

	@Autowired
	private BankAccountTypeServices bankAccountTypeServices;
	
	@Autowired
	private BankServices bankServices;

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private UserMapper userMapper;

	@Override
	public RestBankAccountType mappBankAccountType(BankAccountType accountType) {

		if (accountType != null) {

			RestBankAccountType bankAccountType = new RestBankAccountType();

			if (accountType.getBankAccounts() != null) {
				bankAccountType.setBankAccounts(mapRestBankAccounts(accountType.getBankAccounts()));
			}

			bankAccountType.setId(accountType.getId());
			bankAccountType.setName(accountType.getName());
			bankAccountType.setNumValue(accountType.getNumValue());
			bankAccountType.setValue(accountType.getValue());
			return bankAccountType;
		}
		return null;
	}

	@Override
	public RestBankAccount mapOnlyBankAccount(BankAccount bankAccount) {
		if (bankAccount != null) {

			RestBankAccount account = new RestBankAccount();

			account.setAccountName(bankAccount.getAccountName());
			account.setAccountNumber(bankAccount.getAccountNumber());
			account.setBankName(bankAccount.getBankName());
			account.setBranchName(bankAccount.getBranchName());
			account.setPublicId(bankAccount.getPublicId());
			account.setBankAccountType(mapOnlyAccountType(bankAccount.getBankAccountType()));

			return account;
		}
		return null;
	}

	@Override
	public RestBankAccount mappBankAccount(BankAccount account) {

		if (account != null) {

			RestBankAccount bankAccount = new RestBankAccount();

			bankAccount.setAccountName(account.getAccountName());
			bankAccount.setAccountNumber(account.getAccountNumber());
			bankAccount.setActive(account.isActive());
			bankAccount.setBankAccountType(mappBankAccountType(account.getBankAccountType()));
			bankAccount.setBankName(account.getBankName());
			bankAccount.setBranchName(account.getBranchName());
			bankAccount.setCountry(countryMapper.mappCountry(account.getCountry()));
			bankAccount.setPublicId(account.getPublicId());
			bankAccount.setStatus(statusMapper.mapRestStatus(account.getStatus()));
			bankAccount.setAmount(helperServices.getDoubleToString(
					account.getAmount().round(new MathContext(2, RoundingMode.HALF_EVEN)).doubleValue()));

			return bankAccount;
		}

		return null;
	}

	@Override
	public RestBankAccount mapRechargBankAccount(BankAccount account) {

		if (account != null) {

			RestBankAccount bankAccount = new RestBankAccount();

			bankAccount.setAccountName(account.getAccountName());
			bankAccount.setAccountNumber(account.getAccountNumber());
			bankAccount.setActive(account.isActive());
			bankAccount.setBankAccountType(mappBankAccountType(account.getBankAccountType()));
			bankAccount.setBankName(account.getBankName());
			bankAccount.setBranchName(account.getBranchName());
			bankAccount.setCountry(countryMapper.mappCountry(account.getCountry()));
			bankAccount.setPublicId(account.getPublicId());
			bankAccount.setStatus(statusMapper.mapRestStatus(account.getStatus()));

			return bankAccount;
		}

		return null;
	}

	@Override
	public BankAccount mapReqBankAccount(ReqBankAccount reqAccount) {

		if (reqAccount != null) {

			BankAccount account = new BankAccount();

			account.setAccountName(reqAccount.getAccountName());
			account.setAccountNumber(reqAccount.getAccountNumber());

			account.setAmount(new BigDecimal(0.0));

			if (reqAccount.getInitialAmount() != null) {
				if (!reqAccount.getInitialAmount().isEmpty()) {
					account.setAmount(new BigDecimal(reqAccount.getInitialAmount()));
				}
			}

			account.setBankName(reqAccount.getBankName());
			account.setBranchName(reqAccount.getBranchName());

			if (reqAccount.getBankAccountType() > 0) {
				account.setBankAccountType(
						bankAccountTypeServices.getBankAccountTypeById(reqAccount.getBankAccountType()));
			}

			if (reqAccount.getCountry() > 0) {
				account.setCountry(countryServices.getCountryById(reqAccount.getCountry()));
			}
			
			account.setBankLogoUrl(reqAccount.getLogoUrl());
			
			System.out.println("Bank Logo Image URL, "+ account.getBankLogoUrl());
			return account;
		}

		return null;
	}

	@Override
	public RestBankAccount mapRechargBankAccountViaAdd(BankAccount bankAccount) {

		if (bankAccount != null) {

			RestBankAccount account = new RestBankAccount();

			account.setAccountName(bankAccount.getAccountName());
			account.setAccountNumber(bankAccount.getAccountNumber());
			account.setAmount(helperServices.getDoubleToString(
					bankAccount.getAmount().round(new MathContext(2, RoundingMode.HALF_EVEN)).doubleValue()));
			account.setBankAccountType(mapAccountTypeViaAdd(bankAccount.getBankAccountType()));
			account.setBankName(bankAccount.getBankName());
			account.setBranchName(bankAccount.getBranchName());
			account.setCountry(countryMapper.mappCountry(bankAccount.getCountry()));
			account.setPublicId(bankAccount.getPublicId());
			account.setStatus(mapStatus(bankAccount.getStatus()));

			return account;
		}

		return null;
	}

	@Override
	public List<RestBankAccount> getAllRestBankAccounts(List<BankAccount> accounts) {

		List<RestBankAccount> restBankAccounts = new ArrayList<>();

		for (BankAccount bankAccount : accounts) {

			RestBankAccount restBankAccount = mapRestBankAccount(bankAccount);
			if (restBankAccount != null) {
				restBankAccounts.add(restBankAccount);
			}
		}

		return restBankAccounts;
	}

	@Override
	public List<BankOption> getBankNameOptions(List<BankAccount> allBankName) {

		if (allBankName != null) {

			List<BankOption> bankOprions = new ArrayList<>();

			for (BankAccount bankAccount : allBankName) {
				BankOption bankOption = new BankOption();

				bankOption.setLabel(bankAccount.getBankName());
				bankOption.setValue(bankAccount.getBankName());

				bankOprions.add(bankOption);
			}

			return bankOprions;
		}

		return null;
	}

	@Override
	public List<BankOption> getBankAccountNameOptions(List<BankAccount> bankAccounts) {

		if (bankAccounts != null) {

			List<BankOption> bankOprions = new ArrayList<>();

			for (BankAccount bankAccount : bankAccounts) {
				BankOption bankOption = new BankOption();

				bankOption.setLabel(bankAccount.getAccountName());
				bankOption.setValue(bankAccount.getAccountName());

				bankOprions.add(bankOption);
			}

			return bankOprions;
		}

		return null;
	}

	@Override
	public List<BankOption> getBankAccountNoOptions(List<BankAccount> bankAccounts) {

		if (bankAccounts != null) {

			List<BankOption> bankOprions = new ArrayList<>();

			for (BankAccount bankAccount : bankAccounts) {
				BankOption bankOption = new BankOption();

				bankOption.setLabel(bankAccount.getAccountNumber());
				bankOption.setValue(bankAccount.getAccountNumber());

				bankOprions.add(bankOption);
			}

			return bankOprions;
		}

		return null;
	}

	@Override
	public List<BankOption> getBankBranchOptions(List<BankAccount> bankAccounts) {

		if (bankAccounts != null) {

			List<BankOption> bankOprions = new ArrayList<>();

			for (BankAccount bankAccount : bankAccounts) {
				BankOption bankOption = new BankOption();

				bankOption.setLabel(bankAccount.getBranchName());
				bankOption.setValue(bankAccount.getBranchName());

				bankOprions.add(bankOption);
			}

			return bankOprions;
		}

		return null;
	}

	@Override
	public RestEnBankAccount mapBankAccountEndUser(BankAccount bankAccount) {

		if (bankAccount != null) {

			RestEnBankAccount account = new RestEnBankAccount();

			account.setAccountName(bankAccount.getAccountName());
			account.setAccountNumber(bankAccount.getAccountNumber());
			account.setBankAccountType(typeMapper.mapAccountRestTypeOnly(bankAccount.getBankAccountType()));
			account.setBankName(bankAccount.getBankName());
			account.setBranchName(bankAccount.getBranchName());
			account.setCountry(countryMapper.mappCountry(bankAccount.getCountry()));
			account.setId(bankAccount.getPublicId());

			return account;
		}

		return null;
	}
	
	@Override
	public BankAccountUpdateInf mapBankAccountUpdateInf(ReqBankAccountUpdateInf bankUpdateInf) {
		
		if(bankUpdateInf != null) {
			
			BankAccountUpdateInf buInf = new BankAccountUpdateInf();
			
			buInf.setActive(true);
			buInf.setBankId(bankUpdateInf.getBankId());
			buInf.setFieldName(bankUpdateInf.getFieldName());
			buInf.setValue(bankUpdateInf.getValue());
			
			return buInf;
		}
		
		return null;
	}
	
	@Override
	public List<RestBankUpdatePending> getAllRestUpdatePendingBankAccounts(List<BankAccount> accounts) {
		
		if(accounts != null) {
			
			List<RestBankUpdatePending> pendings = new ArrayList<>();
			
			for (BankAccount bankAccount : accounts) {
				
				RestBankUpdatePending pAccount = getRestUpdatePendingAccount(bankAccount);
				if(pAccount != null) {
					pendings.add(pAccount);
				}
			}
			
			return pendings;
		}
		
		return null;
	}
	
	@Override
	public List<RestBankAccountType> mapAllAccountTypesOnly(List<BankAccountType> types) {
		List<RestBankAccountType> accountTypes = new ArrayList<>();
		
		for (BankAccountType bankAccountType : types) {
			
			RestBankAccountType type = mapOnlyAccountType(bankAccountType);
			if(type != null) {
				accountTypes.add(type);
			}
		}
		
		return accountTypes;
	}
	
	private RestBankUpdatePending getRestUpdatePendingAccount(BankAccount bankAccount) {
		
		if(bankAccount != null) {
			
			RestBankUpdatePending pending = new RestBankUpdatePending();
			
			RestUpdateInf bankUpInf = mapRestUpdateInfo(bankAccount.getPublicId());
			if(bankUpInf != null) {
				pending.setRestUpdateInf(bankUpInf);
				
			}else {
				return null;
			}
			
			pending.setAccountName(bankAccount.getAccountName());
			pending.setAccountNumber(bankAccount.getAccountNumber());
			pending.setActive(bankAccount.isActive());
			pending.setAmount(helperServices.getDoubleToString(bankAccount.getAmount().doubleValue()));
			pending.setApproveUser(userMapper.mapEsUser(bankAccount.getApproveUser()));
			pending.setBankAccountType(typeMapper.mapAccountRestTypeOnly(bankAccount.getBankAccountType()));
			pending.setBankName(bankAccount.getBankName());
			pending.setBranchName(bankAccount.getBranchName());
			pending.setCountry(countryMapper.mappCountry(bankAccount.getCountry()));
			pending.setPublicId(bankAccount.getPublicId());
			pending.setUpdateUser(userMapper.mapEsUser(bankAccount.getUpdateUser()));
			pending.setUser(userMapper.mapEsUser(bankAccount.getCreatedUser()));
						
			
			return pending;
		
			
			
		}
		
		return null;
	}

	private RestUpdateInf mapRestUpdateInfo(String publicId) {
		
		if(publicId != null) {
			
			BankAccountUpdateInf bankUpdateInf = bankServices.getBankUpdateInfByBankId(publicId);
			
			if(bankUpdateInf != null) {
				
				RestUpdateInf inf = new RestUpdateInf();
				
				inf.setFieldName(bankUpdateInf.getFieldName());
				inf.setValue(bankUpdateInf.getValue());
				
				return inf;
			}
		}
		
		return null;
	}

	private RestBankAccountType mapOnlyAccountType(BankAccountType bankAccountType) {

		if (bankAccountType != null) {

			RestBankAccountType accountType = new RestBankAccountType();

			accountType.setId(bankAccountType.getId());
			accountType.setName(bankAccountType.getName());
			accountType.setNumValue(bankAccountType.getNumValue());
			accountType.setValue(bankAccountType.getValue());

			return accountType;
		}
		return null;
	}

	private List<RestBankAccount> mapRestBankAccounts(List<BankAccount> bankAccounts) {

		if (bankAccounts != null) {

			List<RestBankAccount> restBankAccounts = new ArrayList<>();

			for (BankAccount bankAccount : bankAccounts) {

				RestBankAccount account = mappBankAccount(bankAccount);
				if (account != null) {
					restBankAccounts.add(account);
				}

			}

			return restBankAccounts;
		}

		return null;
	}

	public RestBankAccount mapRestBankAccount(BankAccount bankAccount) {

		if (bankAccount != null) {

			RestBankAccount account = new RestBankAccount();

			account.setAccountName(bankAccount.getAccountName());
			account.setAccountNumber(bankAccount.getAccountNumber());
			account.setActive(bankAccount.isActive());
			account.setBankAccountType(typeMapper.mapAccountRestTypeOnly(bankAccount.getBankAccountType()));
			account.setBankName(bankAccount.getBankName());
			account.setBranchName(bankAccount.getBranchName());
			account.setCountry(countryMapper.mappCountry(bankAccount.getCountry()));
			account.setPublicId(bankAccount.getPublicId());
			account.setStatus(statusMapper.mapRestStatus(bankAccount.getStatus()));
			account.setApproveStatus(bankAccount.getApproveStatus());
			account.setUpdateStatus(bankAccount.getUpdateStatus());
			account.setUser(userMapper.mapEsUser(bankAccount.getCreatedUser()));
			account.setAmount(helperServices.getDoubleToString(
					bankAccount.getAmount().round(new MathContext(2, RoundingMode.HALF_EVEN)).doubleValue()));
			account.setApproveUser(userMapper.mapEsUser(bankAccount.getApproveUser()));
			account.setUpdateUser(userMapper.mapEsUser(bankAccount.getUpdateUser()));
			account.setUpdateAproveUser(userMapper.mapEsUser(bankAccount.getUpdateApproveUser()));
			return account;
		}

		return null;
	}

	private RestStatus mapStatus(Status status) {

		if (status != null) {

			RestStatus restStatus = new RestStatus();

			restStatus.setId(status.getId());
			restStatus.setName(status.getName());
			restStatus.setNumValue(status.getNumValue());
			restStatus.setValue(status.getValue());

			return restStatus;
		}

		return null;
	}

	private RestBankAccountType mapAccountTypeViaAdd(BankAccountType bankAccountType) {

		if (bankAccountType != null) {

			RestBankAccountType accountType = new RestBankAccountType();

			accountType.setName(bankAccountType.getName());
			accountType.setNumValue(bankAccountType.getNumValue());
			accountType.setValue(bankAccountType.getValue());
			accountType.setId(bankAccountType.getId());

			return accountType;
		}
		return null;
	}
}
