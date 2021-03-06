package org.pubMed.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;
import org.pubMed.utils.PubMedSAXHandler;
import org.utils.jaxb.Article;
import org.utils.jaxb.ArticleMeta;
import org.utils.jaxb.Date;
import org.utils.jaxb.Journal;
import org.utils.jaxb.Month;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class TestPubMedSAXHandler {

	private static final String TEST_XML = "src/test/files/efetch.xml";

	@Test
	public void testPubMedSAXHandler() throws SAXException, IOException {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		PubMedSAXHandler handler = new PubMedSAXHandler();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		InputStream inputStream = new FileInputStream(TEST_XML);
		xr.parse(new InputSource(inputStream));
	
		final ArrayList<Article> articles = (ArrayList<Article>) handler
				.getArtciles();
		assertNotNull(articles);
		assertEquals(2, articles.size());
		final ArticleMeta articleMeta = articles.get(0).getArticleMeta();
		assertNotNull(articleMeta);
		final String title = articleMeta.getTitle();
		assertNotNull(articleMeta);
		assertEquals(
				"Is cryopreservation a homogeneous process? Ultrastructure and motility of untreated, prefreezing, and postthawed spermatozoa of Diplodus puntazzo (Cetti).",
				title);
		Journal type = (Journal) articleMeta.getArticleType();
		assertEquals("Cryobiology", type.getTitle());
		Date journalDate = type.getPubDate();
		assertEquals(6, journalDate.getMonth());
		Date date = articleMeta.getPubDate();
		assertEquals(26, date.getDay());
		assertEquals(6, Month.compare("jun"));
	}
}
