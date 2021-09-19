package com.usoit.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usoit.api.model.Recharge;

@Repository
public interface RechargeRpository extends CrudRepository<Recharge, Integer>{

	
	@Query
	public Optional<Recharge> getRechargeBygenId(String genId);

	@Query
	public Optional<Recharge> getRechargeByPublicId(String publicId);

	@Query
	public Optional<Recharge> getRechargeByPublicIdAndApproveStatus(String publicId, int approveStatus);

	@Query
	public List<Recharge> getAllRechargeByApproveStatusAndWalletStatus(int approveStatus, boolean walletStatus);

	@Query
	public List<Recharge> getRechargeByRejected(boolean rejected);

}
