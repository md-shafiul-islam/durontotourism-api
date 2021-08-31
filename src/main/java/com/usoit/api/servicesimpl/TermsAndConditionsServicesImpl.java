package com.usoit.api.servicesimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.TermsAndConditions;
import com.usoit.api.repository.TermsAndConditionsRepository;
import com.usoit.api.services.TermsAndConditionsServices;

@Service
public class TermsAndConditionsServicesImpl implements TermsAndConditionsServices{
	
	@Autowired
	private TermsAndConditionsRepository termsAndConditionsRepository;
	
	@Override
	public List<TermsAndConditions> getAllTermAndConds() {
		return (List<TermsAndConditions>) termsAndConditionsRepository.findAll();
	}
	
	@Override
	public boolean isKeyExist(String key) {
		
		if(key != null) {
			TermsAndConditions conditions = termsAndConditionsRepository.getTermsAndConditionsByPublicId(key);
			
			if(conditions != null) {
				conditions = null;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean save(TermsAndConditions termAndConditions) {
		
		if (termAndConditions != null) {
			
			if (termAndConditions.getName() != null && termAndConditions.getDiscription() != null && 0 >= termAndConditions.getId()) {
				
				termAndConditions.setDate(new Date());
				
				termsAndConditionsRepository.save(termAndConditions);
				
				if (termAndConditions.getId() > 0) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean update(TermsAndConditions termAndConditions) {
		
		if (termAndConditions != null) {
			
			if (termAndConditions.getPublicId() != null) {
				
				if (termAndConditions.getPublicId().length() == 105) {
					
					TermsAndConditions andConditions = termsAndConditionsRepository.getTermsAndConditionsByPublicId(termAndConditions.getPublicId());
					
					if (andConditions != null) {
						
						andConditions.setDiscription(termAndConditions.getDiscription());
						andConditions.setName(termAndConditions.getName());
						andConditions.setPublicId(termAndConditions.getPublicId());
						
						if (andConditions.getId() > 0) {
							termsAndConditionsRepository.save(andConditions);
							return true;
						}else {
							return false;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public TermsAndConditions getTermAndCondsByPubId(String pubId) {
		
		if (pubId != null) {
			
			if (pubId.length() == 105) {
				
				return termsAndConditionsRepository.getTermsAndConditionsByPublicId(pubId);
			}
		}
		
		return null;
	}
	
	@Override
	public long getCount() {
		return termsAndConditionsRepository.count();
	}

	
	
}
