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
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Service;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankWithDraw;
import com.usoit.api.model.Customer;
import com.usoit.api.model.PaymentStatus;
import com.usoit.api.model.ShippingAddress;
import com.usoit.api.model.User;
import com.usoit.api.model.Wallet;
import com.usoit.api.model.WalletWithDraw;
import com.usoit.api.model.WithDrawBankDetails;
import com.usoit.api.model.WithDrawMobilBanking;
import com.usoit.api.model.request.ReqWalletApprove;
import com.usoit.api.repository.WalletWithDrawRepository;
import com.usoit.api.services.BankAccountServices;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.KeyOrIdGenerator;
import com.usoit.api.services.WalletWithDrawServeice;

import ch.qos.logback.classic.turbo.TurboFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WalletWithDrawServeiceImpl implements WalletWithDrawServeice {
	
	@Autowired
	private KeyOrIdGenerator keyGenerator;
	
	@Autowired
	private WalletWithDrawRepository walletWithDrawRepository;
	
	@Autowired
	private HelperServices helperServices;
	
	private SessionFactory sessionFactory;

	@Autowired
	private BankAccountServices bankAccountServices;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public List<WalletWithDraw> getAllWalletWithDraws() {

		return (List<WalletWithDraw>) walletWithDrawRepository.findAll();
	}

	@Override
	public boolean addWalletWithdarwViaClient(WalletWithDraw walletWithDraw, User user) {

		if (walletWithDraw != null) {
			
			log.info("Add Wallet Withdraw Via Client :) ");
			
			Session session = sessionFactory.openSession();
			Transaction transaction = null;
						
			if(walletWithDraw.getWithdrawType() == null) {
				return false;
			}
			
			walletWithDraw.setPublicId(getUniquePublicId());
			walletWithDraw.setGenId(getWalletWithDrawGenId(walletWithDraw.getWithdrawType().getValue()));
			
			try {
				Date date = new Date();
				transaction = session.beginTransaction();

				Customer customer = session.get(Customer.class, user.getId());
				PaymentStatus paymentStatus = session.get(PaymentStatus.class, 1);

				walletWithDraw.setCustomer(customer);								
				walletWithDraw.setDate(date);
				walletWithDraw.setDateGroup(date);
				walletWithDraw.setPaymentStatus(paymentStatus);
				
				if (walletWithDraw.getWithDrawBankDetails() != null) {
					WithDrawBankDetails bankDetails = walletWithDraw.getWithDrawBankDetails();
					bankDetails.setCustomer(customer);
					session.persist(bankDetails);
					walletWithDraw.setWithDrawBankDetails(bankDetails);
				}
				
				if(walletWithDraw.getShippingAddress() != null) {
					ShippingAddress address = walletWithDraw.getShippingAddress();
					session.persist(address);
					walletWithDraw.setShippingAddress(address);
				}
				
				if(walletWithDraw.getWithDrawMobilBanking() != null) {
					WithDrawMobilBanking mobileBank = walletWithDraw.getWithDrawMobilBanking();
					session.persist(mobileBank);
					walletWithDraw.setWithDrawMobilBanking(mobileBank);
				}
				session.persist(walletWithDraw);
				transaction.commit();
				closeSession(session);
				log.info("Wallet WithDraw Done !!");
				
				return true;
			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
				}
				closeSession(session);

				e.printStackTrace();
				
			}

		}

		return false;
	}
	


	@Override
	public WalletWithDraw getWalletWithByPublicId(String publicId) {

		Optional<WalletWithDraw> optional = walletWithDrawRepository.getWalletWithDrawByPublicId(publicId);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	@Override
	public WalletWithDraw approveWalletWithDraw(ReqWalletApprove withDrawApprove, User user) {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			
			transaction = session.beginTransaction();
			
			WalletWithDraw draw = getWalletWithByPublicId(withDrawApprove.getPublicId());
			BankAccount bankAccount = bankAccountServices.getBankAccountByPublicID(withDrawApprove.getAccountId());
			Date date = new Date();
			if(draw != null && bankAccount != null) {
				
				
				WalletWithDraw dbWalletWithDraw = session.get(WalletWithDraw.class, draw.getId());
				
				PaymentStatus paymentStatus = session.get(PaymentStatus.class, 2);
				
				Wallet dbWallet = session.get(Wallet.class, dbWalletWithDraw.getCustomer().getWallet().getId());
				
				BankAccount dbBankAccount = session.get(BankAccount.class, bankAccount.getId());
				
				if(dbWallet.getTotalAmount() >= dbWalletWithDraw.getAmount()) {
					
					updateWalletWithDrawField(withDrawApprove, user, dbWalletWithDraw, paymentStatus);					
					
					updateWalletAmount(dbWalletWithDraw, dbWallet);					
					
					updateBankAmount(dbWalletWithDraw, dbBankAccount);
					
					BankWithDraw bankWithDraw = createBankWithDraw(user, date, dbWalletWithDraw, dbBankAccount);
					session.persist(bankWithDraw);
					
					session.update(dbBankAccount);				
					
					//TODO: Make Transaction 
					
					session.update(dbWallet);
					
					session.update(dbWalletWithDraw);
				}
				
								
				
			}
					
			
			transaction.commit();
		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.clear();
				session.close();
			}
		}

		return null;
	}

	private BankWithDraw createBankWithDraw(User user, Date date, WalletWithDraw dbWalletWithDraw, BankAccount dbBankAccount) {
		BankWithDraw bankWithDraw = new BankWithDraw();
		bankWithDraw.setAccount(dbBankAccount);
		bankWithDraw.setAmount(dbWalletWithDraw.getAmount());
		bankWithDraw.setDate(date);
		bankWithDraw.setDateGroup(date);
		bankWithDraw.setUser(user);
		
		return bankWithDraw;
	}

	private void updateBankAmount(WalletWithDraw dbWalletWithDraw, BankAccount dbBankAccount) {
		double dbNBankAmount = dbBankAccount.getAmount().doubleValue() - dbWalletWithDraw.getAmount();
		dbBankAccount.setAmount(new BigDecimal(dbNBankAmount));
	}

	private void updateWalletAmount(WalletWithDraw dbWalletWithDraw, Wallet dbWallet) {
		double nAamount = dbWallet.getTotalAmount() - dbWalletWithDraw.getAmount();
		dbWallet.setTotalAmount(nAamount);
	}

	private void updateWalletWithDrawField(ReqWalletApprove withDrawApprove, User user, WalletWithDraw dbWalletWithDraw,
			PaymentStatus paymentStatus) {
		dbWalletWithDraw.setApproveNote(withDrawApprove.getApproveNote());
		dbWalletWithDraw.setApproveUser(user);
		
		dbWalletWithDraw.setPaymentStatus(paymentStatus);
		dbWalletWithDraw.setStatus(2);
		dbWalletWithDraw.setTransStatus(true);
		dbWalletWithDraw.setWalletStaus(true);
	}
	
	private void closeSession(Session session) {
		if(session.isOpen()) {
			session.clear();
			session.close();
		}
	}


	private String getWalletWithDrawGenId(String payType) {
		
		return keyGenerator.getWalletWithDrawKeyOrGenId(payType, null);
	}

	private String getUniquePublicId() {
		
		String publicId = helperServices.getRnadoUUID();
		boolean status = true;
		while (status) {
			status = isPublicIdExist(publicId) ? true : false;
		}
		
		return publicId;
	}

	private boolean isPublicIdExist(String publicId) {
		Optional<WalletWithDraw> optional = walletWithDrawRepository.getWalletWithDrawByPublicId(publicId);
		if(!optional.isPresent()) {
			return false;
		}
		
		return true;
	}
	
}
