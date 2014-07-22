package nicta.pubMed.utils;

import java.util.ArrayList;
import java.util.Collection;

import nicta.utils.Article;
import nicta.utils.ArticleId;
import nicta.utils.ArticleIdList;
import nicta.utils.ArticleMeta;
import nicta.utils.Author;
import nicta.utils.Book;
import nicta.utils.Date;
import nicta.utils.ISBN;
import nicta.utils.Journal;
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
	private ArticleIdList articleIdList;

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
		case "articleidlist":
			articleIdList = new ArticleIdList();
			break;
		}

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
