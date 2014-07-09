package nicta.pubMed.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import nicta.pubMed.jaxb.eSearch.Id;
import nicta.pubMed.utils.pubMedSAXHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class PubMedMultiThreadsCall {

	private List<Future<pubMedSAXHandler>> results;

	public PubMedMultiThreadsCall(List<Id> idList, String db) {

		results = new ArrayList<Future<pubMedSAXHandler>>();
		if (idList.size() > 0) {
			int length = idList.size();
			int threads =length/100;
			if(length%100!=0){
				threads++;
			}
			ExecutorService executor = Executors.newFixedThreadPool(threads);
			
			int i=0;
			String fetchIds="db="+db+"&id="+idList.get(0).getContent()+",";
			while (i < length) {
				if ((i + 1) % 100 == 0) {	
					Future<pubMedSAXHandler> future = executor
							.submit(new MyCallable(fetchIds));			
					results.add(future);
				//	System.out.println(fetchIds);
					fetchIds ="db="+db+"&id="+ idList.get(i).getContent();
				} else {
					if (i > 0)
						fetchIds += ",";
					String id = idList.get(i+1).getContent();
					fetchIds += id;					
				}
				i++;
			}
			executor.shutdown();
			
		}

	}

	public List<Future<pubMedSAXHandler>> getFutures() {
		return results;
	}

	class MyCallable implements Callable<pubMedSAXHandler> {

		private String query;

		public MyCallable(String query) {
			this.query = query;
		}

		@Override
		public pubMedSAXHandler call() throws Exception {

			EFetch efetch = new EFetch(query, true);
			XMLReader xr = XMLReaderFactory.createXMLReader();
			pubMedSAXHandler handler = new pubMedSAXHandler();
			Date timer1=new Date();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
			InputStream inputStream = efetch.getEFetchResult();
			xr.parse(new InputSource(inputStream));
			Date timer2=new Date();
			System.err.println(timer2.getTime()-timer1.getTime());
			return handler;
		}

	}
}
