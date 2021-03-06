package org.utils.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "title", "publisher", "isbnList" })
@XmlRootElement(name = "book")
public class Book {

	@XmlElement(name = "bookTitle", required = true)
	protected String title;
	@XmlElement(name = "publisher", required = false)
	protected Publisher publisher;

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<ISBN> getIsbn() {
		if(isbn==null)
			isbn=new ArrayList<ISBN>();
		return isbn;
	}


	@XmlElement(name = "ISBN", required = false)
	protected List<ISBN>isbn;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// still need to consider if the pubDate is necessary to put under book or
	// just in the Article class
	@XmlElement(name = "pubDate", required = true)
	protected Date pubDate;

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

}
