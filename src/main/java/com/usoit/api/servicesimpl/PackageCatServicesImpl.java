package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.PackageCat;
import com.usoit.api.repository.PackCatRepository;
import com.usoit.api.services.PackageCatServices;

import ch.qos.logback.core.joran.conditional.IfAction;

@Service
public class PackageCatServicesImpl implements PackageCatServices{

	@Autowired
	private PackCatRepository packCatRepository;
	
	@Override
	public List<PackageCat> getAllPackCats() {
		return (List<PackageCat>) packCatRepository.findAll();
	}

	@Override
	public long getCount() {
		return packCatRepository.count();
	}
	
	@Override
	public PackageCat getPackCatById(int id) {
		
		if (id > 0) {
			
			Optional<PackageCat> optional = packCatRepository.findById(id);
			
			if (optional != null) {
				
				return optional.get();
			}
			
		}
		
		return null;
	}
	
	@Override
	public boolean save(PackageCat packageCat) {
		
		if (0 >= packageCat.getId()) {
			
			packCatRepository.save(packageCat);
			
			if (packageCat.getId() > 0) {
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean update(PackageCat packageCat) {
		
		if (packageCat.getId() > 0) {
			
			packCatRepository.save(packageCat);
			return true;
		}
		
		return false;
	}

}
