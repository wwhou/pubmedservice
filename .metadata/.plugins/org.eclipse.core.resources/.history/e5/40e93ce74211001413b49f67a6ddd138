package nicta.pubMed.service;
//
//import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPmcServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.ArticleDocument.Article;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument.EFetchRequest;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchResultDocument;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//

public class PubMedMultiThreads {

//	/**
//	 * @param args
//	 */
//
//	private int threads = 30;
//	// private List<String> results = new ArrayList<String>();
//	private List<Future<RawDocument[]>> results;
//
//	private EFetchResultDocument res;
//	// private List<Article> artList = new ArrayList<Article>();
//	private String fetchIds = "";
//	private List<String> ids;
//	private List<List<String>> idsList;
//	// private Article[] arts;
//	private List<String> fetchIdList;
//
//	public PubMedMultiThreads(String dataSetName, String query,
//			String numberOfResults) {
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
//			if (res.getIdList().getId().length < 10) {
//				for (int i = 0; i < res.getIdList().getId().length; i++) {
//					ids.add(res.getIdList().getId()[i]);
//					if (i > 0)
//						fetchIds += ",";
//					fetchIds += res.getIdList().getId()[i];
//					System.out.print(res.getIdList().getId()[i] + " ");
//				}
//				fetchIdList.add(fetchIds);
//			} else {
//				int i = 0;
//				fetchIds = "";
//				int length = res.getIdList().getId().length;
//				while (i < length) {
//					if ((i + 1) % 10 == 0) {
//						fetchIdList.add(fetchIds);
//						idsList.add(ids);
//						ids = new ArrayList<String>();
//						fetchIds = res.getIdList().getId()[i];
//					} else {
//						if (i > 0)
//							fetchIds += ",";
//						String id = res.getIdList().getId()[i];
//						fetchIds += id;
//						ids.add(id);
//					}
//					i++;
//				}
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
//		results = new ArrayList<Future<RawDocument[]>>();
//		for (int i = 0; i < fetchIdList.size(); i++) {
//			String docKey = fetchIdList.get(i);
//			List<String> ids = idsList.get(i);
//			Future<RawDocument[]> future = executor.submit(new MyCallable(
//					docKey, ids));
//
//			results.add(future);
//
//		}
//	}
//
//	public List<Future<RawDocument[]>> getResult() {
//		return results;
//	}
//
//	class MyCallable implements Callable<RawDocument[]> {
//		private final String fetchIds;
//		// private String result = "";
//		private RawDocument[] rawDocs;
//		private List<String> ids;
//
//		MyCallable(String fetchIds, List<String> ids) {
//			this.fetchIds = fetchIds;
//			this.ids = ids;
//			this.rawDocs = new RawDocument[ids.size()];
//		}
//
//		public RawDocument[] call() throws Exception {
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
//			if (arts.length > 0 && arts.length == ids.size()) {
//				int i = 0;
//				for (Article art : arts) {
//					RawDocument rawDoc = new RawDocument();
//					rawDoc.setDocumentId(ids.get(i));
//					rawDoc.setDocumentType("PubMed");
//					rawDoc.setDocumentContent(art.toString());
//					rawDocs[i] = rawDoc;
//					i++;
//				}
//			} else {
//				throw new IllegalArgumentException("No result");
//			}
//			return rawDocs;
//		}
//
//	}
//
//	public static void main(String[] args) {
//		Date time = new Date();
//
//		PubMedMultiThreads pb = new PubMedMultiThreads("pmc", "mouse", "500");
//		System.out.print(pb.getResult().size() + "\n");
//		List<Future<RawDocument[]>> results = pb.getResult();
//		try {
//			for(Future<RawDocument[]> result: results){
//			RawDocument[] dd = result.get();
//			for(RawDocument d:dd){
//				System.out.print(d.getDocumentId()+" ");
//			}
//			System.out.println();}
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
