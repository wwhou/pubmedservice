package nicta.pubMed.service;


import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.ArticleDocument.Article;
import gov.nih.nlm.ncbi.www.soap.eutils.efetch_pmc.EFetchResultDocument;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PubMedClass {

//	/**
//	 * @param args
//	 */
//	// private EFetchPubmedServiceStub.EFetchResult res;
//	EFetchResultDocument res;
//	public List<Article> artList = new ArrayList<Article>();
//	String fetchIds = "";
//	String[] ids;
//	Article[] arts;
//	List<String> fetchIdList;
//	
//	//this is the constructor for pre-processing the eSearch. 
//	//parameters: dataSetName = the database name
//	// query = search query
//	// numberOfResult = number retrieval of articles 
//	public PubMedClass(String dataSetName, String query, String numberOfResults) {
//		try {
//			EUtilsServiceStub service = new EUtilsServiceStub();
//			fetchIdList=new ArrayList<String>();
//			// call NCBI ESearch utility
//			// NOTE: search term should be URL encoded
//			EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
//			req.setDb(dataSetName);
//			req.setTerm(query);
//			req.setRetMax(numberOfResults);
//			EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
//	
//			ids = new String[Integer.parseInt(numberOfResults)];
//
//			// 10 as a group, set multithread see if it runs faster///
//			if (res.getIdList().getId().length < 10) {
//				for (int i = 0; i < res.getIdList().getId().length; i++) {
//					ids[i] = res.getIdList().getId()[i];
//					if (i > 0)
//						fetchIds += ",";
//					fetchIds += res.getIdList().getId()[i];
//					System.out.print(res.getIdList().getId()[i] + " ");
//				}
//				fetchIdList.add(fetchIds);
//			} else {
//				int i=0;
//				String ids="";
//				while(i<res.getIdList().getId().length){
//					if((i+1)/10==0){
//						fetchIdList.add(ids);
//						ids=res.getIdList().getId()[i];
//					}else{
//						ids+=res.getIdList().getId()[i];
//					}
//					i++;
//				}
//			}
//
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//
//		
//	}
//	
//	
//	// this this the out put result of pubmedMetaData 
//	public Article[] getArticleMetaData(){
//		Article[] arts = res.getEFetchResult().getPmcArticleset()
//				.getArticleArray();
//		return arts;
//	}
//	
//
//	public static void main(String[] args) throws Exception {
//		Date time = new Date();
//
//		pubMedCrawler pb = new pubMedCrawler("pmc", "mouse", "500");
//		int count = pb.getArticleMetaData();
//
//		System.out.println(count);
//		Date time1 = new Date();
//		System.out.println(time1.getTime() - time.getTime());
//
//	}

}
