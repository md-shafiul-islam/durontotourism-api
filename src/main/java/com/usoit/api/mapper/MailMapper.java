package com.usoit.api.mapper;

import com.usoit.api.model.MailVerifiedToken;

public interface MailMapper {

	public String getMailContent(MailVerifiedToken verificationToken);

}
