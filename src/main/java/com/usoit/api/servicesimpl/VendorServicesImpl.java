package com.usoit.api.servicesimpl;

import java.util.Date;
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

import com.usoit.api.model.Address;
import com.usoit.api.model.ContactPerson;
import com.usoit.api.model.Department;
import com.usoit.api.model.Packages;
import com.usoit.api.model.PaymentInfo;
import com.usoit.api.model.Vendor;
import com.usoit.api.repository.VendorRepository;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.VendorMapper;
import com.usoit.api.services.VendorServices;

import net.bytebuddy.asm.Advice.Return;

@Service
public class VendorServicesImpl implements VendorServices {

	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private HelperServices helperServices;
	
	@Autowired
	private VendorMapper vendorMapper;

	private SessionFactory sessionFactory;

	
	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}
	
	@Override
	public boolean isKeyExist(String key) {

		if(key != null) {
			Vendor option = vendorRepository.getVendorByPublicId(key);
			
			if(option != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean save(Vendor vendor) {

		System.out.println("Vendor save Run!!");

		if (0 >= vendor.getId() && vendor.getAddresses() != null && vendor.getPaymentInfos() != null
				&& vendor.getContactPersons() != null) {

			long cId = vendorRepository.count() +1;
			
			String vGenId = "SUP " + Long.toString(cId);
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
	
	@Override
	public boolean approveVendorByPublicID(String pubId) {
	
		if (pubId != null) {
			
			if (pubId.length() == 75) {
				
				Vendor dbVendor = vendorRepository.getVendorByPublicId(pubId);
				
				if (dbVendor != null) {
					
					int dbId = dbVendor.getId();
					
					dbVendor = null;
					
					Session session = sessionFactory.openSession();
					Transaction transaction = null;
					
					try {
						
						transaction = session.beginTransaction();
						
						Vendor approveVendor = session.get(Vendor.class, dbId);
						
						approveVendor.setApproveStatus(1);
						approveVendor.setUpdateApprove(1);
						
						session.update(approveVendor);
						
						transaction.commit();
						
						return true;
						
					} catch (Exception e) {

						if (transaction != null) {
							
							transaction.rollback();
							
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}
	
	
	@Override
	public boolean rejectVendorByPublicID(String pubId) {
	

		if (pubId != null) {
			
			if (pubId.length() == 75) {
				
				Vendor dbVendor = vendorRepository.getVendorByPublicId(pubId);
				
				if (dbVendor != null) {
					
					int dbId = dbVendor.getId();
					
					dbVendor = null;
					
					Session session = sessionFactory.openSession();
					Transaction transaction = null;
					
					try {
						
						transaction = session.beginTransaction();
						
						Vendor approveVendor = session.get(Vendor.class, dbId);
						
						approveVendor.setApproveStatus(2);
						approveVendor.setUpdateApprove(2);
						
						session.update(approveVendor);
						
						transaction.commit();
						
						return true;
						
					} catch (Exception e) {

						if (transaction != null) {
							
							transaction.rollback();
							
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}
	
	
	@Override
	public boolean approveUpdateVendor(Vendor vendor) {
		
		if (vendor != null) {
			
			Vendor dbVendor = vendorRepository.getVendorByPublicId(vendor.getPublicId());
			
			if (dbVendor != null) {
				
				int dbId = dbVendor.getId();
				
				vendor.setId(dbId);
				vendor.setApproveStatus(dbVendor.getApproveStatus());
				vendor.setUpdateApprove(dbVendor.getUpdateApprove());
				
				if (dbVendor.getDate() == null) {
					vendor.setDate(new Date());
				}else {
					vendor.setDate(dbVendor.getDate());
				}
				
				if (dbVendor.getDateGroup() != null) {
					
					vendor.setDateGroup(dbVendor.getDateGroup());
				}else {
					
					vendor.setDateGroup(new Date());
				}
				
				vendor.setUser(dbVendor.getUser());
				vendor.setVGenId(dbVendor.getVGenId());
				
				if (vendor.getId() > 0) {
					vendorRepository.save(vendor);
				}else {
					vendor.setId(dbVendor.getId());
					vendorRepository.save(vendor);
				}
				
				return true;
				/*
				if (updateVendorPrevVendor(vendor, dbId)) {
					
					return true;
				}*/
			}
		}
		
		return false;
	}
	
	@Override
	public List<Vendor> getAllUpdatePendinVendors() {
		
		
		
		return vendorRepository.getAllVendorByApproveStatusAndUpdateApprove(1, 0);
	}
	
	
	@Override
	public List<Vendor> getAllRejectVendor() {
		
		//For GetAllRejected Vendors
		return vendorRepository.getAllVendorByApproveStatusAndUpdateApprove(2, 2);
	}
	
	@Override
	public boolean updateRquestTaken(String publicId) {
		
		if (publicId != null) {
			
			if (publicId.length() == 75) {
				
				Vendor vendor = vendorRepository.getVendorByPublicId(publicId);
				
				if (vendor != null) {
					
					int vId = vendor.getId();
					
					vendor = null;
					
					Session session = sessionFactory.openSession();
					Transaction transaction = null;
					
					try {
						
						transaction = session.beginTransaction();
						
						Vendor updateTake = session.get(Vendor.class, vId);
						
						updateTake.setUpdateApprove(0);
						
						session.update(updateTake);
						
						transaction.commit();
						
						return true;
						
					} catch (Exception e) {

						if (transaction != null) {
							
							transaction.rollback();
							e.printStackTrace();
						}
					
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean updateVendorPrevVendor(Vendor vendor, int dbId) {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		try {
			
			transaction = session.beginTransaction();
			
			Vendor updateVendor = session.get(Vendor.class, dbId);
			
			
			
			transaction.commit();
			
		} catch (Exception e) {
			
			if (transaction != null) {
				
				transaction.rollback();
				e.printStackTrace();
			}
		}
		
		return false;
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
