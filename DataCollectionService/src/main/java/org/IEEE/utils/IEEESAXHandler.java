package org.IEEE.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import org.utils.jaxb.Article;
import org.utils.jaxb.ArticleId;
import org.utils.jaxb.ArticleIdList;
import org.utils.jaxb.ArticleMeta;
import org.utils.jaxb.Author;
import org.utils.jaxb.Book;
import org.utils.jaxb.Conference;
import org.utils.jaxb.Date;
import org.utils.jaxb.Journal;
import org.utils.jaxb.Keyword;
import org.utils.jaxb.KeywordList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IEEESAXHandler extends DefaultHandler {

	private Collection<ArticleMeta> articleMetas = null;
	private Collection<Article> articles = null;
	private Collection<Author> authors = null;
	private ArticleMeta articleMeta = null;
	private Article article = null;
	private String tmpValue;
	private Journal journal = null;
	private Conference conference = null;
	private Book book = null;
	private Date articlePubDate;
	private ArticleIdList articleIdList;
	private String pubTitle;
	private KeywordList keywordList = null;
	private String authorString = null;
	private String startPage = null;

	public Collection<ArticleMeta> getArticleMetas() {
		return articleMetas;
	}

	public Collection<Article> getArtciles() {
		return articles;
	}

	public Collection<Author> getAuthors() {
		return authors;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		switch (qName.toLowerCase()) {
		case "document":
			if (articleMetas == null)
				articleMetas = new ArrayList<ArticleMeta>();
			if (authors == null)
				authors = new ArrayList<Author>();
			articleMeta = new ArticleMeta();
			if (articles == null)
				articles = new ArrayList<Article>();
			article = new Article();
			articleIdList = new ArticleIdList();
			articlePubDate = new Date();
			break;
		case "controlledterms":
			keywordList = new KeywordList();
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		switch (qName.toLowerCase()) {
		case "documemt":
			Calendar calendar=Calendar.getInstance();		
			String id=calendar.get(Calendar.YEAR)+"-"+
					calendar.get(Calendar.MONTH)+"-"+
					calendar.get(Calendar.DATE)+"-"+
					calendar.get(Calendar.HOUR)+"-"+
					calendar.get(Calendar.MINUTE)+"-"+
					calendar.get(Calendar.SECOND)+UUID.randomUUID();
			articleMeta.setId(id);
			if (conference != null) {
				conference.setTitle(pubTitle);
				conference.setPubDate(articlePubDate);
			} else if (book != null) {
				book.setTitle(pubTitle);
				book.setPubDate(articlePubDate);
			} else if (journal != null) {
				journal.setTitle(pubTitle);
				journal.setPubDate(articlePubDate);
			}
			articleMetas.add(articleMeta);
			article.setArticleMeta(articleMeta);
			articles.add(article);
			break;
		case "title":
			articleMeta.setTitle(tmpValue);
			break;
		case "pubtitle":
			pubTitle = tmpValue;
			break;
		case "pubtype":
			String value = tmpValue.toLowerCase();
			if (value.contains("conference")) {
				conference = new Conference();
			} else if (value.contains("journal")) {
				journal = new Journal();
			} else if (value.contains("book")) {
				book = new Book();
			}
			break;
		case "doi":
			ArticleId articleId = new ArticleId();
			articleId.setIdType("DOI");
			articleId.setContent(tmpValue);
			articleIdList.getArticleId().add(articleId);
			break;
		case "publicationid":
			ArticleId pubId = new ArticleId();
			pubId.setIdType("IEEEId");
			pubId.setContent(tmpValue);
			articleIdList.getArticleId().add(pubId);
			break;
		case "isbn":
			ArticleId isbn = new ArticleId();
			isbn.setIdType("ISBN");
			isbn.setContent(tmpValue);
			articleIdList.getArticleId().add(isbn);
			break;
		case "issn":
			ArticleId issn = new ArticleId();
			issn.setIdType("ISSN");
			issn.setContent(tmpValue);
			if (journal != null) {
				journal.setIssn(tmpValue);
			}
			articleIdList.getArticleId().add(issn);
			break;
		case "abstract":
			articleMeta.setArticleAbstract(tmpValue);
			break;
		case "publisher":
			if (conference != null) {
				conference.setPublisher(tmpValue);
			} else if (book != null) {
				book.setPublisher(tmpValue);
			}
			break;
		case "py":
			articlePubDate.setYear(Integer.parseInt(tmpValue));
			break;
		case "spage":
			startPage = tmpValue;
			break;
		case "epage":
			if (startPage != null) {
				articleMeta.setPagination(startPage + "-" + tmpValue);
			}
			
			break;
		case "volume":
			if (conference != null) {
				conference.setVolume(tmpValue);
			} else if (journal != null) {
				journal.setVolume(tmpValue);

			}
			break;
		case "term":
			if (keywordList != null) {
				Keyword keyword = new Keyword();
				keyword.setContent(tmpValue);
				keywordList.getKeyword().add(keyword);
			}
			break;
		case "thesaurusterms":
			if (keywordList != null)
				articleMeta.setKeywords(keywordList);
			break;
		case "authors":
			authorString = tmpValue;
			break;
		case "affiliations":
			if (authorString != null) {
				String[] authorNames = authorString.split(";");
				for (String authorName : authorNames) {
					Author author = new Author();
					String[] namePair = authorName.trim().split(" ");
					String name0 = namePair[0];
					if (namePair.length == 3) {
						author.setMiddleName(namePair[2]);
					}
					if (name0.contains(",")) {
						author.setLastName(name0);
						author.setFirstName(namePair[namePair.length - 1]);
					} else {
						author.setFirstName(name0);
						author.setLastName(namePair[namePair.length - 1]);
					}
					author.setAffiliation(tmpValue);
					article.getAuthors().add(author);
					authors.add(author);
				}
			}
			break;
		}
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {

		tmpValue = new String(ac, i, j);

	}
}