package org.pubMed.crawlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.pubMed.jaxb.eSearch.ESearchResult;
import org.pubMed.jaxb.eSearch.Id;
import org.pubMed.utils.XMLConvertor;
import org.utils.Search;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;

@SuppressWarnings("unused")
public class ESearch extends Search{

	private final static String eSearchBase = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?";

	private String url;
	public ESearch(String query) {
		super(eSearchBase + query);
	}
	public List<Id> getListOfId() throws ParserConfigurationException,
			SAXException {
		try {
			XMLConvertor<ESearchResult> xmlConvertor = new XMLConvertor<ESearchResult>(
					new ESearchResult(), getSearchResult());
			try {
				ESearchResult eSearchResult;
				try {
					eSearchResult = xmlConvertor.convertXMLtoObject();

					return eSearchResult.getIdList().getId();
				} catch (XMLStreamException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
