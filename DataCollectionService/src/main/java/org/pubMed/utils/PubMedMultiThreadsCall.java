package org.pubMed.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.pubMed.crawlers.EFetch;
import org.pubMed.jaxb.eSearch.Id;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class PubMedMultiThreadsCall {

	private List<Future<PubMedSAXHandler>> results;

	public PubMedMultiThreadsCall(List<Id> idList, String db) {

		results = new ArrayList<Future<PubMedSAXHandler>>();
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
				if (i % 100 == 0) {
					Future<PubMedSAXHandler> future = executor
							.submit(new MyCallable(fetchIds));	
					results.add(future);
					// System.out.println(fetchIds);
					fetchIds = "db=" + db + "&id=" + idList.get(i).getContent();
				} else{
					fetchIds += ",";
					String id = idList.get(i).getContent();
					fetchIds += id;
				}
				i++;
			}
			Future<PubMedSAXHandler> future = executor
					.submit(new MyCallable(fetchIds));	
			results.add(future);
			executor.shutdown();
			
		}

	}

	public List<Future<PubMedSAXHandler>> getFutures() {
		return results;
	}

	class MyCallable implements Callable<PubMedSAXHandler> {

		private String query;

		public MyCallable(String query) {
			this.query = query;
		}

		@Override
		public PubMedSAXHandler call() throws Exception {

			EFetch efetch = new EFetch(query, true);
			XMLReader xr = XMLReaderFactory.createXMLReader();
			PubMedSAXHandler handler = new PubMedSAXHandler();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
			InputStream inputStream = efetch.getSearchResult();
			xr.parse(new InputSource(inputStream));
			return handler;
		}

	}
}
