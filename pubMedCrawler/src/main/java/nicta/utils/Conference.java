package nicta.utils;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "conference")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "title", "city", "country" })
public class Conference {

	@XmlElement(name = "conferenceName", required = true)
	protected String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement(name = "city", required = true)
	protected String city;
	@XmlElement(name = "country", required = true)
	protected String country;
	@XmlElement(name = "pubDate", required = true)
	protected Date pubDate;
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
}
