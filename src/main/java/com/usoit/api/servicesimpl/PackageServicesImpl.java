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

import com.usoit.api.data.converter.PackageMapper;
import com.usoit.api.data.shared.dto.DtoUpdatePackage;
import com.usoit.api.model.ImageGallery;
import com.usoit.api.model.Itarnary;
import com.usoit.api.model.Packages;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqPackage;
import com.usoit.api.repository.PackageRepository;
import com.usoit.api.services.PackageServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PackageServicesImpl implements PackageServices {

	@Autowired
	private PackageRepository packageRepository;
	
	@Autowired
	private PackageMapper packageMapper;

	private SessionFactory sessionFactory;

	@Override
	public boolean isKeyExist(String key) {

		if(key != null) {
			Packages option = packageRepository.getPackageByPublicId(key);
			
			if(option != null) {
				option = null;
				return true;
			}
		}
		return false;
	}
	
	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public Packages getPackageById(int id) {

		if (id > 0) {

			Optional<Packages> optional = packageRepository.findById(id);

			if (optional != null) {

				return optional.get();
			}
		}

		return null;
	}

	@Override
	public boolean save(Packages packages) {

		if (0 >= packages.getId()) {

			packages.setDate(new Date());
			packages.setDateGroup(new Date());

			if (packages.getItarnarys() != null) {

				for (Itarnary item : packages.getItarnarys()) {
					item.setPackages(packages);
					item.setDate(new Date());
					item.setDateGroup(new Date());
				}
			}

			packageRepository.save(packages);

			if (packages.getId() > 0) {
				return true;
			}
		}

		return false;
	}

	@Override
	public List<Packages> getAllPandingPackages() {

		int ap = 0;
		Session session = sessionFactory.openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Packages> criteriaQuery = criteriaBuilder.createQuery(Packages.class);
		Root<Packages> root = criteriaQuery.from(Packages.class);
		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(root.get("approvalStatus"), ap));

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		Query<Packages> query = session.createQuery(criteriaQuery);

		log.info("From Action");

		session.clear();
		// session.close();
		log.info("After Session Clear and close !!");

		return query.getResultList();
	}

	@Override
	public long getCount() {
		return packageRepository.count();
	}

	@Override
	public List<Packages> getAllPackages() {
		return (List<Packages>) packageRepository.findAll();
	}

	@Override
	public List<Packages> getAllConfrimPackages() {
		return getConfrimPackages();
	}

	@Override
	public boolean rejectPackageById(int id, User user) {

		if (id > 0) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				Packages packages = session.get(Packages.class, id);

				if (packages != null) {

					packages.setApprovalStatus(2);
					packages.setUpdateApproval(2);
					packages.setApproveUser(user);
					packages.setModydiyStatus("By Add Package");

					session.update(packages);
				}

				transaction.commit();

				return true;
			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
				}
				e.printStackTrace();

			}
		}

		return false;
	}

	@Override
	public List<Packages> getAllRejectPackages() {
		return getRejectPackages();
	}
	
	@Override
	public ReqPackage getRePackageByPublicId(String pId) {
		
		return getReqPackByPublicId(pId);
	}
	
	@Override
	public DtoUpdatePackage getRePackageUpdateByPublicId(String pId) {
		return getReqUpdatePackByPublicId(pId);
	}
	
	@Override
	public boolean updatePackApproveByPbID(String publicId, User user) {
		
		Packages packages = null;
		
		if (publicId != null && user != null) {
			
			packages = packageRepository.getPackageByPublicId(publicId);
		}else {
			return false;
		}
		
		if (packages != null) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				Packages dbPackage = session.get(Packages.class, packages.getId());

				if (packages != null) {

					dbPackage.setApprovalStatus(1);
					dbPackage.setUpdateApproval(1);
					dbPackage.setApproveUser(user);
					dbPackage.setModydiyStatus("Update");

					session.update(dbPackage);
				}

				transaction.commit();

				return true;
			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
				}
				e.printStackTrace();

			}
		}
		
		return false;
	}
	
	@Override
	public boolean updatePackRejectByPbID(String publicId, User user) {
		
		Packages packages = null;
		
		if (publicId != null && user != null) {
			
			packages = packageRepository.getPackageByPublicId(publicId);
		}else {
			return false;
		}
		
		if (packages != null) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				Packages dbPackage = session.get(Packages.class, packages.getId());

				if (packages != null) {

					dbPackage.setApprovalStatus(2);
					dbPackage.setUpdateApproval(2);
					dbPackage.setApproveUser(user);
					dbPackage.setModydiyStatus("By Update");

					session.update(dbPackage);
				}

				transaction.commit();

				return true;
			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
				}
				e.printStackTrace();

			}
		}
		
		return false;
		
	}
	
	
	private DtoUpdatePackage getReqUpdatePackByPublicId(String pId) {
		log.info("Get Data By Public ID!!!!!");
		Packages packages = null;
		DtoUpdatePackage reqPackage = null;
		
		if (pId != null) {
			
			if (pId.length() == 55) {
				
				log.info(" Data ID Lenght Pass!!");
				
				packages = packageRepository.getPackageByPublicId(pId);
			}
		}
		
		if (packages != null) {
			
			log.info(" Data Found!!");
			reqPackage = packageMapper.getGetUpdatePack(packages);
		}
		
		return reqPackage;
	}

	private ReqPackage getReqPackByPublicId(String pId) {
		log.info("Get Data By Public ID!!!!!");
		Packages packages = null;
		ReqPackage reqPackage = null;
		
		if (pId != null) {
			
			if (pId.length() == 55) {
				
				log.info(" Data ID Lenght Pass!!");
				
				packages = packageRepository.getPackageByPublicId(pId);
			}
		}
		
		if (packages != null) {
			
			log.info(" Data Found!!");
			reqPackage = packageMapper.getReqPackage(packages);
		}
		
		return reqPackage;
	}

	private List<Packages> getConfrimPackages() {

		int ap = 1;
		Session session = sessionFactory.openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Packages> criteriaQuery = criteriaBuilder.createQuery(Packages.class);
		Root<Packages> root = criteriaQuery.from(Packages.class);
		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("approvalStatus"), ap),
				criteriaBuilder.equal(root.get("updateApproval"), ap)));

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		Query<Packages> query = session.createQuery(criteriaQuery);

		log.info("From Action");

		session.clear();
		// session.close();
		log.info("After Session Clear and close !!");

		return query.getResultList();
	}

	private List<Packages> getRejectPackages() {

		int ap = 2;
		Session session = sessionFactory.openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Packages> criteriaQuery = criteriaBuilder.createQuery(Packages.class);
		Root<Packages> root = criteriaQuery.from(Packages.class);
		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("approvalStatus"), ap),
				criteriaBuilder.equal(root.get("updateApproval"), ap)));

		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
		Query<Packages> query = session.createQuery(criteriaQuery);

		log.info("From Action");

		session.clear();
		// session.close();
		log.info("After Session Clear and close !!");

		return query.getResultList();
	}

	@Override
	public boolean approvePackageById(int id, User curUser) {

		if (id > 0) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				Packages packages = session.get(Packages.class, id);

				if (packages != null) {

					packages.setApproveUser(curUser);
					packages.setApprovalStatus(1);
					packages.setUpdateApproval(1);

					session.update(packages);
				}

				transaction.commit();

				return true;
			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
				}
				e.printStackTrace();

			}

		}

		return false;
	}

	@Override
	public boolean updatePack(Packages packages) {

		if (packages.getId() > 0) {

			packages.setApprovalStatus(1);
			packages.setUpdateApproval(0);

			if (packages.getItarnarys().size() > 0) {

				packageRepository.save(packages);
			}
		}

		return true;
	}

	@Override
	public List<Packages> getAllUpdatePandingApprovPackage() {
		return getAllUpdatePandingPackages();
	}

	private List<Packages> getAllUpdatePandingPackages() {

		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		List<Packages> list = null;

		int uStatus = 0;
		int apStatus = 1;

		try {

			transaction = session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Packages> criteriaQuery = criteriaBuilder.createQuery(Packages.class);
			Root<Packages> root = criteriaQuery.from(Packages.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("approvalStatus"), apStatus),
					criteriaBuilder.equal(root.get("updateApproval"), uStatus)));

			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			Query<Packages> query = session.createQuery(criteriaQuery);

			log.info("From Action");

			session.clear();
			// session.close();
			log.info("After Session Clear and close !!");

			list = query.getResultList();

			transaction.commit();

		} catch (Exception e) {

			if (transaction != null) {

				transaction.rollback();
			}

			e.printStackTrace();

		}

		return list;
	}

	@Override
	public boolean approveUpdatePackageById(int id, User curUser) {

		if (id > 0) {

			Session session = sessionFactory.openSession();
			Transaction transaction = null;

			try {

				transaction = session.beginTransaction();

				Packages packages = session.get(Packages.class, id);

				if (packages != null) {

					packages.setUpdateUser(curUser);
					packages.setApprovalStatus(1);
					packages.setUpdateApproval(1);

					session.update(packages);
				}

				transaction.commit();

				return true;
			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
				}
				e.printStackTrace();

			}

		}

		return false;
	}
	
	@Override
	public boolean updatePackDto(DtoUpdatePackage dtoPackages) {
		
		Packages dbPackages = null;
		
		if (dtoPackages.getPublicId().length() >= 30) {
			dbPackages  = packageRepository.getPackageByPublicId(dtoPackages.getPublicId());
		}
		
		if (dbPackages != null) {
			if(packageMapper.getPackDtoPackToPackage(dbPackages, dtoPackages)) {
				
				int pId = dbPackages.getId();
				log.info("Data Update Befor Save !! Pack Services");
				packageRepository.save(dbPackages);
				
				if (pId == dbPackages.getId()) {
					
					log.info("Data Updated !! Pack Services");
					
					return true;
				}else {
					return false;
				}
			}
		}
		
		return false;
		
	}
	
	@Override
	public Packages getPackageByPID(String publicId) {
		if (publicId != null) {
			
			if (publicId.length() == 55) {
				
				return packageRepository.getPackageByPublicId(publicId);
			}
		}
		return null;
	}
	
	@Override
	public boolean rejactPackByPublicId(String packId, User user) {
		
		if (packId != null) {
			
			if (packId.length() == 55) {
				
				Packages packages = packageRepository.getPackageByPublicId(packId);
				
				if (packages != null) {
					return rejectPackageById(packages.getId(), user);
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean approvePackByPublicId(String publicId, User user) {
		
		if (publicId != null) {
			
			if (publicId.length() == 55) {
				
				Packages packages = packageRepository.getPackageByPublicId(publicId);
				
				if (packages != null) {
					
					return approvePackageById(packages.getId(), user);
				}
			}
		}
		
		return false;
	}
	
	private boolean checkAndUpdate(Packages pack) {

		log.info("Pack Update Services !!!!");

		Packages packagesDB = null;

		Optional<Packages> optional = packageRepository.findById(pack.getId());

		if (optional != null) {
			packagesDB = optional.get();
		}

		if (packagesDB != null) {

			if (pack.getItarnarys() != null && packagesDB != null) {

				int count = 0;

				for (Itarnary item : pack.getItarnarys()) {

					int cItnId = packagesDB.getItarnarys().get(count).getId();

					item.setId(cItnId);
					count++;
				}
			}

			if (pack.getCountries() != null && packagesDB.getCountries() != null) {

				int countCnt = 0;
				packagesDB.getCountries().removeAll(packagesDB.getCountries());

				/*
				 * for (Country cnt : pack.getCountries()) {
				 * 
				 * packagesDB.getCountries().add(cnt); }
				 */

				packagesDB.setCountries(pack.getCountries());
			}

			packagesDB.setCode(pack.getCode());
			packagesDB.setCountries(pack.getCountries());
			packagesDB.setDescription(pack.getDescription());
			packagesDB.setName(pack.getName());
			packagesDB.setPackageCat(pack.getPackageCat());
			packagesDB.setPrice(pack.getPrice());
			packagesDB.setUpdateUser(pack.getUpdateUser());

			if (pack.getVideoUrl() != null) {
				packagesDB.setVideoUrl(pack.getVideoUrl());
			}

			if (pack.getImageGalleries() != null) {

				int count = 0;
				for (ImageGallery imageG : pack.getImageGalleries()) {

					ImageGallery imgG = packagesDB.getImageGalleries().get(count);

					if (imageG != null) {

						if (imageG.getSrcUrl() != null) {

							if (!imageG.getSrcUrl().isEmpty()) {

								imgG.setAltTag(imageG.getAltTag());
								imgG.setLocation(imageG.getLocation());
								imgG.setName(imageG.getName());
								imgG.setPackages(packagesDB);
								imgG.setSrcUrl(imageG.getSrcUrl());
							}
						}
					}

					count++;
				}
			}

		}

		packageRepository.save(packagesDB);

		return true;

	}

	
}
