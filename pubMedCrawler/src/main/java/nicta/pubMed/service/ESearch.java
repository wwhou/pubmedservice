package nicta.pubMed.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import nicta.pubMed.jaxb.eSearch.ESearchResult;
import nicta.pubMed.jaxb.eSearch.Id;
import nicta.pubMed.utils.XMLConvertor;
import nicta.pubMed.utils.pubMedSAXHandler;
import nicta.utils.Article;
import nicta.utils.ArticleMeta;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ESearch {

	private String eSearchBase = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?";

	public ESearch(String query) {
		eSearchBase += query;
	}

	private InputStream getESearchStream() throws IOException {
		URL eSearchURL = new URL(eSearchBase);
		InputStream eSearchStream = eSearchURL.openStream();
		// eSearchStream.
		return eSearchStream;
	}

	public List<Id> getListOfId() throws ParserConfigurationException,
			SAXException {
		try {
			XMLConvertor<ESearchResult> xmlConvertor = new XMLConvertor<ESearchResult>(
					new ESearchResult(), getESearchStream());
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

	public static void main(String[] agrs) throws IOException, JAXBException,
			XMLStreamException, InterruptedException, ExecutionException {

		Date time = new Date();
		long t = time.getTime();

		ESearch eSearch = new ESearch(
		
				"db=pubmed&term=cancer&reldate=60&datetype=edat&retmax=500&usehistory=y");
		
		try {
			List<Id> idList = eSearch.getListOfId();
			Date time7 = new Date();
			long t7= time7.getTime();
			System.out.println("eSearch Time:"+(t7- t));
			Date time5 = new Date();
			long t5 = time5.getTime();
			if (idList.size() > 100) {
				PubMedMultiThreadsCall mCall = new PubMedMultiThreadsCall(
						idList, "pubmed");
				List<Future<pubMedSAXHandler>> futures=mCall.getFutures();
				List<Article> articleList=new ArrayList<Article>();
				for(Future<pubMedSAXHandler> future:futures ){
					pubMedSAXHandler phandler=future.get();
		
					articleList.addAll(phandler.getArtciles());
				}
				Date time6 = new Date();
				long t6 = time6.getTime();
				Date time11 = new Date();
				long t11 = time11.getTime();
				System.out.println(t6- t5);
				System.out.println("final time:"+(t6- t));
				UUID uid =UUID.randomUUID();
				System.out.println(t6+"-"+uid+"__"+t11);
				
			} else {
				Date time2 = new Date();
				long t2 = time2.getTime();
				System.out.println(t2 - t);

				String idString = "db=pubmed&id=";
				int index = 0;
				for (Id id : idList) {
					if (index > 0)
						idString += ",";
					idString += id.getContent();
					index++;
				}
				Date time3 = new Date();
				long t3 = time3.getTime();
				System.out.println(t3 - t2);
				System.out.println(idString);
				EFetch efetch = new EFetch(idString, true);

				XMLReader xr = XMLReaderFactory.createXMLReader();
				pubMedSAXHandler handler = new pubMedSAXHandler();
				xr.setContentHandler(handler);
				xr.setErrorHandler(handler);
				InputStream inputStream = efetch.getEFetchResult();

				Date time4 = new Date();
				long t4 = time4.getTime();
				System.out.println("eFetch Speed:" + (t4 - t3));
				xr.parse(new InputSource(inputStream));
				// System.out.print(handler.getArticleMetas().size());
				List<ArticleMeta> collection = (List<ArticleMeta>) handler
						.getArticleMetas();
				System.out.println(collection.get(0).getTitle());
				Date time1 = new Date();
				System.out.println(time1.getTime() - t4);

				System.out.println(time1.getTime() - t);
				
				UUID uid = UUID.fromString(time1.getTime()+"");
				System.out.println(uid.randomUUID());
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
