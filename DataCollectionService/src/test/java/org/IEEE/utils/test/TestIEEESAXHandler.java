package org.IEEE.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.IEEE.utils.IEEESAXHandler;
import org.junit.Test;
import org.utils.jaxb.Article;
import org.utils.jaxb.Person;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class TestIEEESAXHandler {
	private static final String TEST_XML = "src/test/files/search5.xml";// "src/test/files/ipsSearch.xml";

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
		assertEquals(5, articles.size());
		List<Person> authors = articles.get(3).getPeople();
		assertEquals("P", authors.get(0).getFirstName());

	}
}
