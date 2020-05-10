package com.usoit.api.services;

import com.usoit.api.data.model.UserTemp;

public interface TempUserServices {

	public UserTemp getUserTempByPubId(String pubId);

	public boolean getRejectedRequest(String publicId);

	public boolean updateUserData(UserTemp tempUser);

	public boolean saveTemUser(UserTemp userTemp);

	public UserTemp getUserTempByPubIdAlive(String pubId);

}
