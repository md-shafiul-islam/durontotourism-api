package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.TermsAndConditions;

public interface TermsAndConditionsServices {

	public List<TermsAndConditions> getAllTermAndConds();

	public long getCount();

	public boolean save(TermsAndConditions termAndConditions);

	public TermsAndConditions getTermAndCondsByPubId(String pubId);

	public boolean update(TermsAndConditions termAndConditions);

}
