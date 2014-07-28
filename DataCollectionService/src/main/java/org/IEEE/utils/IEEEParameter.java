package org.IEEE.utils;

import java.util.Calendar;

public class IEEEParameter {

	private int startYear;
	private int endYear;

	public IEEEParameter(){
		super();
	}
	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		if (startYear > 1900
				&& startYear < Calendar.getInstance().get(Calendar.YEAR))
			this.startYear = startYear;
		else
			throw new IllegalArgumentException("Exceed the year range!!");
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		if (endYear > startYear && endYear > 1900
				&& endYear < Calendar.getInstance().get(Calendar.YEAR))
			this.endYear = endYear;
		else
			throw new IllegalArgumentException("Exceed the year range!!");
	}

}
