package com.usoit.api.servicesimpl;

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

import com.usoit.api.data.model.Address;
import com.usoit.api.data.model.ContactPerson;
import com.usoit.api.data.model.Packages;
import com.usoit.api.data.model.PaymentInfo;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.repository.VendorRepository;
import com.usoit.api.services.VendorServices;

@Service
public class VendorServicesImpl implements VendorServices {

	@Autowired
	private VendorRepository vendorRepository;

	private SessionFactory sessionFactory;

	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public boolean save(Vendor vendor) {

		System.out.println("Vendor save Run!!");

		if (0 >= vendor.getId() && vendor.getAddresses() != null && vendor.getPaymentInfos() != null
				&& vendor.getContactPersons() != null) {

			long cId = vendorRepository.count() +1;
			
			String vGenId = "Vendor " + Long.toString(cId);
			vendor.setVGenId(vGenId);
			
			return saveVendor(vendor);
		}

		return false;
	}

	@Override
	public Vendor getVendorById(int id) {

		if (id > 0) {

			Optional<Vendor> optional = vendorRepository.findById(id);

			if (optional != null) {

				return optional.get();
			}
		}

		return null;
	}

	@Override
	public boolean update(Vendor vendor) {

		if (vendor.getId() > 0 && vendor.getAddresses() != null && vendor.getPaymentInfos() != null
				&& vendor.getContactPersons() != null) {
			vendorRepository.save(vendor);

			return true;
		}

		return false;
	}

	@Override
	public List<Vendor> getAllVendors() {
		return (List<Vendor>) vendorRepository.findAll();
	}

	@Override
	public long getCount() {
		return vendorRepository.count();
	}
	
	@Override
	public List<Vendor> getAllPanding() {
		return getPandingVendor();
	}
	
	@Override
	public Vendor getVendorByPublicId(String publicId) {
		if (publicId != null) {
			
			if (publicId.length() >= 30) {
				return vendorRepository.getVendorByPublicId(publicId);
			}
		}
		
		return null;
	}
	
	private List<Vendor> getPandingVendor() {
		
		int ap = 0;
		
		Session session = sessionFactory.openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Vendor> criteriaQuery = criteriaBuilder.createQuery(Vendor.class);
		Root<Vendor> root = criteriaQuery.from(Vendor.class);
		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(root.get("approveStatus"), ap));

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		Query<Vendor> query = session.createQuery(criteriaQuery);

		System.out.println("From Action");

		session.clear();
		// session.close();
		System.out.println("After Session Clear and close !!");

		return query.getResultList();
	}

	private boolean saveVendor(Vendor vendor) {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			if (vendor.getAddresses() != null) {

				for (Address address : vendor.getAddresses()) {

					address.setVendor(vendor);
				}
			}

			if (vendor.getContactPersons() != null) {

				for (ContactPerson person : vendor.getContactPersons()) {

					if (person.getPhoneNo() != null && person.getCountry1() != null) {

						String phoneNo = person.getCountry1().getDialOrPhoneCode() + "-" + person.getPhoneNo();

						person.setPhoneNo(phoneNo);
					}

					if (person.getPhoneNo2() != null && person.getCountry2() != null) {

						String phoneNo2 = person.getCountry2().getDialOrPhoneCode() + "-" + person.getPhoneNo2();

						person.setPhoneNo2(phoneNo2);
					}

					person.setVendor(vendor);
				}
			}

			if (vendor.getPaymentInfos() != null) {

				for (PaymentInfo payInf : vendor.getPaymentInfos()) {
					payInf.setVendor(vendor);
				}
			}

			session.persist(vendor);

			transaction.commit();
			return true;

		} catch (Exception e) {

			if (transaction != null) {

				transaction.rollback();
			}

			e.printStackTrace();
		}

		return false;
	}

}
