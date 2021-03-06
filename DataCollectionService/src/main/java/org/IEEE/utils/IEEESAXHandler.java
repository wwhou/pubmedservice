package org.IEEE.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import org.utils.UnifiedID;
import org.utils.jaxb.Affiliation;
import org.utils.jaxb.Article;
import org.utils.jaxb.ArticleId;
import org.utils.jaxb.ArticleIdList;
import org.utils.jaxb.ArticleMeta;
import org.utils.jaxb.Book;
import org.utils.jaxb.Conference;
import org.utils.jaxb.Date;
import org.utils.jaxb.Journal;
import org.utils.jaxb.Keyword;
import org.utils.jaxb.KeywordList;
import org.utils.jaxb.Person;
import org.utils.jaxb.Publisher;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IEEESAXHandler extends DefaultHandler {

	private Collection<ArticleMeta> articleMetas = null;
	private Collection<Article> articles = null;
	private Collection<Person> authors = null;
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
	private boolean affiFlag = false;

	public Collection<ArticleMeta> getArticleMetas() {
		return articleMetas;
	}

	public Collection<Article> getArtciles() {
		return articles;
	}

	public Collection<Person> getAuthors() {
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
				authors = new ArrayList<Person>();
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
		case "document":
			articleMeta.setId(UnifiedID.generateID("IE"));
			if (conference != null) {
				conference.setTitle(pubTitle);
				conference.setPubDate(articlePubDate);
				articleMeta.setArticleType(conference);
			} else if (book != null) {
				book.setTitle(pubTitle);
				book.setPubDate(articlePubDate);
				articleMeta.setArticleType(book);
			} else if (journal != null) {
				journal.setTitle(pubTitle);
				journal.setPubDate(articlePubDate);
				articleMeta.setArticleType(journal);
			}
			articleMeta.setPubDate(articlePubDate);
			articleMetas.add(articleMeta);
			article.setArticleMeta(articleMeta);
			if (authorString != null && !affiFlag) {
				if (authorString.contains(";")) {
					String[] authorNames = authorString.split(";");
					for (String authorName : authorNames) {
						Person author = new Person();
						author.setType("author");
						author.setId(UnifiedID.generateID("AU"));
						String[] namePair = authorName.trim().split(" ");
						String name0 = namePair[0];
						if (namePair.length == 3) {
							author.setMiddleName(namePair[2]);
						}
						if (name0.contains(",")) {
							author.setLastName(name0.replace(",", "").trim());
							author.setFirstName(namePair[namePair.length - 1]);
						} else {
							author.setFirstName(name0);
							author.setLastName(namePair[namePair.length - 1]);
						}
						article.getPeople().add(author);
						authors.add(author);
					}
				} else {
					Person author = new Person();
					String[] namePair = authorString.split(" ");
					String name0 = namePair[0];
					if (namePair.length == 3) {
						author.setMiddleName(namePair[2]);
					}
					if (name0.contains(",")) {
						author.setLastName(name0.replace(",", "").trim());
						author.setFirstName(namePair[namePair.length - 1]);
					} else {
						author.setFirstName(name0);
						author.setLastName(namePair[namePair.length - 1]);
					}
					article.getPeople().add(author);
					authors.add(author);
				}
				affiFlag = false;
			} else {
				affiFlag = false;
			}
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
			Publisher publisher=new Publisher();
			publisher.setPublisherName(tmpValue);
			if (conference != null) {
				conference.setPublisher(publisher);
			} else if (book != null) {			
				book.setPublisher(publisher);
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
			affiFlag = true;
			if (authorString != null) {
				if (authorString.contains(";")) {
					String[] authorNames = authorString.split(";");
					for (String authorName : authorNames) {
						Person author = new Person();
						author.setFullName(authorName);
						author.setType("author");
						author.setId(UnifiedID.generateID("AU"));
						String[] namePair = authorName.trim().split(" ");
						String name0 = namePair[0];
						if (namePair.length == 3) {
							author.setMiddleName(namePair[2]);
						}
						if (name0.contains(",")) {
							author.setLastName(name0.replace(",", "").trim());
							author.setFirstName(namePair[namePair.length - 1]);
						} else {
							author.setFirstName(name0);
							author.setLastName(namePair[namePair.length - 1]);
						}
						Affiliation affiliation=new Affiliation();
						affiliation.setContent(tmpValue);
						author.getAffiliation().add(affiliation);
						article.getPeople().add(author);
						authors.add(author);
					}
				} else {
					Person author = new Person();
					String[] namePair = authorString.split(" ");
					String name0 = namePair[0];
					if (namePair.length == 3) {
						author.setMiddleName(namePair[2]);
					}
					if (name0.contains(",")) {
						author.setLastName(name0.replace(",", "").trim());
						author.setFirstName(namePair[namePair.length - 1]);
					} else {
						author.setFirstName(name0);
						author.setLastName(namePair[namePair.length - 1]);
					}
					Affiliation affiliation=new Affiliation();
					affiliation.setContent(tmpValue);
					author.getAffiliation().add(affiliation);
					article.getPeople().add(author);
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
