package org.utils;

import java.util.Calendar;
import java.util.UUID;

public class UnifiedID {
	public static String generateID(String type) {
		Calendar calendar = Calendar.getInstance();
		String id = type + "_" + calendar.get(Calendar.YEAR)
				+ calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE)
				+ calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE)
				+ calendar.get(Calendar.SECOND)+"_"+ UUID.randomUUID();
		return id;
	}
}
