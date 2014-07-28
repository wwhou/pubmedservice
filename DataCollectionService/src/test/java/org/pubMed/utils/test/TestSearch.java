package org.pubMed.utils.test;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.pubMed.utils.ObjectMapperProvider;
import org.pubMed.utils.PubMedParameter;
import org.utils.jaxb.Article;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestSearch {

	public static void main(String[] args) throws IOException {
		final ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        config.getClasses().add(ObjectMapperProvider.class);
        final Client client = Client.create(config);
		PubMedParameter pram = new PubMedParameter();
		pram.setDatabase("pubmed");
		pram.setTerm("mouse");
		pram.setRetMax(5);
		Date date=new Date();
		WebResource service = client
				.resource("http://localhost:8080/DataCollectionService/rest/search/article");
		System.out.print(pram.toString());
		ClientResponse response = service.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, pram);
		Collection<Article> articles = response.getEntity(new GenericType<Collection<Article>>() {});
		Date date1=new Date();
		long time=date1.getTime()-date.getTime();
		System.err.println(time);
		for(Article article:articles){
			System.out.println(article.getAuthors().get(0).getFirstName());
		}
		System.out.print(articles.size());		
	}
}
