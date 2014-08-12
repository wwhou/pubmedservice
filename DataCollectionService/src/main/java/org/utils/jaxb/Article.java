package org.utils.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "articleMeta", "authors" })
@XmlRootElement(name = "article")
public class Article {

	@XmlElement(name = "articleMeta", required = true)
	protected ArticleMeta articleMeta;

	public ArticleMeta getArticleMeta() {
		return articleMeta;
	}

	public void setArticleMeta(ArticleMeta articleMeta) {
		this.articleMeta = articleMeta;
	}

	@XmlElement(name = "people", required = true)
	protected List<Person> people;

	public List<Person> getPeople() {
		if (people == null)
			people = new ArrayList<Person>();
		return people;
	}
}
