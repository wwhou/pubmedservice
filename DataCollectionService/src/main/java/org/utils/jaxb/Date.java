package org.utils.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="data")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "YEAR", "MONTH", "DAY" })
public class Date {

	@XmlElement(name="YEAR", required=true)
	protected int year;
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	@XmlElement(name="MONTH", required=true)
	protected int month;
	@XmlElement(name="DAY", required=true)
	protected int day;
	
	
}