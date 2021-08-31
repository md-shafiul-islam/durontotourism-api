package com.usoit.api.servicesimpl;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Department;
import com.usoit.api.model.UserAddressTemp;
import com.usoit.api.model.UserTemp;
import com.usoit.api.repository.TempUserRepository;
import com.usoit.api.services.TempUserServices;

@Service
class TempUserServicesImpl implements TempUserServices {

	@Autowired
	private TempUserRepository tempUserRepository;

	private SessionFactory sessionFactory;

	@Override
	public boolean isKeyExist(String key) {

		if(key != null) {
			UserTemp option = tempUserRepository.getUserTempByPublicId(key);
			
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
	public UserTemp getUserTempByPubId(String pubId) {

		if (pubId != null) {

			if (pubId.length() == 30) {
				return tempUserRepository.getUserTempByPublicId(pubId);
			}
		}
		return null;
	}

	@Override
	public boolean getRejectedRequest(String publicId) {

		if (publicId != null) {

			if (publicId.length() == 30) {

				UserTemp temp = getUserTempByPubIdAlive(publicId);

				if (temp != null) {

					int tmpeUserId = temp.getId();
					temp = null;

					if (sessionFactory != null) {

						Session session = sessionFactory.openSession();

						Transaction transaction = null;

						try {

							transaction = session.beginTransaction();

							UserTemp userTemp = session.get(UserTemp.class, tmpeUserId);

							if (userTemp != null) {

								userTemp.setLifelStatus(2);
								session.update(userTemp);

							}

							transaction.commit();

							if (userTemp.getLifelStatus() == 2) {
								return true;
							}

						} catch (Exception e) {

							if (transaction != null) {

								transaction.rollback();

								e.printStackTrace();
							}
						}

					}

				}
			}

		}

		return false;
	}

	@Override
	public boolean updateUserData(UserTemp tempUser) {

		if (tempUser != null) {

			if (tempUser.getId() > 0) {

				int tmpeUserId = tempUser.getId();
				tempUser = null;

				if (sessionFactory != null) {

					Session session = sessionFactory.openSession();

					Transaction transaction = null;

					try {

						transaction = session.beginTransaction();

						UserTemp userTemp = session.get(UserTemp.class, tmpeUserId);

						if (userTemp != null) {

							userTemp.setLifelStatus(1);
							session.update(userTemp);

						}

						transaction.commit();

						if (userTemp.getLifelStatus() == 1) {
							return true;
						}

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
	public boolean saveTemUser(UserTemp userTemp) {
		
		if (userTemp != null) {
			
			System.out.println("uset Temp Not null Save!!");
			
			userTemp.setId(0);
			
			if (userTemp.getUserAddresses() != null) {
				
				if (userTemp.getUserAddresses().size() > 0) {
					
					System.out.println("uset Temp have Address Save!!");
					
					for (UserAddressTemp address : userTemp.getUserAddresses()) {
						address.setId(0);
						address.setUserTemp(userTemp);
					}
				}
				
			}
			
			System.out.println("uset Temp befor  Save Action !!");
			tempUserRepository.save(userTemp);
			System.out.println("uset Temp After  Save Action !!");
			
			if (userTemp.getId() > 0) {
				System.out.println("uset Temp After  Save Action Done ");
				return true;
			}else {
				System.out.println("uset Temp After  Save Action !! Some Error !!");
			}
		}
		
		return false;
	}

	@Override
	public UserTemp getUserTempByPubIdAlive(String pubId) {
		
		int ap = 0;
		Session session = sessionFactory.openSession();

		try {
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<UserTemp> criteriaQuery = criteriaBuilder.createQuery(UserTemp.class);
			Root<UserTemp> root = criteriaQuery.from(UserTemp.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("lifelStatus"), ap), criteriaBuilder.equal(root.get("publicId"), pubId)));

			
			Query<UserTemp> query = session.createQuery(criteriaQuery);


			session.clear();
			System.out.println("After Tem User Found Query !!");

			return query.getSingleResult();
			
		}catch (NoResultException e) {
			
			return null;
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}