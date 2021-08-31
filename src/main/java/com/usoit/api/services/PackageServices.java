package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.shared.dto.DtoUpdatePackage;
import com.usoit.api.model.Packages;
import com.usoit.api.model.User;
import com.usoit.api.model.request.ReqPackage;

public interface PackageServices {

	public boolean save(Packages packages);

	public List<Packages> getAllPandingPackages();

	public long getCount();

	public List<Packages> getAllPackages();

	public List<Packages> getAllConfrimPackages();

	public boolean approvePackageById(int id, User cUser);

	public Packages getPackageById(int id);

	public boolean rejectPackageById(int id, User cUser);

	public List<Packages> getAllRejectPackages();

	public boolean updatePack(Packages packages);

	public List<Packages> getAllUpdatePandingApprovPackage();

	public boolean approveUpdatePackageById(int id, User cUser);

	public ReqPackage getRePackageByPublicId(String pId);

	public DtoUpdatePackage getRePackageUpdateByPublicId(String pId);

	public boolean updatePackDto(DtoUpdatePackage packages);

	public Packages getPackageByPID(String id);

	public boolean rejactPackByPublicId(String packId, User user);

	public boolean approvePackByPublicId(String publicId, User user);

	public boolean updatePackRejectByPbID(String publicId, User user);

	public boolean updatePackApproveByPbID(String publicId, User user);
	
	/**
	 * Check Generated id Or Key Exist 
	 * @param key
	 * @return {@link Boolean}
	 */
	public boolean isKeyExist(String key);

}
