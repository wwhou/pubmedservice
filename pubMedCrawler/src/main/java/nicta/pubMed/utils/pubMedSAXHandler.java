package nicta.pubMed.utils;

import java.util.ArrayList;
import java.util.Collection;

import nicta.utils.Article;
import nicta.utils.ArticleMeta;
import nicta.utils.Author;
import nicta.utils.Book;
import nicta.utils.Date;
import nicta.utils.ISBN;
import nicta.utils.Journal;
import nicta.utils.Keyword;
import nicta.utils.Publisher;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class pubMedSAXHandler extends DefaultHandler {

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
	private Date articlePubDate;
	private Publisher publisher = null;

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
		case "articledate":
			articleDateFlag = true;
			articlePubDate = new Date();
			break;
		case "publisher":
			publisher = new Publisher();
			break;
		}
		// if (qName.equalsIgnoreCase("PubmedArticle")) {
		// if (articleMetas == null)
		// articleMetas = new ArrayList<ArticleMeta>();
		// if (authors == null)
		// authors = new ArrayList<Author>();
		// journal = new Journal();
		// articleMeta = new ArticleMeta();
		// article = new Article();
		// } else if (qName.equalsIgnoreCase("PubmedBookArticle")) {
		// if (articleMetas == null)
		// articleMetas = new ArrayList<ArticleMeta>();
		// if (authors == null)
		// authors = new ArrayList<Author>();
		// book = new Book();
		// articleMeta = new ArticleMeta();
		// article = new Article();
		// } else if (qName.equalsIgnoreCase("Author")) {
		// author = new Author();
		// } else if (qName.equalsIgnoreCase("AbstractText")) {
		// String label = attributes.getValue("NlmCategory");
		// if (!label.equals("UNLABELLED")) {
		// abstractText += label;
		// }
		// } else if (qName.equalsIgnoreCase("ArticleDate")) {
		// articleDateFlag = true;
		// articlePubDate = new Date();
		// } else if (qName.equalsIgnoreCase("Publisher")) {
		// publisher = new Publisher();
		// }
	}

	@Override
	public void endElement(String uri, String localName, String qName) {

		switch (qName.toLowerCase()) {
		case "pubmedarticle":
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
			if (articleDateFlag)
				articlePubDate.setYear(Integer.parseInt(tmpValue));
			break;
		case "month":
			if (articleDateFlag)
				articlePubDate.setMonth(Integer.parseInt(tmpValue));
			break;
		case "day":
			if (articleDateFlag)
				articlePubDate.setDay(Integer.parseInt(tmpValue));
			articleDateFlag = false;
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
		}
		//
		// if (qName.equals("PubmedArticle") ||
		// qName.equals("PubmedBookArticle")) {
		// articleMetas.add(articleMeta);
		// article.setArticleMeta(articleMeta);
		// article.getAuthors().add(author);
		// articles.add(article);
		// } else if (qName.equalsIgnoreCase("LastName")) {
		// author.setLastName(tmpValue);
		// } else if (qName.equalsIgnoreCase("ForeName")) {
		// author.setFirstName(tmpValue);
		// } else if (qName.equalsIgnoreCase("Affiliation")) {
		// author.setAffiliation(tmpValue);
		// } else if (qName.equalsIgnoreCase("Author")) {
		// authors.add(author);
		// } else if (qName.equalsIgnoreCase("Title")) {
		// if (journal != null) {
		// journal.setTitle(tmpValue);
		// }
		//
		// } else if (qName.equalsIgnoreCase("ISSN")) {
		// journal.setIssn(tmpValue);
		// } else if (qName.equalsIgnoreCase("MedlinePgn")) {
		// if (!tmpValue.isEmpty())
		// articleMeta.setPagination(tmpValue);
		// } else if (qName.equalsIgnoreCase("ArticleTitlle")) {
		// articleMeta.setTitle(tmpValue);
		// } else if (qName.equalsIgnoreCase("AbstractText")) {
		// abstractText += tmpValue + "/n";
		// } else if (qName.equalsIgnoreCase("Abstract")) {
		// articleMeta.setArticleAbstract(abstractText);
		// } else if (qName.equals("Keyword")) {
		// Keyword keyword = new Keyword();
		// keyword.setContent(tmpValue);
		// articleMeta.getKeywods().add(keyword);
		// } else if (qName.equalsIgnoreCase("BookTitle")) {
		// book.setTitle(tmpValue);
		// } else if (qName.equalsIgnoreCase("Publisher")) {
		// book.setPublisher(tmpValue);
		// } else if (qName.equalsIgnoreCase("isbn")) {
		// ISBN isbn = new ISBN();
		// isbn.setvalue(tmpValue);
		// book.getIsbn().add(isbn);
		// } else if (qName.equalsIgnoreCase("Year") && articleDateFlag) {
		// articlePubDate.setYear(Integer.parseInt(tmpValue));
		// } else if (qName.equalsIgnoreCase("Month") && articleDateFlag) {
		// articlePubDate.setMonth(Integer.parseInt(tmpValue));
		// } else if (qName.equalsIgnoreCase("Day") && articleDateFlag) {
		// articlePubDate.setDay(Integer.parseInt(tmpValue));
		// articleDateFlag = false;
		// }
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {

		tmpValue = new String(ac, i, j);

	}

}
