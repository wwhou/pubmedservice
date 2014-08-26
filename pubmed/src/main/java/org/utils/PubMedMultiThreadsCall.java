package org.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;

import org.crawlers.EFetch;
import org.jaxb.eSearch.Id;

public class PubMedMultiThreadsCall {

	private List<Future<InputStream>> results;

	public PubMedMultiThreadsCall(List<Id> idList, String db) {

		results = new ArrayList<Future<InputStream>>();
		if (idList.size() > 0) {
			int length = idList.size();
			int threads = length / 100;
			if (length % 100!= 0) {
				threads++;
			}
			ExecutorService executor = Executors.newFixedThreadPool(threads);

			int i = 1;
			String fetchIds = "db=" + db + "&id=" + idList.get(0).getContent()
					+ ",";

			while (i < length) {
				if (i % 100 == 0) {
					Future<InputStream> future = executor
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
			
			 Future<InputStream> future = executor.submit(new MyCallable(
			 fetchIds));
			 results.add(future);
			executor.shutdown();

		}

	}

	public List<Future<InputStream>> getFutures() {
		return results;
	}

	class MyCallable implements Callable<InputStream> {

		private String query;

		public MyCallable(String query) {
			this.query = query;
		}

		@Override
		public InputStream call() throws Exception {

			EFetch efetch = new EFetch(query, true);
			InputStream inputStream = efetch.getSearchResult();

			return inputStream;
		}

	}

	public static void main(String[] args) {
		PubMedParameter param = new PubMedParameter();
		param.setRetMax(5);
		param.setDatabase("pubmed");
		param.setTerm("mouse");
		ESearchResult eSearchResult = new ESearchResult(param);
		final List<Id> idList = eSearchResult.getIdList();
		
		PubMedMultiThreadsCall multiThreadCall = new PubMedMultiThreadsCall(
				idList, param.getDatabase());
		List<Future<InputStream>> futures = multiThreadCall.getFutures();
		List<InputStream> streams = new ArrayList<InputStream>();
		for (Future<InputStream> future : futures) {
			try {
				streams.add(future.get());

			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (InputStream inputStream : streams) {
			BufferedReader br1 = new BufferedReader(
					new InputStreamReader(inputStream));
			try {
				String s1 = "";
			
				while ((s1 = br1.readLine()) != null) {
					System.out.println(s1);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("---------------------------------------");
		}
		
		System.out.println(streams.size());
	}
}
