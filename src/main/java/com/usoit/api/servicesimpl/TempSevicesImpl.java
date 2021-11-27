package com.usoit.api.servicesimpl;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.TempVendor;
import com.usoit.api.repository.TempVendorRepository;
import com.usoit.api.services.TempSevices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TempSevicesImpl implements TempSevices {

	@Autowired
	private TempVendorRepository tempVendorRepository;

	private SessionFactory sessionFactory;

	
	@Autowired
	public void getSession(EntityManagerFactory factory) {

		if (factory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}

	@Override
	public boolean save(TempVendor tempVendor) {

		tempVendor.setId(0);

		tempVendorRepository.save(tempVendor);
		if (tempVendor.getId() > 0) {

			return true;
		}

		return false;
	}

	@Override
	public TempVendor getTempVendorByPublicId(String pubId) {

		TempVendor tempVendor = tempVendorRepository.getTempVendorByPublicIdAndValidStatus(pubId, 0);

		if (tempVendor != null) {
			return tempVendor;
		}

		return null;
	}

	@Override
	public TempVendor getValidTempVendorByPublicId(String pubId) {

		TempVendor tempVendor = getTempVendorByPublicId(pubId);

		if (tempVendor != null) {

			if (tempVendor.getValidStatus() == 0) {
				return tempVendor;
			}
		}

		return null;
	}

	@Override
	public boolean getUpdateValidation(String pubId) {

		if (pubId != null) {

			if (pubId.length() == 75) {
				TempVendor dbTempVendor = getValidTempVendorByPublicId(pubId);

				if (dbTempVendor != null) {

					int id = dbTempVendor.getId();
					dbTempVendor = null;

					Session session = sessionFactory.openSession();
					Transaction transaction = null;

					try {

						transaction = session.beginTransaction();

						TempVendor updateTemVendor = session.get(TempVendor.class, id);

						updateTemVendor.setValidStatus(1);

						session.update(updateTemVendor);

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
	public boolean getUpdateRejectAction(String pubId) {

		if (pubId != null) {

			if (pubId.length() == 75) {
				TempVendor dbTempVendor = getValidTempVendorByPublicId(pubId);

				if (dbTempVendor != null) {

					int id = dbTempVendor.getId();
					dbTempVendor = null;

					Session session = sessionFactory.openSession();
					Transaction transaction = null;

					try {

						transaction = session.beginTransaction();

						TempVendor updateTemVendor = session.get(TempVendor.class, id);

						updateTemVendor.setValidStatus(2);

						session.update(updateTemVendor);

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

}
