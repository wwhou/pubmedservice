package org.service;

import java.io.IOException;

import java.util.Collection;
import java.util.Date;
import org.IEEE.client.IEEEClient;
import org.IEEE.utils.IEEEParameter;
import org.lens.client.PatentClient;
import org.lens.utils.PatentParameter;
import org.pubMed.client.PubMedClient;
import org.pubMed.utils.PubMedParameter;
import org.utils.jaxb.Article;

public class TestSearch {

	public static void main(String[] args) throws IOException {

		IEEEParameter pram = new IEEEParameter();
		pram.setTerm("mouse");
		pram.setRetMax(10);
		// pram.setStartYear(2000);
		// pram.setEndYear(2013);
		Date date = new Date();
		IEEEClient ieeeClient = new IEEEClient();
		Collection<Article> articles = ieeeClient.callIEEEService(pram);
		for (Article article : articles) {
			System.out.println(article.getArticleMeta().getTitle());
			///System.out.println(article.getPeople().get(0).getFirstName());
		}

		System.out.println("----------------------------------------");

		PubMedParameter pubmedParam = new PubMedParameter();
		pubmedParam.setDatabase("pubmed");
		pubmedParam.setTerm("mouse");
		pubmedParam.setRetMax(10);
		PubMedClient pubmedClient = new PubMedClient();
		Collection<Article> articles1 = pubmedClient.callPubMedService(pubmedParam);
		for (Article article : articles1) {
			System.out.println(article.getArticleMeta().getTitle());
			//System.out.println(article.getPeople().get(0).getFirstName());
		}

		PatentClient patentClient = new PatentClient();
		PatentParameter patentParam = new PatentParameter();
		patentParam.setRetMax(10);
		patentParam.setTerm("NICTA");
		patentParam.setSortField("-pub_date");
		System.out.println("----------------------------------------");
		Collection<Article> articles2 = patentClient.callPantentService(patentParam);
		for (Article article : articles2) {
			System.out.println(article.getArticleMeta().getTitle());
			//System.out.println(article.getPeople().get(0).getFirstName());
		}

//		WebResource service1 = client
//				.resource("http://localhost:8080/DataCollectionService/rest/search/patent/article");
//		System.out.print(patentParam.toString());
//		ClientResponse response1 = service1.type(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)
//				.post(ClientResponse.class, patentParam);
//		InputStream inputStream1 = response1.getEntityInputStream();
//		BufferedReader br1 = new BufferedReader(new InputStreamReader(
//				inputStream1));
//		try {
//			String s1 = "";
//			while ((s1 = br1.readLine()) != null) {
//				System.out.println(s1 + "\n");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
