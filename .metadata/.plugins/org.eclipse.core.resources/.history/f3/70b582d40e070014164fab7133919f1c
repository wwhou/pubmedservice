package nicta.pubMed.service;

import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPmcServiceStub;

import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.ArticleDocument.Article;

import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument;
import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchRequestDocument.EFetchRequest;
import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchResultDocument;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class pubMedCrawler {

//	/**
//	 * @param args
//	 */
//	//private EFetchPubmedServiceStub.EFetchResult res;
//	EFetchResultDocument res;
//	public List<Article> artList = new ArrayList<Article>();
//	String fetchIds = "";
//	String[] ids;
//	Article[] arts;
//
//	public pubMedCrawler(String dataSetName, String query,
//			String numberOfResults) {
//		try {
//			EUtilsServiceStub service = new EUtilsServiceStub();
//			// call NCBI ESearch utility
//			// NOTE: search term should be URL encoded
//			EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
//			req.setDb(dataSetName);
//			req.setTerm(query);
//			req.setRetMax(numberOfResults);
//			EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
//			// results output
//			System.out
//					.println("Original query: stem cells AND free fulltext[filter]");
//			System.out.println("Found ids: " + res.getCount());
//			System.out.print("First " + res.getRetMax() + " ids: ");
//			ids = new String[Integer.parseInt(numberOfResults)];
//			
//			
//			// 10 as a group, set multithread see if it runs faster///
//			
//			for (int i = 0; i < res.getIdList().getId().length; i++) {
//				ids[i] = res.getIdList().getId()[i];
//				if (i > 0)
//					fetchIds += ",";
//				fetchIds += res.getIdList().getId()[i];
//				System.out.print(res.getIdList().getId()[i] + " ");
//			}
//			
//			
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//
//		try {
//			EFetchPmcServiceStub service = new EFetchPmcServiceStub();
//			EFetchRequestDocument reqDoc = EFetchRequestDocument.Factory
//					.newInstance();
//			EFetchRequest req = EFetchRequest.Factory.newInstance();
//			req.setId(fetchIds);
//			reqDoc.setEFetchRequest(req);
//			 res = service.run_eFetch(reqDoc);
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//	}
//	
//	public int getArticleMetaData(){
//		int count=0;
//		Article[] arts=res.getEFetchResult().getPmcArticleset().getArticleArray();
//		
//		if(arts.length>0){
//			count=arts.length;
////			for(Article art:arts){
////				//System.out.println(art.toString());
////			count++;
////			}
//		}else{
//			throw new IllegalArgumentException("No result!");
//		}
//		return count;
//	}
////
////	public List<String> convertToListOfXML() {
////		// TODO Auto-generated method stub
////		List<String> contents = new ArrayList<String>();
////		Article[] arts = res.getEFetchResult().getPmcArticleset().getArticleArray();
////		if (arts.length > 0)
////}
//	public static void main(String[] args) throws Exception {
//		Date time=new Date();
//		
//		pubMedCrawler pb = new pubMedCrawler("pmc", "mouse", "1000");
//		int count=pb.getArticleMetaData();
//		
//	System.out.println(count);
//	Date time1=new Date();
//	System.out.println(time1.getTime()-time.getTime());
//		//List<String> ss = pb.convertToListOfXML();
////		for (String s : ss) {
////			System.out.println(s);
////		}
//		// pb.saveToXML("/Users/weiweihou/Desktop/YYYYY/testMedPub");
//		// pb.saveToTextFile("/Users/weiweihou/Desktop/YYYYY/testMedPubFile");
//
//	}
}
