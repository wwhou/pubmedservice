package org.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.IEEE.client.IEEEClient;
import org.IEEE.utils.IEEEParameter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.lens.client.PatentClient;
import org.lens.utils.PatentParameter;
import org.pubMed.client.PubMedClient;
import org.pubMed.utils.ObjectMapperProvider;
import org.pubMed.utils.PubMedParameter;
import org.utils.jaxb.Article;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestSearch {

	public static void main(String[] args) throws IOException {

		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		config.getClasses().add(ObjectMapperProvider.class);
		final Client client = Client.create(config);
		IEEEParameter pram = new IEEEParameter();
		pram.setTerm("mouse");
		pram.setRetMax(5);
		// pram.setStartYear(2000);
		// pram.setEndYear(2013);
		Date date = new Date();
		IEEEClient ieeeClient = new IEEEClient();
		Collection<Article> articles = ieeeClient.call(pram);
		for (Article article : articles) {
			System.out.println(article.getArticleMeta().getTitle());
			System.out.println(article.getPeople().get(0).getFirstName());
		}

		PubMedParameter pubmedParam = new PubMedParameter();
		pubmedParam.setDatabase("pubmed");
		pubmedParam.setTerm("mouse");
		pubmedParam.setRetMax(2);
		PubMedClient pubmedClient = new PubMedClient();
		Collection<Article> articles1 = pubmedClient.call(pubmedParam);
		for (Article article : articles1) {
			System.out.println(article.getArticleMeta().getTitle());
			System.out.println(article.getPeople().get(0).getFirstName());
		}

		PatentClient patentClient = new PatentClient();
		PatentParameter patentParam = new PatentParameter();
		patentParam.setRetMax(5);
		patentParam.setTerm("NICTA");
		patentParam.setSortField("-pub_date");
		patentClient.call(patentParam);

		Collection<Article> articles2 = patentClient.call(patentParam);
		for (Article article : articles2) {
			System.out.println(article.getArticleMeta().getTitle());
			System.out.println(article.getPeople().get(0).getFirstName());
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
