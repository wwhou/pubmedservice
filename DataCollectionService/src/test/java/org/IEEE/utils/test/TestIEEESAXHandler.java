package org.IEEE.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.IEEE.utils.IEEESAXHandler;
import org.junit.Test;
import org.utils.jaxb.Article;
import org.utils.jaxb.Author;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class TestIEEESAXHandler {
	private static final String TEST_XML = "src/test/files/ipsSearch.xml";

	@Test
	public void testIEEESAXHandler() throws SAXException, IOException {
		XMLReader xr = XMLReaderFactory.createXMLReader();
		IEEESAXHandler handler = new IEEESAXHandler();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		InputStream inputStream = new FileInputStream(TEST_XML);
		xr.parse(new InputSource(inputStream));
		final ArrayList<Article> articles = (ArrayList<Article>) handler
				.getArtciles();
		assertNotNull(articles);
		assertEquals(10, articles.size());

		Author author = articles.get(0).getAuthors().get(0);
		assertEquals("Harty", author.getLastName());
		
		
	}
}
