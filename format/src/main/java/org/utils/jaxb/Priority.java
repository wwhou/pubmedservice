package org.utils.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "priority")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "countryCode", "documentDate",
		"documentNumber", "documentKind" })
public class Priority {
	@XmlElement(name = "countryCode", required = true)
	String countryCode;
	@XmlElement(name = "documentDate", required = true)
	String documentDate;
	@XmlElement(name = "documentNumber", required = true)
	String documentNumber;
	@XmlElement(name="documentKind", required=true)
	String documentKind;
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String contryCode) {
		this.countryCode = contryCode;
	}
	public String getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getDocumentKind() {
		return documentKind;
	}
	public void setDocumentKind(String documentKind) {
		this.documentKind = documentKind;
	}
}
