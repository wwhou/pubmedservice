//package nicta.pubMedService;
//
//import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPmcServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.ArticleDocument.Article;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument.EFetchRequest;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchResultDocument;
//
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import nicta.utils.RawDocument;
//
//public class PubMed {
//
//	/**
//	 * @param args
//	 */
//
//	private int threads = 30;
//	// private List<String> results = new ArrayList<String>();
//	private List<RawDocument> results;
//
//	private EFetchResultDocument res;
//	// private List<Article> artList = new ArrayList<Article>();
//	private String fetchIds = "";
//	private List<String> ids;
//	private List<List<String>> idsList;
//	// private Article[] arts;
//	private List<String> fetchIdList;
//
//	public PubMed(String dataSetName, String query, int numberOfResults) {
//
//		
//			fetchIdList = new ArrayList<String>();
//			idsList = new ArrayList<List<String>>();
//			ids = new ArrayList<String>();
//			results = new ArrayList<RawDocument>();
//			try {
//				String[] fetchIdsArray = PubMedProcessor.getPubMedIdList(
//						dataSetName, numberOfResults, query);
//				process(fetchIdsArray);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			// 10 as a group///		
//
//	}
//
//	
//	private void process(String[] fetchIdsArray){
//		if (fetchIdsArray.length <=50) {
//			for (int i = 0; i < fetchIdsArray.length; i++) {
//				ids.add(fetchIdsArray[i]);
//				if (i > 0)
//					fetchIds += ",";
//				fetchIds += fetchIdsArray[i];
//				System.out.print(fetchIdsArray[i] + " ");
//			}
//			PubMedProcessor pubMedProcessor = new PubMedProcessor();
//			results = pubMedProcessor.getDocumentList(fetchIds, ids);
//		} else {
//			int i = 1;
//			fetchIds = fetchIdsArray[0];
//			ids.add(fetchIdsArray[0]);
//			int length = fetchIdsArray.length;
//			while (i < length) {
//				if ((i) % 50 == 0) {
//					fetchIdList.add(fetchIds);
//					idsList.add(ids);
//					ids = new ArrayList<String>();
//					ids.add(fetchIdsArray[i]);
//					fetchIds = fetchIdsArray[i];
//				} else {
//					fetchIds += ",";
//					String id = fetchIdsArray[i];
//					fetchIds += id;
//					ids.add(id);
//				}
//				i++;
//			}
//			fetchIdList.add(fetchIds);
//			idsList.add(ids);
//			int size = fetchIdList.size();
//			if (size < threads) {
//				threads = size;
//			} else if (size / 10 < 30) {
//				threads = size / 10;
//			}
//			ExecutorService executor = Executors
//					.newFixedThreadPool(threads);
//
//			for (int j = 0; j < fetchIdList.size(); j++) {
//				String docKey = fetchIdList.get(j);
//				List<String> ids = idsList.get(j);
//				Future<List<RawDocument>> future = executor
//						.submit(new MyCallable(docKey, ids));
//
//				try {
//					results.addAll(future.get());
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		}
//
//	
//	}
//	public List<RawDocument> getResult() {
//		return results;
//	}
//
//	class MyCallable implements Callable<List<RawDocument>> {
//		private final String fetchIds;
//
//		// private String result = "";
//		private List<RawDocument> rawDocs;
//		private List<String> ids;
//
//		MyCallable(String fetchIds, List<String> ids) {
//			this.fetchIds = fetchIds;
//			this.ids = ids;
//			// this.rawDocs = new RawDocument[ids.size()];
//		}
//
//		public List<RawDocument> call() throws Exception {
//			PubMedProcessor pubMedProcessor = new PubMedProcessor();
//			return rawDocs = pubMedProcessor.getDocumentList(fetchIds, ids);
//		}
//
//	}
//
//	public static void main(String[] args) {
//		Date time = new Date();
//
//		PubMed pb = new PubMed("pmc", "mouse", 500);
//		System.out.print(pb.getResult().size() + "\n");
//		List<RawDocument> results = pb.getResult();
//		
//			System.err.print(results.size());
//		
//
//		Date time1 = new Date();
//		System.out.println(time1.getTime() - time.getTime());
//	}
//
//}

package nicta.pubMed.service;
//
//import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPmcServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.ArticleDocument.Article;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument.EFetchRequest;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchResultDocument;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class PubMed {
//
//	/**
//	 * @param args
//	 */
//
//	private int threads = 30;
//	// private List<String> results = new ArrayList<String>();
//	private List<Future<Article[]>> results;
//
//	private EFetchResultDocument res;
//	// private List<Article> artList = new ArrayList<Article>();
//	private String fetchIds = "";
//	private List<String> ids;
//	private List<List<String>> idsList;
//	// private Article[] arts;
//	private List<String> fetchIdList;
//
//	public PubMed(String dataSetName, String query, String numberOfResults) {
//
//		try {
//			EUtilsServiceStub service = new EUtilsServiceStub();
//			fetchIdList = new ArrayList<String>();
//			idsList = new ArrayList<List<String>>();
//			// call NCBI ESearch utility
//			// NOTE: search term should be URL encoded
//			EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
//			req.setDb(dataSetName);
//			req.setTerm(query);
//			req.setRetMax(numberOfResults);
//			EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
//			// results output
//
//			ids = new ArrayList<String>();
//
//			// 10 as a group///
//			if (res.getIdList().getId().length < 100) {
//				for (int i = 0; i < res.getIdList().getId().length; i++) {
//					ids.add(res.getIdList().getId()[i]);
//					if (i > 0)
//						fetchIds += ",";
//					fetchIds += res.getIdList().getId()[i];
//					System.out.print(res.getIdList().getId()[i] + " ");
//				}
//				fetchIdList.add(fetchIds);
//			} else {
//				int i = 1;
//
//				fetchIds = res.getIdList().getId()[0];
//
//				int length = res.getIdList().getId().length;
//				while (i < length) {
//					if ((i) % 100 == 0) {
//						fetchIdList.add(fetchIds);
//						idsList.add(ids);
//						// ids = new ArrayList<String>();
//						fetchIds = res.getIdList().getId()[i];
//					} else {
//						fetchIds += ",";
//						String id = res.getIdList().getId()[i];
//						fetchIds += id;
//						// ids.add(id);
//					}
//					i++;
//				}
//				fetchIdList.add(fetchIds);
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//
//		int size = fetchIdList.size();
//		if (size < threads) {
//			threads = size;
//		} else if (size / 10 < 30) {
//			threads = size / 10;
//		}
//		ExecutorService executor = Executors.newFixedThreadPool(threads);
//		results = new ArrayList<Future<Article[]>>();
//		for (int i = 0; i < fetchIdList.size(); i++) {
//			String docKey = fetchIdList.get(i);
//			// List<String> ids = idsList.get(i);
//			Future<Article[]> future = executor.submit(new MyCallable(docKey));
//
//			results.add(future);
//
//		}
//	}
//
//	public List<Future<Article[]>> getResult() {
//		return results;
//	}
//
//	class MyCallable implements Callable<Article[]> {
//		private final String fetchIds;
//
//		// private String result = "";
//		// private Article[] rawDocs;
//		// private List<String> ids;
//
//		MyCallable(String fetchIds) {
//			this.fetchIds = fetchIds;
//			// this.ids = ids;
//			// this.rawDocs = new RawDocument[ids.size()];
//		}
//
//		public Article[] call() throws Exception {
//			try {
//				EFetchPmcServiceStub service = new EFetchPmcServiceStub();
//				EFetchRequestDocument reqDoc = EFetchRequestDocument.Factory
//						.newInstance();
//				EFetchRequest req = EFetchRequest.Factory.newInstance();
//				req.setId(fetchIds);
//				reqDoc.setEFetchRequest(req);
//				res = service.run_eFetch(reqDoc);
//			} catch (Exception e) {
//				System.out.println(e.toString());
//			}
//			Article[] arts = res.getEFetchResult().getPmcArticleset()
//					.getArticleArray();
//			if (arts.length > 0) {
//				// int i = 0;
//				// for (Article art : arts) {
//				// RawDocument rawDoc = new RawDocument();
//				// rawDoc.setDocumentId(ids.get(i));
//				// rawDoc.setDocumentType("PubMed");
//				// rawDoc.setDocumentContent(art.toString());
//				// rawDocs[i] = rawDoc;
//				// i++;
//				// }
//				return arts;
//			} else {
//				return null;
//			}
//
//		}
//
//	}
//
//	public static void main(String[] args) {
//		Date time = new Date();
//
//		PubMed pb = new PubMed("pmc", "mouse", "1000");
//		System.out.print(pb.getResult().size() + "\n");
//		List<Future<Article[]>> results = pb.getResult();
//		try {
//			for (Future<Article[]> result : results) {
//				Article[] dd = result.get();
//				System.out.print(dd.length);
////				if (dd != null)
////					for (Article a : dd) {
////						System.out.println(a.getFront().getArticleMeta()
////								.getArticleIdArray(0).toString());
////					}
//				System.out.println();
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Date time1 = new Date();
//		System.out.println(time1.getTime() - time.getTime());
//	}

}
