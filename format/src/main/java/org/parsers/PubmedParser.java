package org.parsers;

import java.util.ArrayList;
import java.util.Collection;

import org.utils.UnifiedID;
import org.utils.jaxb.Affiliation;
import org.utils.jaxb.Article;
import org.utils.jaxb.ArticleId;
import org.utils.jaxb.ArticleIdList;
import org.utils.jaxb.ArticleMeta;
import org.utils.jaxb.Book;
import org.utils.jaxb.Date;
import org.utils.jaxb.ISBN;
import org.utils.jaxb.Journal;
import org.utils.jaxb.Month;
import org.utils.jaxb.Person;
import org.utils.jaxb.Publisher;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PubmedParser extends DefaultHandler {
	private Collection<ArticleMeta> articleMetas = null;
	private Collection<Article> articles = null;
	private Collection<Person> authors = null;
	private ArticleMeta articleMeta = null;
	private Article article = null;
	private Person author = null;
	private String tmpValue;
	private Journal journal = null;
	private Book book = null;
	private String abstractText = "";
	private boolean articleDateFlag = false;
	private boolean journalDateFlag = false;
	private Date articlePubDate;
	private Publisher publisher = null;
	private ArticleIdList articleIdList;
	private Date journalPubDate;
	private ArticleId articleId;

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
		case "pubmedarticle":
			if (articleMetas == null)
				articleMetas = new ArrayList<ArticleMeta>();
			if (authors == null)
				authors = new ArrayList<Person>();
			journal = new Journal();
			articleMeta = new ArticleMeta();
			if (articles == null)
				articles = new ArrayList<Article>();
			article = new Article();
			articleMeta.setId(UnifiedID.generateID("PM"));
			break;
		case "pubmedbookarticle":
			if (articleMetas == null)
				articleMetas = new ArrayList<ArticleMeta>();
			if (authors == null)
				authors = new ArrayList<Person>();
			book = new Book();
			articleMeta.setId(UnifiedID.generateID("PM"));
			articleMeta = new ArticleMeta();
			if (articles == null)
				articles = new ArrayList<Article>();
			article = new Article();
			break;
		case "author":
			author = new Person();
			author.setId(UnifiedID.generateID("AU"));
			author.setType("author");
			break;
		case "abstracttext":
			String label = attributes.getValue("NlmCategory");
			if (label != null && !label.equals("UNLABELLED"))
				abstractText += label + " ";
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
		case "articleid":
			articleId = new ArticleId();
			articleId.setIdType(attributes.getValue("IdType"));
			break;

		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) {

		switch (qName.toLowerCase()) {
		case "pubmedarticle":
			articleMetas.add(articleMeta);
			article.setArticleMeta(articleMeta);
			article.getPeople().add(author);
			articles.add(article);

			break;
		case "pubmedbookarticle":
			if (publisher != null)
				book.setPublisher(publisher);
			articleMeta.setArticleType(book);
			articleMetas.add(articleMeta);
			article.setArticleMeta(articleMeta);
			article.getPeople().add(author);
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
			Affiliation affiliation = new Affiliation();
			affiliation.setContent(tmpValue);
			author.getAffiliation().add(affiliation);
			break;
		case "author":
			author.setId(UnifiedID.generateID("AU"));
			author.setFullName();
			authors.add(author);
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
			if (tmpValue != null)
				abstractText += tmpValue + "/n";
			break;
		case "abstract":
			articleMeta.setArticleAbstract(abstractText);
			abstractText = "";
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
		case "booktitle":
			book.setTitle(tmpValue);
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
