package org.IEEE.utils;

import java.util.Calendar;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.utils.Parameter;

public class IEEEParameter extends Parameter {

	private int startYear = 0;
	private int endYear = 0;

	public IEEEParameter() {
		super();
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
	
		if (startYear != 0) {
			if (startYear >= 1900
					&& startYear <= Calendar.getInstance().get(Calendar.YEAR))
				this.startYear = startYear;
			else
				throw new IllegalArgumentException("Exceed the year range!!");
		}
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		if (endYear != 0) {
			if (endYear > startYear && endYear >= 1900
					&& endYear <= Calendar.getInstance().get(Calendar.YEAR))
				this.endYear = endYear;
			else
				throw new IllegalArgumentException("Exceed the year range!!");
		}
	}

}
