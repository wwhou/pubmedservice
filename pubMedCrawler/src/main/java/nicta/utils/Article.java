package nicta.utils;

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

	@XmlElement(name = "authors", required = true)
	protected List<Author> authors;

	public List<Author> getAuthors() {
		if (authors == null)
			authors = new ArrayList<Author>();
		return authors;
	}
}
