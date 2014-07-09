package nicta.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "title", "articleAbstract", "keywords", "pagination",
		"articleType", "pubDate", "articleIdList" })
@XmlRootElement(name = "articleMeta")
public class ArticleMeta {

	@XmlElement(name = "articleTitle", required = true)
	protected String title;
	@XmlElement(name = "abstract", required = false)
	protected String articleAbstract;
	// here has three types Book, Conference and Journal
	@XmlElements(value = {
			@XmlElement(name = "book", required = true, type = Book.class),
			@XmlElement(name = "journal", required = true, type = Journal.class),
			@XmlElement(name = "conference", required = true, type = Conference.class) })
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
	protected List<Keyword> keywords;
	@XmlElement(name = "articleIdList", required = false)
	protected List<ArticleId> articleId;
	@XmlElement(name = "pubDate", required = true)
	protected Date pubDate;

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public List<ArticleId> getArticleId() {
		if (articleId == null)
			articleId = new ArrayList<ArticleId>();
		return articleId;
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
	public List<Keyword> getKeywods() {
		if (keywords == null)
			keywords = new ArrayList<Keyword>();
		return keywords;
	}

}
