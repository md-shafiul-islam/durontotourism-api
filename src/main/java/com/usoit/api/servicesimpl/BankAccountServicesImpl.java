package com.usoit.api.servicesimpl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.BankAccount;
import com.usoit.api.model.BankAccountType;
import com.usoit.api.model.BankAccountUpdateInf;
import com.usoit.api.model.Country;
import com.usoit.api.model.Status;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqBankAccountUApprove;
import com.usoit.api.model.request.ReqBankApprove;
import com.usoit.api.model.request.ReqBankReject;
import com.usoit.api.repository.BankAccountRepository;
import com.usoit.api.repository.BankAccountUpdateInfRepository;
import com.usoit.api.repository.StatusRepository;
import com.usoit.api.services.BankAccountServices;
import com.usoit.api.services.HelperServices;

@Service
public class BankAccountServicesImpl implements BankAccountServices {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private BankAccountUpdateInfRepository bankAccountUpdateInfRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private HelperServices helperServices;

	@Autowired
	private BankAccountTypeServices bankAccountTypeServices;

	private SessionFactory sessionFactory;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public List<BankAccount> getBankAccounts() {

		return (List<BankAccount>) bankAccountRepository.findAll();
	}

	@Override
	public List<BankAccount> getActiveBankAccounts() {

		return bankAccountRepository.getAllBankAccountByActive(true);
	}

	@Override
	public BankAccount getBankAccountByAccountNumber(String acNo) {

		if (acNo != null) {

			return bankAccountRepository.getBankAccountByAccountNumber(acNo);
		}

		return null;
	}

	@Override
	public BankAccountType getBankAccountType(String id) {

		if (id != null) {

			if (!id.isEmpty()) {

				return bankAccountTypeServices.getBankAccountTypeById(helperServices.getStringToInt(id));
			}
		}

		return null;
	}

	@Override
	public boolean approveBankAccount(ReqBankApprove bankApprove, User user) {

		if (bankApprove != null) {

			BankAccount bankAccount = getBankAccountByPublicID(bankApprove.getPublicId());

			if (bankAccount != null) {

				Session session = sessionFactory.openSession();
				Transaction transaction = null;

				try {

					transaction = session.beginTransaction();

					Status status = session.get(Status.class, bankApprove.getStatus());

					BankAccount dbBankAccount = session.get(BankAccount.class, bankAccount.getId());
					dbBankAccount.setStatus(status);
					dbBankAccount.setActive(true);
					dbBankAccount.setUpdateUser(user);
					session.update(dbBankAccount);
					transaction.commit();
					session.clear();
					session.close();
					return true;

				} catch (Exception e) {

					if (transaction != null) {
						transaction.rollback();
					}
					session.clear();
					session.close();
					e.printStackTrace();
				} finally {
					System.out.println("Close Bank Approve Finally ");

				}

			}
		}
		return false;
	}

	@Override
	public List<BankAccount> getUpdatePendingBankAccounts() {

		return bankAccountRepository.getAllBankAccountByUpdateReqStatus(true);
	}

	@Override
	public List<BankAccount> getAllBankName() {

		return getActiveBankAccounts();
	}

	@Override
	public List<BankAccount> getAllBankByName(String bankName) {

		return bankAccountRepository.getBankAccountBybankNameAndActive(bankName, true);
	}

	@Override
	public boolean rejectBankAccount(ReqBankReject bankReject, User user) {

		if (bankReject != null && user != null) {

			BankAccount account = getBanAccountByPubliidAndActiveStatus(bankReject.getPublicId(), false);

			if (account != null) {

				Session session = sessionFactory.openSession();
				Transaction transaction = null;

				try {

					transaction = session.beginTransaction();
					Status status = session.get(Status.class, 3);
					BankAccount bdAcoount = session.get(BankAccount.class, account.getId());

					bdAcoount.setStatus(status);
					bdAcoount.setUpdateUser(user);
					transaction.commit();
					session.clear();
					session.close();
					return true;
				} catch (Exception e) {

					if (transaction != null) {
						transaction.rollback();
					}

					e.printStackTrace();
				}

			}
		}

		return false;
	}

	@Override
	public BankAccount getBankAccountByPublicID(String id) {

		if (id != null) {
			System.out.println("Bank Given ID " + id);
			if (!id.isEmpty()) {

				Optional<BankAccount> optional = bankAccountRepository.getBankAccountByPublicId(id);

				if (optional.isPresent()) {
					System.out.println("Bank Acoount Found ");
					return optional.get();
				} else {
					System.out.println("Bank Acoount Not Found " + optional);
				}

			}
		}

		return null;
	}

	@Override
	public boolean addBankAcount(BankAccount bankAccount, User user) {

		if (bankAccount != null) {

			bankAccount.setPublicId(getPublicId());
			bankAccount.setBankAccountType(bankAccountTypeServices.getBankAccountTypeById(1));
			bankAccount.setStatus(getStatus());
			bankAccount.setCreatedUser(user);
			bankAccountRepository.save(bankAccount);

			return true;
		}

		return false;
	}

	@Override
	public boolean isKeyExist(String key) {

		if (key != null) {
			Optional<BankAccount> option = bankAccountRepository.getBankAccountByPublicId(key);

			if (option.isPresent()) {
				System.out.println("Bank Account Found !! ");
				return true;

			}
		}
		System.out.println("Bank Account Not Found :) ");
		return false;
	}

	@Override
	public boolean updateBankAccountInf(BankAccountUpdateInf bAccountUpdateInf) {

		if (bAccountUpdateInf != null) {
			BankAccount bankAccount = getBankAccountByPublicID(bAccountUpdateInf.getBankId());
			Session session = sessionFactory.openSession();
			Transaction transaction = null;
			try {

				transaction = session.beginTransaction();

				BankAccount dbAccount = session.get(BankAccount.class, bankAccount.getId());
				dbAccount.setUpdateReqStatus(true);
				session.update(dbAccount);

				session.persist(bAccountUpdateInf);

				transaction.commit();

				return true;

			} catch (Exception e) {

				if (transaction != null) {
					transaction.rollback();
					e.printStackTrace();
				}
			}

		}

		return false;
	}

	@Override
	public BankAccountUpdateInf getBankUpdateInfByBankId(String publicId) {

		Optional<BankAccountUpdateInf> optional = bankAccountUpdateInfRepository
				.getBankAccountUpdateInfByBankIdAndActive(publicId, true);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	@Override
	public boolean approveUpdateBankAccount(ReqBankAccountUApprove approveReq, User updateApproveUser) {

		if (approveReq != null) {

			BankAccount account = getBankAccountByPublicID(approveReq.getPublicId());

			BankAccountUpdateInf updateInf = getBankUpdateInfByBankId(approveReq.getPublicId());

			if (account != null && updateInf != null) {

				String dbFieldName = updateInf.getFieldName();
				String dbValue = updateInf.getValue();
				boolean valid = false;
				if (dbFieldName.equals(approveReq.getRestUpdateInf().getFieldName())
						&& dbValue.equals(approveReq.getRestUpdateInf().getValue())) {
					valid = true;
				}

				System.out.println("Account Status" + account.isUpdateReqStatus() + " ID " + account.getId());

				if (account.isUpdateReqStatus() && valid) {

					Session session = sessionFactory.openSession();
					Transaction transaction = null;

					try {

						transaction = session.beginTransaction();
						BankAccount dbAccount = session.get(BankAccount.class, account.getId());
						BankAccountUpdateInf dbUpdateInf = session.get(BankAccountUpdateInf.class, updateInf.getId());
						if (getUpdateFieldValidationAndSet(dbUpdateInf, approveReq, dbAccount, session)) {

							dbAccount.setUpdateReqStatus(false);
							dbAccount.setUpdateApproveUser(updateApproveUser);

							session.update(dbAccount);

							dbUpdateInf.setActive(false);
							dbUpdateInf.setApprove(1);
							dbUpdateInf.setStatus(1);
							session.update(dbUpdateInf);

						}

						transaction.commit();
						session.clear();
						session.close();

						return true;
					} catch (Exception e) {

						if (transaction != null) {
							transaction.rollback();

						}
						e.printStackTrace();
					}

				}
			}
		}

		return false;
	}

	@Override
	public List<BankAccount> getActiveWalletBankAccounts() {

		return bankAccountRepository.getAllBankAccountByActiveAndWalletEnable(true, true);
	}

	@Override
	public List<BankAccount> getActiveWalletBankAccountsByType(String accountType) {
		List<BankAccount> bankAccounts = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<BankAccount> criteriaQuery = criteriaBuilder.createQuery(BankAccount.class);
			Root<BankAccount> root = criteriaQuery.from(BankAccount.class);
			criteriaQuery.select(root);

			criteriaQuery.where(
					criteriaBuilder.and(criteriaBuilder.equal(root.get("bankAccountType").get("value"), accountType),
							criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), true),
									criteriaBuilder.equal(root.get("walletEnable"), true))));

			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("bankName")));
			Query<BankAccount> query = session.createQuery(criteriaQuery);

			System.out.println("From Action");

			System.out.println("After Session Clear and close !!");
			bankAccounts = query.getResultList();
			transaction.commit();
			session.clear();
			session.close();
			return bankAccounts;
		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return null;
	}

	private boolean getUpdateFieldValidationAndSet(BankAccountUpdateInf dbUpdateInf, ReqBankAccountUApprove approveReq,
			BankAccount dbAccount, Session session)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		System.out.println("getUpdateFieldValidationAndSet Run :) ");

		if (approveReq != null && dbUpdateInf != null && dbAccount != null) {

			Field field = approveReq.getClass().getDeclaredField(dbUpdateInf.getFieldName());
			field.setAccessible(true);
			String fieldValue = (String) field.get(approveReq);

			System.out.println("Field Value " + fieldValue + " DB Value " + dbUpdateInf.getValue());

			if (fieldValue.equals(dbUpdateInf.getValue())) {

				String countryStr = "country";
				String bankingType = "bankAccountType";

				if (dbUpdateInf.getFieldName().equals(countryStr) || dbUpdateInf.getFieldName().equals(bankingType)) {

					Field selectedField = dbAccount.getClass().getDeclaredField(dbUpdateInf.getFieldName());
					selectedField.setAccessible(true);

					if (dbUpdateInf.getFieldName().equals(countryStr)) {
						Country country = session.get(Country.class, Integer.parseInt(dbUpdateInf.getValue()));
						selectedField.set(dbAccount, country);

						System.out.println("Country Name: " + country.getName());
					}

					if (dbUpdateInf.getFieldName().equals(bankingType)) {
						BankAccountType bankAcType = session.get(BankAccountType.class,
								Integer.parseInt(dbUpdateInf.getValue()));
						selectedField.set(dbAccount, bankAcType);
						System.out.println("Bank Type Name: " + bankAcType.getName());
					}

				} else {
					Field bankName = dbAccount.getClass().getDeclaredField(dbUpdateInf.getFieldName());
					bankName.setAccessible(true);

					String amount = "amount";

					if (dbUpdateInf.getFieldName().equals(amount)) {
						bankName.set(dbAccount, new BigDecimal(dbUpdateInf.getValue()));
					} else {
						bankName.set(dbAccount, dbUpdateInf.getValue());
					}

				}

				return true;
			}

		}

		return false;

	}

	private boolean isValid(Object obj) {

		return obj != null;
	}

	private BankAccount getBanAccountByPubliidAndActiveStatus(String publicId, boolean active) {

		Optional<BankAccount> optional = bankAccountRepository.getBankAccountByPublicIdAndActive(publicId, active);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;
	}

	private Status getStatus() {

		Optional<Status> optional = statusRepository.findById(1);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	private String getPublicId() {

		String publicId = null;

		boolean status = true;
		while (status) {

			publicId = helperServices.getRnadoUUID();

			if (!isKeyExist(publicId)) {

				status = false;
			}
		}
		return publicId;
	}

}
