package com.usoit.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.BankAccount;

@Repository
public interface BankRepository extends CrudRepository<BankAccount, Integer>{

	@Query
	public Optional<BankAccount> getBankAccountByPublicId(String key);

	@Query
	public Optional<BankAccount> getBankAccountByPublicIdAndActive(String publicId, boolean active);

	@Query
	public List<BankAccount> getAllBankAccountByApproveStatusAndUpdateStatus(int approveStatus, int updateStatus);

	@Query
	public List<BankAccount> getAllBankAccountByUpdateStatusAndApproveStatusLessThanEqual(int updateStatus, int approveStatus);

	@Query
	public List<BankAccount> getBankAccountBybankName(String bankName);

	@Query
	public List<BankAccount> getBankAccountBybankNameAndActive(String bankName, boolean active);

	@Query
	public List<BankAccount> getAllBankAccountByActive(boolean active);

	@Query
	public BankAccount getBankAccountByAccountNumber(String accountNumber);

	@Query
	public List<BankAccount> getAllBankAccountByUpdateStatusAndUpdateReqStatus(int updateStatus, boolean updateReqStatus);
	
	@Query
	public List<BankAccount> getAllBankAccountByUpdateReqStatus(boolean b);

}
