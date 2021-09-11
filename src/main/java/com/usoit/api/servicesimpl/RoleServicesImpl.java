package com.usoit.api.servicesimpl;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestRole;
import com.usoit.api.model.Access;
import com.usoit.api.model.AccessType;
import com.usoit.api.model.Department;
import com.usoit.api.model.Role;
import com.usoit.api.model.User;
import com.usoit.api.repository.AccessTypeRepository;
import com.usoit.api.repository.RoleRepository;
import com.usoit.api.services.AccessServices;
import com.usoit.api.services.AccessTypeServices;
import com.usoit.api.services.HelperAuthenticationServices;
import com.usoit.api.services.RoleServices;
import com.usoit.api.services.UtilsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleServicesImpl implements RoleServices {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AccessTypeServices accessTypeServices;
	
	@Autowired
	private AccessServices accessServices;

	@Autowired
	private HelperAuthenticationServices helperAuthenticationServices;

	@Autowired
	private UtilsServices utilsServices;

	private SessionFactory sessionFactory;

	@Override
	public boolean isKeyExist(String key) {

		if (key != null) {
			Role role = roleRepository.getRoleByPublicId(key);

			if (role != null) {
				role = null;
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
	public List<Role> getAllRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	public long getCount() {
		return roleRepository.count();
	}

	@Override
	public Role getRoleById(int id) {

		if (id > 0) {

			Optional<Role> optional = roleRepository.findById(id);

			if (optional != null) {

				return optional.get();
			}
		}

		return null;
	}

	@Override
	public boolean save(Role role) {

		if (role != null) {
			role.setPublicId(getUnicKeyOrId());
			initRole(role);
			if (0 >= role.getId()) {

				roleRepository.save(role);

				if (role.getId() > 0) {
					return true;
				}
			}
		}

		return false;
	}


	private String getUnicKeyOrId() {
		boolean status = true;

		String key = null;
		while (status) {
			key = utilsServices.getUnicId();

			if (!isKeyExist(key)) {
				status = false;
			}
		}

		return key;
	}

	@Override
	public boolean update(Role role) {

		if (role.getId() > 0) {

			for (Access access : role.getAccesses()) {
				access.setRole(role);
			}

			roleRepository.save(role);

			return true;
		}

		return false;
	}

	@Override
	public RestRole getRestRoleByPublicId(String publicId) {

		return getRestRoleGivenPublicId(publicId);
	}

	@Override
	public List<Role> getAllGeneralRole() {
		
		List<Role> roles = new ArrayList<>();
		int cStatus = 0;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
			Root<Role> root = criteriaQuery.from(Role.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.equal(root.get("authStatus"), cStatus));

			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			Query<Role> query = session.createQuery(criteriaQuery);

			System.out.println("From Action");

			session.clear();
			System.out.println("After Session Clear and close !!");

			transaction.commit();

			roles = query.getResultList();
			
			if(roles != null) {
				for (int i = 0; i < roles.size(); i++) {
					if(roles.get(i) != null) {
						roles.get(i).getAccesses();
					}
				}
			}
			session.close();
			
		} catch (Exception e) {

			if (transaction != null) {

				transaction.rollback();
				e.printStackTrace();
			}
		}

		return roles;
	}

	private RestRole getRestRoleGivenPublicId(String publicId) {

		Role role = roleRepository.getRoleByPublicId(publicId);

		System.out.println("Role Services Run!!");

		RestRole restRole = new RestRole();
		System.out.println("Role Name: " + role.getName() + "Gen ID: " + role.getGenId());

		if (role != null) {
			System.out.println("Role Services Role Not Null");

			if (role != null) {

				List<Access> tmpAccess = new ArrayList<Access>();

				List<AccessType> accessTypes = accessTypeServices.getAllAccessType();
				System.out.println("Access Types Size: " + accessTypes.size());
				System.out.println("Role Access Size : " + role.getAccesses().size());

				for (AccessType acType : accessTypes) {

					Access tAccess = new Access();
					tAccess.setNoAccess(1);
					tAccess.setAccessType(acType);

					System.out.println("Set TEMP AccessType ID: " + acType.getId() + " Name: " + acType.getName());

					tmpAccess.add(tAccess);
				}

				for (Access tmpAcs : tmpAccess) {

					System.out.println("Temp Access Type ID: " + tmpAcs.getAccessType().getId());

					for (Access roleAccess : role.getAccesses()) {

						if (roleAccess.getAccessType() != null && tmpAcs.getAccessType() != null) {

							System.out.println("Role Type ID: " + roleAccess.getAccessType().getId()
									+ " & temp Type ID: " + tmpAcs.getAccessType().getId());

							if (roleAccess.getAccessType().getId() == tmpAcs.getAccessType().getId()) {

								System.out.println("ID Pass!!");

								tmpAcs.setAdd(roleAccess.getAdd());
								tmpAcs.setAccessType(roleAccess.getAccessType());
								tmpAcs.setAll(roleAccess.getAdd());
								tmpAcs.setApprove(roleAccess.getApprove());
								tmpAcs.setEdit(roleAccess.getEdit());
								tmpAcs.setNoAccess(roleAccess.getNoAccess());

								tmpAcs.setUpdateApproval(roleAccess.getUpdateApproval());
								tmpAcs.setView(roleAccess.getView());

								tmpAcs.setId(roleAccess.getId());

								tmpAcs.setRole(role);

								if (tmpAcs.getId() > 0) {
									System.out.println("After Set Temp Access ID: " + tmpAcs.getId() + " Role ID: "
											+ tmpAcs.getRole().getId());
								}
							}
						}
					}

				}

				role.setAccesses(null);
				role.setAccesses(tmpAccess);

				for (Access access2 : tmpAccess) {
					System.out.println("TMP Access ID: " + access2.getId());
				}

				for (Access rAc : role.getAccesses()) {

					if (rAc != null) {

						if (rAc.getAccessType() != null) {
							System.out.println("Role Access After Set Temp: " + rAc.getId() + " Type:"
									+ rAc.getAccessType().getName());
						}
					}
				}

			}

			restRole = DozerMapper.parseObject(role, RestRole.class);

			System.out.println("Name: " + restRole.getName() + " Access: Size: " + restRole.getAccesses().size());

		} else {
			System.out.println("Role Services Role is Null");
		}

		return restRole;
	}
	
	private void initRole(Role role) {
		if(role != null) {
			
			for (int i = 0; i < role.getAccesses().size(); i++) {
				
				if(role.getAccesses().get(i) != null) {
					role.getAccesses().get(i).setPublicId(getUnicAccessKey());
					role.getAccesses().get(i).setRole(role);
					role.getAccesses().get(i).setName(role.getName());
					
				}
			}
		}
		
	}

	private String getUnicAccessKey() {
		
		boolean status = true;
		String key = null;
		while (status) {
			key = utilsServices.getUnicId();
			if(!accessServices.isKeyExist(key)) {
				status = false;
			}
		}
		
		return key;
	}
	
	@Override
	public List<Role> getAllRoleWitAccess() {
		
		List<Role> roles = new ArrayList<>();
		
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {

			transaction = session.beginTransaction();

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
			Root<Role> root = criteriaQuery.from(Role.class);
			criteriaQuery.select(root);

			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			Query<Role> query = session.createQuery(criteriaQuery);
			
			transaction.commit();

			roles = query.getResultList();
			
			if(roles != null) {
				for (int i = 0; i < roles.size(); i++) {
					if(roles.get(i) != null) {
						roles.get(i).getAccesses();
					}
				}
			}
			session.clear();
			
			session.close();

		} catch (Exception e) {

			if (transaction != null) {

				transaction.rollback();
				e.printStackTrace();
			}
		}

		
		return roles;
	}
	
	@Override
	public Role getRoleWitAccessById(int id) {
		Role role = null;
		if(id > 0) {
			Session session = sessionFactory.openSession();
			Transaction transaction = null;
			try {

				transaction = session.beginTransaction();
				
				role = session.get(Role.class, id);		
				if(role != null) {
					if(role.getAccesses() != null) {
						role.getAccesses().size();
						log.debug("Access Size "+role.getAccesses().size());

					}
					
				}
				
				transaction.commit();
				session.close();
				
			} catch (Exception e) {

				if (transaction != null) {

					transaction.rollback();
					e.printStackTrace();
				}
			}
		}
		return role;
	}
}
