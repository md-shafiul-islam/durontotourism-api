package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Duration;

public interface DurationServices {

	public List<Duration> getAllDurations();
	
	public Duration getDurationById(int id);

	public long getCount();

	public boolean update(Duration duration);

	public boolean save(Duration duration);

}
