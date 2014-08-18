package org.lens.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.lens.utils.PatentJSONParser;

public class MultithreadsJSONTextConnection {

	/**
	 * @param args
	 */

	private int threads = 30;
	// private List<String> results = new ArrayList<String>();
	private ArrayList<Future<PatentJSONParser>> results;
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
		results = new ArrayList<Future<PatentJSONParser>>();
		for (int i = 0; i < docKeys.size(); i++) {
			String docKey = docKeys.get(i);
			Future<PatentJSONParser> future = executor
					.submit(new PatentSearchCallable(docKey));
			results.add(future);

		}
	}

	public ArrayList<Future<PatentJSONParser>> getXMLList() {
		return results;
	}

	class PatentSearchCallable implements Callable<PatentJSONParser> {
		private String dockey;

		PatentSearchCallable(String dockey) {
			this.dockey = dockey;
		}

		@Override
		public PatentJSONParser call() throws Exception {
			PatentSearch patentSearch = new PatentSearch(dockey);
			PatentJSONParser patentJsonParser = new PatentJSONParser(
					patentSearch.getSearchResult());
			return patentJsonParser;
		}
	}
}