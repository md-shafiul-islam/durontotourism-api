package com.usoit.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.usoit.api.data.model.Packages;

public interface PackageRepository extends CrudRepository<Packages, Integer>{

	public Packages getPackageByPublicId(String publicId);

}
