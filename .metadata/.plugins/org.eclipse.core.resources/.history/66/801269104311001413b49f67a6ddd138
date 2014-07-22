package nicta.pubMed.service;
//
//import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPmcServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.ArticleDocument.Article;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument.EFetchRequest;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument;
//import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchResultDocument;
//
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.List;


public class PubMedProcessor {
	
//	public List<Article> artList = new ArrayList<Article>();
//	String fetchIds = "";
//	String[] ids;
//	Article[] arts;
//	static List<String> fetchIdList;
//
//	EFetchResultDocument res;
//	public List<RawDocument> getDocumentList(String searchIds, List<String> ids) {
//		List<RawDocument> rawDocs = new ArrayList<RawDocument>();
//
//		try {
//			EFetchPmcServiceStub service = new EFetchPmcServiceStub();
//			EFetchRequestDocument reqDoc = EFetchRequestDocument.Factory
//					.newInstance();
//			EFetchRequest req = EFetchRequest.Factory.newInstance();
//			req.setId(searchIds);
//			reqDoc.setEFetchRequest(req);
//			 res = service.run_eFetch(reqDoc);
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//		
//		//res.getEFetchResult().getPmcArticleset().getArticleArray();
//		Article[] arts=res.getEFetchResult().getPmcArticleset().getArticleArray();
//		if (arts.length > 0 && ids.size()==arts.length) {
//			int i = 0;
//			for (Article art : arts) {
//				RawDocument rawDoc = new RawDocument();
//				rawDoc.setDocumentId(ids.get(i));
//				rawDoc.setDocumentType("PubMed");
//				rawDoc.setDocumentContent(art.toString());
//				rawDocs.add(rawDoc);
//				i++;
//			}
//			return rawDocs;
//		} else {
//			throw new IllegalArgumentException("No result!\n");
//		}
//	//	res.getEFetchResult().getPmcArticleset()
////		try {
////			EFetchPmcServiceStub service = new EFetchPmcServiceStub();
////			EFetchRequestDocument reqDoc = EFetchRequestDocument.Factory
////					.newInstance();
////			EFetchRequest req = EFetchRequest.Factory.newInstance();
////			req.setId(fetchIds);
////			reqDoc.setEFetchRequest(req);
////			EFetchResultDocument res = service.run_eFetch(reqDoc);
////			res.getEFetchResult().getPmcArticleset().getArticleArray();
////			if (arts.length > 0) {
////				int i = 0;
////				for (Article art : arts) {
////					RawDocument rawDoc = new RawDocument();
////					rawDoc.setDocumentId(ids.get(i));
////					rawDoc.setDocumentType("PubMed");
////					rawDoc.setDocumentContent(art.toString());
////					rawDocs.add(rawDoc);
////					i++;
////				}
////				return rawDocs;
////			} else {
////				throw new IllegalArgumentException("No result!\n");
////			}
////		} catch (Exception e) {
////			System.out.println(e.toString());
////		}
//		//return rawDocs;
//		
//	}
//
//	public static String[] getPubMedIdList(String dataSetName,
//			int numberOfResults, String query) throws RemoteException {
//		EUtilsServiceStub service = new EUtilsServiceStub();
//		fetchIdList = new ArrayList<String>();
//		// idsList = new ArrayList<List<String>>();
//		// call NCBI ESearch utility
//		// NOTE: search term should be URL encoded
//		EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
//		req.setDb(dataSetName);
//		req.setTerm(query);
//		req.setRetMax(numberOfResults + "");
//		EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
//		return res.getIdList().getId();
//	}
//	
//	
//	public static void main(String[] args){
//		try {PubMedProcessor p=new PubMedProcessor();
//		String[] s=PubMedProcessor.getPubMedIdList("pmc",5, "mouse");
//			List<String> ids=new ArrayList<String>();
//			String a=s[0];
//			ids.add(a);
//			for(int i=1;i<s.length;i++){
//				a+=","+s[i];
//				ids.add(s[i]);
//			}
//			
//			p.getDocumentList(a, ids);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
