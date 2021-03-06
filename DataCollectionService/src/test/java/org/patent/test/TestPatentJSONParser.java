package org.patent.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.lens.utils.PatentJSONParser;
import org.utils.jaxb.Article;
import org.utils.jaxb.ArticleId;
import org.utils.jaxb.ArticleIdList;
import org.utils.jaxb.ArticleMeta;
import org.utils.jaxb.KeywordList;
import org.utils.jaxb.Patent;
import org.utils.jaxb.Person;
import org.utils.jaxb.Priority;

public class TestPatentJSONParser {

	@Test
	public void testPatentJSONParser() throws FileNotFoundException {
		InputStream in = new FileInputStream("src/test/files/frontpage.json");
		PatentJSONParser pa = new PatentJSONParser(in, "AU_A_2006243809");
		pa.parse();
		final Article article = pa.getArticle();
		assertNotNull(article);
		final ArticleMeta articleMeta = article.getArticleMeta();
		assertNotNull(articleMeta);
		final List<Person> people = article.getPeople();
		assertEquals(2, people.size());
		final Person person = people.get(0);
		final String applicant = person.getAffiliation().get(0).getContent();
		assertEquals("NICTA IPR PTY LTD", applicant);
		final String fullname = person.getFullName();
		assertEquals("ANDERSON TREVOR", fullname);
		final String lastName = person.getLastName();
		assertEquals("ANDERSON", lastName);
		final String firstName = person.getFirstName();
		assertEquals("TREVOR", firstName);
		final String articleAbstract = articleMeta.getArticleAbstract();
		assertNull(articleAbstract);
		final ArticleIdList idList = articleMeta.getArticleIdList();
		assertEquals(2, idList.getArticleId().size());
		final ArticleId articleId = idList.getArticleId().get(0);
		assertEquals("AU_A_2006243809", articleId.getContent());
		assertEquals("patent", articleId.getIdType());
		final KeywordList keywordList = articleMeta.getKeywords();
		assertEquals(0, keywordList.getKeyword().size());
		final Patent patent = (Patent) articleMeta.getArticleType();
		assertEquals("AU", patent.getJuristiction());
		assertEquals("2009-04-23", patent.getOpiDate());
		assertEquals(3, patent.getPriority().size());
		Priority priority = patent.getPriority().get(0);
		assertEquals("AU", priority.getCountryCode());
		assertEquals("2005-04-29", priority.getDocumentDate());
		assertEquals("2005902178", priority.getDocumentNumber());
		assertEquals("A", priority.getDocumentKind());

		assertEquals(
				"Method and device for in-band optical performance monitoring",
				articleMeta.getTitle());
		assertNotNull(articleMeta.getId());
		assertNotNull(person.getId());
	}
}
