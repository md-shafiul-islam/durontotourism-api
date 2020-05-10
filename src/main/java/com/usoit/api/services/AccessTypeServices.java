package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.AccessType;

public interface AccessTypeServices {

	public List<AccessType> getAllAccessType();

	public long getCount();

}
