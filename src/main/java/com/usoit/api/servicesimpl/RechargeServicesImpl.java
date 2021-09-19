package com.usoit.api.servicesimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.Customer;
import com.usoit.api.model.PaymentStatus;
import com.usoit.api.model.Recharge;
import com.usoit.api.model.User;
import com.usoit.api.model.Wallet;
import com.usoit.api.model.request.ReqRechargeApprove;
import com.usoit.api.model.request.ReqRechargeReject;
import com.usoit.api.repository.RechargeRpository;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.PaymentStatusServices;
import com.usoit.api.services.RechargeServices;
import com.usoit.api.services.UserServices;
import com.usoit.api.services.WalletServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RechargeServicesImpl implements RechargeServices {

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private RechargeRpository rechargeRpository;

	private SessionFactory sessionFactory;

	@Autowired
	private PaymentStatusServices paymentStatusServices;

	@Autowired
	private WalletServices walletServices;

	@Autowired
	private UserServices userServices;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public List<Recharge> getAllRecharges() {
		
		return (List<Recharge>) rechargeRpository.findAll();
	}

	@Override
	public List<Recharge> getAllPendingRecharges() {
		
		return rechargeRpository.getAllRechargeByApproveStatusAndWalletStatus(0, false);
	}
	
	@Override
	public boolean addRecharge(Recharge recharge, User user) {

		if (recharge != null) {

			Date date = new Date();
			// DT_ADW_CA, CQ, MB, IB
			
			//DT_BTOB_ADW_BD_20210912_IB_885566771
			//DT_BTOC_ADW_BD_20210912_IB_885566771
			
			recharge.setGenId(getUniqueGenID(recharge.getTransType()));
			recharge.setPublicId(getPublicId());
			recharge.setPaymentStatus(paymentStatusServices.getPaymentStatusById(1));
			Customer customer = userServices.getCustomerById(user.getId());
			recharge.setCustomer(customer);
			recharge.setDate(date);

			rechargeRpository.save(recharge);

			return true;
		}

		return false;
	}

	@Override
	public Recharge getRechargeByPublicId(String publicId) {

		if (publicId != null) {

			Optional<Recharge> optional = rechargeRpository.getRechargeByPublicId(publicId);

			if (optional.isPresent()) {
				return optional.get();
			}
		}

		return null;
	}

	@Override
	public boolean approveRecharge(ReqRechargeApprove rechargeApprove, User user) {

		log.debug("Recharge Approve Services Action :) ");

		if (rechargeApprove != null) {

			Recharge recharge = getRechargeByPublicId(rechargeApprove.getPublicId());

			if (recharge != null) {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
					
					if (recharge.getCustomer() != null) {
						log.debug("Recharge Customer Name: " + recharge.getCustomer().getName());

						PaymentStatus paymentStatus = session.get(PaymentStatus.class, 2);
						Recharge dbRecharg = session.get(Recharge.class, recharge.getId());

						log.debug("Recharge Amount DB " + dbRecharg.getAmount());

						if (dbRecharg.getBankAccount() != null && dbRecharg.getCustomer() != null) {
														
							double chargeAmount = rechargeApprove.getChargeAmount();				
							
							dbRecharg.setChargeAmount(chargeAmount);
							
							double netAmount = dbRecharg.getAmount() - chargeAmount;
							
							if (dbRecharg.getCustomer().getWallet() != null) {

								Wallet dbWallet = session.get(Wallet.class,
										dbRecharg.getCustomer().getWallet().getId());

								double nWaAmount = dbWallet.getTotalAmount() + netAmount;
								dbWallet.setTotalAmount(nWaAmount);

								dbRecharg.setApproveStatus(rechargeApprove.getStatus());
								dbRecharg.setPaymentStatus(paymentStatus);
								dbRecharg.setWalletStatus(true);
								session.update(dbWallet);

							} else {
								Wallet wallet = new Wallet();
								wallet.setCustomer(recharge.getCustomer());
								wallet.setDate(new Date());
								wallet.setTotalAmount(netAmount);
								wallet.setApproveStatus(1);
								dbRecharg.setApproveStatus(rechargeApprove.getStatus());
								dbRecharg.setPaymentStatus(paymentStatus);
								dbRecharg.setWalletStatus(true);
								session.persist(wallet);
								dbRecharg.getCustomer().setWallet(wallet);
							}
							
							BankAccount dbBankAccount = session.get(BankAccount.class, dbRecharg.getBankAccount().getId());
							log.debug("Befor Updated Bank ID: "+dbBankAccount.getId()+ " Amount " + dbBankAccount.getAmount() + " Recharge Amount "+ netAmount);
							double bankAmount = dbBankAccount.getAmount().doubleValue() + netAmount;
							dbBankAccount.setAmount(new BigDecimal(bankAmount));
							log.debug("Updated Bank Amount " + dbBankAccount.getAmount());
							session.update(dbBankAccount);
							
							//TODO:Transection Recharge To bank account, Recharge to Wallet, Wallet to bank Or Any charge provider 
						}

						dbRecharg.setActionDate(new Date());
						session.update(dbRecharg);
						transaction.commit();
						return true;

					} else {
						log.debug("Recharge Customer Not Found !!");
					}

				} catch (Exception e) {

					if (transaction != null) {
						log.debug("Recharge Approve Roll Back !!");
						transaction.rollback();
					}
					e.printStackTrace();
				} finally {
					session.clear();
					session.close();
				}
			} else {
				log.debug("Recharge Not Found !!");
			}

		}

		return false;
	}

	@Override
	public boolean rejectRecharge(ReqRechargeReject rechargeReject) {
		
		if(rechargeReject != null) {
			Recharge recharge = getRechargeByPublicIdAndApproveStatus(rechargeReject.getPublicId(), 0);
			
			if(recharge != null) {
				log.debug("Reject Recharge Amount "+ recharge.getAmount());
				
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				
				try {
					transaction = session.beginTransaction();
					PaymentStatus paymentStatus = session.get(PaymentStatus.class, 3);
					Recharge dbRecharge = session.get(Recharge.class, recharge.getId());
					dbRecharge.setRejected(true);
					dbRecharge.setRejectedNote(rechargeReject.getRejectNote());
					dbRecharge.setActionDate(new Date()); 
					dbRecharge.setApproveStatus(2);
					dbRecharge.setPaymentStatus(paymentStatus);
					session.update(dbRecharge);
					
					transaction.commit();
					session.clear();
					session.close();
					return true;
				} catch (Exception e) {
					
					if(transaction != null) {
						log.debug("Reject Recharge Transection Roll Back ");
						transaction.rollback();
					}
					session.clear();
					session.close();
					e.printStackTrace();
				
				}
			}else {
				log.debug("Reject Recharge Not Found ");
			}
			
			
			
			return true;
		}
		
		
		return false;
	}

	
	@Override
	public List<Recharge> getRejectRecharges() {
		
		return rechargeRpository.getRechargeByRejected(true);
	}
	
	private Recharge getRechargeByPublicIdAndApproveStatus(String publicId, int status) {
		Optional<Recharge> optional = rechargeRpository.getRechargeByPublicIdAndApproveStatus(publicId, status);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	private String getPublicId() {
		boolean status = true;
		String publicId = null;
		while (status) {
			publicId = helperServices.getRnadoUUID();
			Optional<Recharge> optional = rechargeRpository.getRechargeByPublicId(publicId);

			if (!optional.isPresent()) {
				status = false;

			}

		}

		return publicId;
	}

	private String getUniqueGenID(String type) {
		boolean status = true;
		String genId = null;

		while (status) {
			genId = getGenaretdStringID(type);

			Optional<Recharge> optional = rechargeRpository.getRechargeBygenId(genId);

			if (!optional.isPresent()) {
				status = false;
			}
		}

		return genId;
	}

	private String getGenaretdStringID(String transType) {
		if (transType != null) {

			String genID = "DTADW";

			switch (transType) {
			case "cash":
				genID += "CA";
				break;
			case "cheque":
				genID += "CQ";
				break;

			case "online_bank":
				genID += "IB";
				break;
			case "mobile_bank":
				genID += "MB";
				break;
			default:
				break;
			}
			genID += helperServices.getRandomString(20).toUpperCase();

			return genID;

		}

		return null;
	}
}
