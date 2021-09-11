package com.usoit.api.servicesimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.Department;
import com.usoit.api.model.PrivacyPolicy;
import com.usoit.api.repository.PrivacyPolicyRepository;
import com.usoit.api.services.PrivacyPolicyServices;

@Service
public class PrivacyPolicyServicesImpl implements PrivacyPolicyServices{

	@Autowired
	private PrivacyPolicyRepository privacyPolicyRepository;

	@Override
	public boolean isKeyExist(String key) {

		if(key != null) {
			PrivacyPolicy option = privacyPolicyRepository.getPrivacyPolicyByPublicId(key);
			
			if(option != null) {
				option = null;
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public List<PrivacyPolicy> getAllPrivacyPolicy() {
		return (List<PrivacyPolicy>) privacyPolicyRepository.findAll();
	}

	@Override
	public long getCount() {
		
		return privacyPolicyRepository.count();
	}

	@Override
	public boolean update(PrivacyPolicy privacyPolicy) {
		
		if (privacyPolicy != null) {
			
			if (privacyPolicy.getPublicId() != null) {
				
				if (privacyPolicy.getPublicId().length() == 105) {
					
					PrivacyPolicy policy = privacyPolicyRepository.getPrivacyPolicyByPublicId(privacyPolicy.getPublicId());
					
					if (policy != null) {
						
						policy.setName(privacyPolicy.getName());
						policy.setDescription(privacyPolicy.getDescription());
						policy.setPublicId(privacyPolicy.getPublicId());
						
						
						privacyPolicyRepository.save(policy);
						
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public PrivacyPolicy getPrivacyPolicyByPubId(String pubId) {
		return privacyPolicyRepository.getPrivacyPolicyByPublicId(pubId);
	}

	@Override
	public boolean save(PrivacyPolicy privacyPolicy) {
		
		if (privacyPolicy != null) {
			
			if (privacyPolicy.getName() != null && privacyPolicy.getDescription() != null) {
				
				privacyPolicy.setId(0);
				privacyPolicy.setDate(new Date());
				
				privacyPolicyRepository.save(privacyPolicy);
				
				if (privacyPolicy.getId() > 0) {
					
					return true;
				}
				
			}
		}
		
		return false;
	}


}
