package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.data.model.Duration;
import com.usoit.api.repository.DurationRespository;
import com.usoit.api.services.DurationServices;

@Service
public class DurationServicesImpl implements DurationServices{

	@Autowired
	private DurationRespository durationRespository;
	
	@Override
	public List<Duration> getAllDurations() {
		
		return (List<Duration>) durationRespository.findAll();
	}
	
	@Override
	public Duration getDurationById(int id) {
		
		if (id > 0) {
			Optional<Duration> optional = durationRespository.findById(id);
			
			if (optional != null) {
				
				return optional.get();
			}
		}
		
		return null;
	}

	@Override
	public boolean save(Duration duration) {
		
		if (duration != null) {
			
			if ( 0 >= duration.getId()) {
				durationRespository.save(duration);
				
				if (duration.getId() > 0) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean update(Duration duration) {
		
		if (duration != null) {
			
			if (duration.getId() > 0) {
				durationRespository.save(duration);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public long getCount() {
		
		return durationRespository.count();
	}

}
