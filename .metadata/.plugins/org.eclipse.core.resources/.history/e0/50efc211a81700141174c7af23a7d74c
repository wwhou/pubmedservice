package org.pubMed.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.pubMed.jaxb.eSearch.ESearchResult;
import org.pubMed.jaxb.eSearch.Id;
import org.pubMed.jaxb.eSearch.IdList;
import org.pubMed.utils.XMLConvertor;
import org.xml.sax.SAXException;

public class TestXMLConvertor {
	private static final String TEST_XML = "src/test/files/esearch.xml";

	@Test
	public void testXMLConvertor() throws FileNotFoundException, JAXBException,
			ParserConfigurationException, SAXException, XMLStreamException {
		InputStream inputstream = new FileInputStream(TEST_XML);
		ESearchResult eSearchResult = new ESearchResult();
		XMLConvertor<ESearchResult> xmlConvertor = new XMLConvertor<ESearchResult>(
				eSearchResult, inputstream);
		eSearchResult = xmlConvertor.convertXMLtoObject();
		assertNotNull(eSearchResult);
		final IdList idList = eSearchResult.getIdList();
		assertNotNull(idList);
		final Id idFirst = idList.getId().get(0);
		assertEquals("25046855", idFirst.getContent());
		final Id idLast = idList.getId().get(idList.getId().size() - 1);
		assertEquals("25045937", idLast.getContent());

	}

}
