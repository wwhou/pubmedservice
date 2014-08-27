package org.lens.crawler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadsJSONTextConnection {

	/**
	 * @param args
	 */

	private int threads = 30;
	// private List<String> results = new ArrayList<String>();
	private ArrayList<Future<InputStream>> results;
	private static final int threadsDivider = 10;
	private static final int threadsThreshold = 30;

	public MultithreadsJSONTextConnection(List<String> docKeys) {

		int size = docKeys.size();
		if (size < threads) {
			threads = size;
		} else if (size / threadsDivider < threadsThreshold) {
			threads = size / threadsDivider;
		}
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		results = new ArrayList<Future<InputStream>>();
		for (int i = 0; i < docKeys.size(); i++) {
			String docKey = docKeys.get(i);
			Future<InputStream> future = executor
					.submit(new PatentSearchCallable(docKey));
			results.add(future);

		}
	}

	public ArrayList<Future<InputStream>> getFutureList() {
		return results;
	}

	class PatentSearchCallable implements Callable<InputStream> {
		private String dockey;

		PatentSearchCallable(String dockey) {
			this.dockey = dockey;
		}

		public InputStream call() throws Exception {
			PatentSearch patentSearch = new PatentSearch(dockey);			
			return patentSearch.getSearchResult();
		}

		
	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		List<String> docKeys = new ArrayList<String>();
		docKeys.add("AU_A1_2009344196");
		MultithreadsJSONTextConnection con = new MultithreadsJSONTextConnection(
				docKeys);
//		ArrayList<Future<InputStream>> xxx = con.getFutureList();
//		for (Future<InputStream> x : xxx) {
//			InputStream p = x.get();
//			p.parse();
//			System.out.print(p.getArticle().getArticleMeta().getTitle());
//		}
	}
}
