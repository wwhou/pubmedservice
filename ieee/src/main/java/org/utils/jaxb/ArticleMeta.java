package org.utils.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "title", "articleAbstract", "keywords",
		"pagination", "articleType", "pubDate", "articleIdList" })
@XmlRootElement(name = "articleMeta")
public class ArticleMeta {

	@XmlElement(name = "id", required = true)
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "title", required = true)
	protected String title;
	@XmlElement(name = "articleAbstract", required = false)
	protected String articleAbstract;
	// here has three types Book, Conference and Journal
	@XmlElements(value = {
			@XmlElement(name = "book", required = false, type = Book.class),
			@XmlElement(name = "journal", required = false, type = Journal.class),
			@XmlElement(name = "conference", required = false, type = Conference.class),
			@XmlElement(name = "patent", required = false, type = Patent.class) })
	private Object articleType;
	@XmlElement(name = "pagination", required = false)
	protected String pagination;

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	@XmlElement(name = "keywords", required = false)
	protected KeywordList keywords;
	@XmlElement(name = "articleIdList", required = false)
	protected ArticleIdList articleIdList;

	public ArticleIdList getArticleIdList() {
		return articleIdList;
	}

	public void setArticleIdList(ArticleIdList articleIdList) {
		this.articleIdList = articleIdList;
	}

	@XmlElement(name = "pubDate", required = true)
	protected Date pubDate;

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleAbstract() {
		return articleAbstract;
	}

	public void setArticleAbstract(String articleAbstract) {
		this.articleAbstract = articleAbstract;
	}

	public Object getArticleType() {
		return articleType;
	}

	public void setArticleType(Object articleType) {
		this.articleType = articleType;
	}

	public KeywordList getKeywords() {
		return keywords;
	}

	public void setKeywords(KeywordList keywords) {
		this.keywords = keywords;
	}

}
