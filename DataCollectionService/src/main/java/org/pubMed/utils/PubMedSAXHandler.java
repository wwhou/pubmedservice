package org.pubMed.utils;

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
import org.utils.jaxb.Date;
import org.utils.jaxb.ISBN;
import org.utils.jaxb.Journal;
import org.utils.jaxb.Month;
import org.utils.jaxb.Publisher;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PubMedSAXHandler extends DefaultHandler {

	private Collection<ArticleMeta> articleMetas = null;
	private Collection<Article> articles = null;
	private Collection<Author> authors = null;
	private ArticleMeta articleMeta = null;
	private Article article = null;
	private Author author = null;
	private String tmpValue;
	private Journal journal = null;
	private Book book = null;
	private String abstractText;
	private boolean articleDateFlag = false;
	private boolean journalDateFlag = false;
	private Date articlePubDate;
	private Publisher publisher = null;
	private ArticleIdList articleIdList;
	private Date journalPubDate;

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
		case "pubmedarticle":
			if (articleMetas == null)
				articleMetas = new ArrayList<ArticleMeta>();
			if (authors == null)
				authors = new ArrayList<Author>();
			journal = new Journal();
			articleMeta = new ArticleMeta();
			if (articles == null)
				articles = new ArrayList<Article>();
			article = new Article();
			break;
		case "pubmedbookarticle":
			if (articleMetas == null)
				articleMetas = new ArrayList<ArticleMeta>();
			if (authors == null)
				authors = new ArrayList<Author>();
			book = new Book();

			articleMeta = new ArticleMeta();
			if (articles == null)
				articles = new ArrayList<Article>();
			article = new Article();
			break;
		case "author":
			author = new Author();
			break;
		case "abstracttext":
			String label = attributes.getValue("NlmCategory");
			if (label != null && !label.equals("UNLABELLED"))
				abstractText += label;
			break;
		case "pubmedpubdate":
			if (attributes.getValue("PubStatus").equals("pubmed")) {
				articleDateFlag = true;
				articlePubDate = new Date();
			}
			break;
		case "pubdate":
			journalDateFlag = true;
			journalPubDate = new Date();
			break;
		case "publisher":
			publisher = new Publisher();
			break;
		case "articleidlist":
			articleIdList = new ArticleIdList();
			break;

		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) {

		switch (qName.toLowerCase()) {
		case "pubmedarticle":
			Calendar calendar = Calendar.getInstance();
			String id = calendar.get(Calendar.YEAR) + "-"
					+ calendar.get(Calendar.MONTH) + "-"
					+ calendar.get(Calendar.DATE) + "-"
					+ calendar.get(Calendar.HOUR) + "-"
					+ calendar.get(Calendar.MINUTE) + "-"
					+ calendar.get(Calendar.SECOND) + UUID.randomUUID();
			articleMeta.setId(id);
			articleMetas.add(articleMeta);
			article.setArticleMeta(articleMeta);
			article.getAuthors().add(author);
			articles.add(article);
			break;
		case "pubmedbookarticle":
			articleMetas.add(articleMeta);
			article.setArticleMeta(articleMeta);
			article.getAuthors().add(author);
			articles.add(article);
			break;
		case "journal":
			articleMeta.setArticleType(journal);
			break;

		case "lastname":
			author.setLastName(tmpValue);
			break;
		case "forename":
			author.setFirstName(tmpValue);
			break;
		case "affiliation":
			author.setAffiliation(tmpValue);
			break;
		case "author":
			authors.add(author);
//			Calendar calendar1 = Calendar.getInstance();
//			String id1 = calendar1.get(Calendar.YEAR) + "-"
//					+ calendar1.get(Calendar.MONTH) + "-"
//					+ calendar1.get(Calendar.DATE) + "-"
//					+ calendar1.get(Calendar.HOUR) + "-"
//					+ calendar1.get(Calendar.MINUTE) + "-"
//					+ calendar1.get(Calendar.SECOND) + UUID.randomUUID();
//			author.setId();
			break;
		case "title":
			if (journal != null)
				journal.setTitle(tmpValue);
			break;
		case "issn":
			journal.setIssn(tmpValue);
			break;
		case "medlinepgn":
			if (!tmpValue.isEmpty())
				articleMeta.setPagination(tmpValue);
			break;
		case "articletitle":
			articleMeta.setTitle(tmpValue);
			break;
		case "abstracttext":
			abstractText += tmpValue + "/n";
			break;
		case "abstract":
			articleMeta.setArticleAbstract(abstractText);
			break;
		case "year":
			if (journalDateFlag)
				journalPubDate.setYear(Integer.parseInt(tmpValue));
			if (articleDateFlag)
				articlePubDate.setYear(Integer.parseInt(tmpValue));
			break;
		case "month":
			if (journalDateFlag) {
				journalPubDate.setMonth(Month.compare(tmpValue));
				journalDateFlag = false;
				journal.setPubDate(journalPubDate);
			}
			if (articleDateFlag)
				articlePubDate.setMonth(Integer.parseInt(tmpValue));
			break;
		case "day":
			if (articleDateFlag) {
				articlePubDate.setDay(Integer.parseInt(tmpValue));
				articleMeta.setPubDate(articlePubDate);
				articleDateFlag = false;
			}
			break;
		case "pagination":
			articleMeta.setPagination(tmpValue);
			break;
		case "booktitle":
			book.setTitle(tmpValue);
			break;
		case "publisher":
			book.setPublisher(tmpValue);
			break;
		case "publishername":
			publisher.setPublisherName(tmpValue);
			break;
		case "pubisherlocation":
			publisher.setPublisherLocation(tmpValue);
			break;
		case "isbn":
			ISBN isbn = new ISBN();
			isbn.setvalue(tmpValue);
			book.getIsbn().add(isbn);
			break;
		case "articleid":
			ArticleId articleId = new ArticleId();
			articleId.setContent(tmpValue);
			articleIdList.getArticleId().add(articleId);
			break;
		case "articleidlist":
			articleMeta.setArticleIdList(articleIdList);
			break;
		}
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {

		tmpValue = new String(ac, i, j);

	}

}
