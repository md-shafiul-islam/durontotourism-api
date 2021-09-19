package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Recharge;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqRechargeApprove;
import com.usoit.api.model.request.ReqRechargeReject;

public interface RechargeServices {

	public boolean addRecharge(Recharge recharge, User user);

	public List<Recharge> getAllRecharges();

	public boolean approveRecharge(ReqRechargeApprove rechargeApprove, User user);

	public Recharge getRechargeByPublicId(String publicId);

	public boolean rejectRecharge(ReqRechargeReject rechargeReject);

	public List<Recharge> getAllPendingRecharges();

	public List<Recharge> getRejectRecharges();

}
