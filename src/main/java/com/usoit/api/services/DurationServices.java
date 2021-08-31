package com.usoit.api.services;

import java.util.List;

import com.usoit.api.model.Duration;

public interface DurationServices {

	public List<Duration> getAllDurations();
	
	public Duration getDurationById(int id);

	public long getCount();

	public boolean update(Duration duration);

	public boolean save(Duration duration);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
