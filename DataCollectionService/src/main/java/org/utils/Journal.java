package org.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Journal")
@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "", propOrder = { "title", "volume", "issn"})
public class Journal {
	@XmlElement(name = "journalTitle", required = true)
	protected String title;
	@XmlElement(name="ISSN", required=false)
	protected String issn;
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
	}
	//do I need to put pubDate here, or just in the article class
	@XmlElement(name = "pubDate", required = true)
	protected Date pubDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	@XmlElement(name = "volume", required = false)
	protected String volume;
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	//@XmlElement(name="pubDate", required)
}
