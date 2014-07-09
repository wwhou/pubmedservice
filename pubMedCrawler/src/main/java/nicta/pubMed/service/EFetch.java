package nicta.pubMed.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import nicta.pubMed.jaxb.eFetch.PubmedArticleSet;
import nicta.pubMed.utils.XMLConvertor;

public class EFetch {

	String base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?";
	private URL url;

	public EFetch(String query) {
		try {
			this.url = new URL(base + query);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EFetch(String query, boolean isXML) {
		if (isXML) {
			try {
				this.url = new URL(base + query + "&retmode=xml");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				this.url = new URL(base + query);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("restriction")
	public PubmedArticleSet getPubMedArticleSet() throws IOException,
			JAXBException, ParserConfigurationException, SAXException,
			XMLStreamException {
		XMLConvertor<PubmedArticleSet> xmlConvertor = new XMLConvertor<PubmedArticleSet>(
				new PubmedArticleSet(), url.openStream());
		return xmlConvertor.convertXMLtoObject();

	}

	public InputStream getEFetchResult() throws IOException {

		return url.openStream();
	}
}
