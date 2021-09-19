package com.usoit.api.servicesimpl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.model.TransactionType;
import com.usoit.api.services.KeyOrIdGenerator;
import com.usoit.api.services.TransactionTypeServices;

@Service
public class KeyOrIdGeneratorImpl implements KeyOrIdGenerator {

	@Autowired
	private TransactionTypeServices codeTypeServices;

	@Override
	public String getWalletWithDrawKeyOrGenId(String paymentType, String countryCode) {
		
		//DT_WIW_BD_20210912_IB_88556677
		String genId = "DTWIW";
		if(countryCode != null) {
			genId+= countryCode;
		}
		
		
		String getTypeCode = getTypeCode(paymentType);
		
		String dateTime = getCurrentDateTime();
		String randomNum = getRandomNumber(8);
		
		genId += dateTime;
		genId += getTypeCode;
		genId += randomNum;
		
		return genId;
	}

	private String getRandomNumber(int i) {
		
		return RandomStringUtils.randomNumeric(8);
	}

	private String getCurrentDateTime() {
		//20210912
		String dateString = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		dateString += intToDateString(calendar.get(Calendar.YEAR));
		dateString += intToDateString(calendar.get(Calendar.MONTH));
		dateString += intToDateString(calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("Date "+ dateString);
		return dateString;
	}

	private String intToDateString(int i) {	
		if(10 > i) {
			return "0"+Integer.toString(i);
		}
		return Integer.toString(i);
	}

	private String getTypeCode(String codeKey) {
		String code = "";
		if(codeKey != null) {
			TransactionType codeType = codeTypeServices.getCodeByKey(codeKey);
			
			if(codeType != null) {
				code = codeType.getCode();
			}
		}
		
		return code; 
	}

}
