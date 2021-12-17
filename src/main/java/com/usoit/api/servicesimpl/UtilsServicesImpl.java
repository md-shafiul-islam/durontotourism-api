package com.usoit.api.servicesimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import com.usoit.api.services.UtilsServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UtilsServicesImpl implements UtilsServices {

	@Override
	public String getUnicId() {

		UUID uuid = UUID.randomUUID();

		String unicId = "";

		unicId = uuid.toString();

		log.info("Befor Replace " + unicId);
		unicId = unicId.replace("-", "");

		Calendar calendar = getCalender();
				

		String dateTime = Integer.toString(calendar.get(Calendar.YEAR)) + Integer.toString(calendar.get(Calendar.DATE))
				+ Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + Integer.toString(calendar.get(Calendar.MINUTE))
				+ Integer.toString(calendar.get(Calendar.SECOND))
				+ Integer.toString(calendar.get(Calendar.MILLISECOND));

		unicId = unicId + dateTime;

		return unicId;
	}
	
	@Override
	public String getGeneralPublicId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	private Calendar getCalender() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);
		return calendar;
	}
	
	@Override
	public String getCustomerGenaretedId() {
		
		// DTC-00-0000-0000
		// Customer Gen ID DTA(MN)(YN)(RND4N)
		// Company name 2 digit, User Category 1 digit, Month 2 Digit, Year 4 Digit,
		// Random number 4 digit
		
		String cId = "DTC"+getAfterGenID();
		return cId;
	}
	
	private String getAfterGenID() {
		
		Calendar calendar = getCalender();		
		int mnt = calendar.get(Calendar.MONTH)+1;
				
		String month =  mnt < 10 ? "0"+Integer.toString(mnt) : Integer.toString(mnt);	
		return month+calendar.get(Calendar.YEAR)+getRandomNumber(4);
	}
	
	private String getRandomNumber(int i) {
	
		return RandomStringUtils.random(i, false, true);
	}

	@Override
	public String getCustomerPublicId() {
		return getUnicId();
	}
	
	@Override
	public String getAgentGenID() {
		String cId = "DTA"+getAfterGenID();
		return cId;
	}
	
	@Override
	public String getMailVerifiedToken() {
		String key = RandomStringUtils.random(55, true, true);
		key = key+"-"+RandomStringUtils.random(6, false, true);
		return key;
	}
	
	@Override
	public String getMailVerDegitCode() {
		
		return RandomStringUtils.random(6, false, true);
	}
	
	@Override
	public String getPhoneOtpCode() {
		return RandomStringUtils.random(6, false, true);
	}
	
	@Override
	public String getSubAgentGenID() {
		String subAgentId = "DTSA"+getAfterGenID();
		return subAgentId;
	}
}
