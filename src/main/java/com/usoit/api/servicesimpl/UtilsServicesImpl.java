package com.usoit.api.servicesimpl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.usoit.api.services.UtilsServices;

@Service
public class UtilsServicesImpl implements UtilsServices {

	@Override
	public String getUnicId() {

		UUID uuid = UUID.randomUUID();

		String unicId = "";

		unicId = uuid.toString();

		System.out.println("Befor Replace " + unicId);
		unicId = unicId.replace("-", "");

		System.out.println("After Replace " + unicId + " And Size " + Integer.toString(unicId.length()));

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		String dateTime = Integer.toString(calendar.get(Calendar.YEAR)) + Integer.toString(calendar.get(Calendar.DATE))
				+ Integer.toString(calendar.get(Calendar.HOUR_OF_DAY)) + Integer.toString(calendar.get(Calendar.MINUTE))
				+ Integer.toString(calendar.get(Calendar.SECOND))
				+ Integer.toString(calendar.get(Calendar.MILLISECOND));

		unicId = unicId + dateTime;

		return unicId;
	}
}
