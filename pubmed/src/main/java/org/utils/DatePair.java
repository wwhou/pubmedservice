package org.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class DatePair {

	private String minDate;
	private String maxDate;

	public DatePair() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		maxDate = dateFormat.format(cal.getTime());
		minDate = "1970/01/01";
	}

	//only give start date, the end date will be the current day
	public DatePair(String startDate) {

		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			@SuppressWarnings("unused")
			Date date = dateFormat1.parse(startDate);
			this.minDate = startDate;
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			maxDate = dateFormat.format(cal.getTime());
		} catch (ParseException e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("Couldn't parse date string: " + e.getMessage())
					.build());
		}

	}

	// setting start and end date
	public DatePair(String startDate, String endDate) {

		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
		try {
			@SuppressWarnings("unused")
			Date date = dateFormat1.parse(startDate);
			@SuppressWarnings("unused")
			Date date2 = dateFormat1.parse(endDate);
			this.minDate = startDate;
			this.maxDate = endDate;
		} catch (ParseException e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("Couldn't parse date string: " + e.getMessage())
					.build());
		}
	}

	public String toString() {
		return "&mindate=" + minDate + "&maxdate" + maxDate;
	}

}
