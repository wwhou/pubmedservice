package org.utils;

import java.util.Collection;

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

public class UniversalClient {
	private final WebResource service;
	private static final String DEFAULT_BASE_URL="http://localhost:8080/DataCollectionService/";
	
	
	public UniversalClient(String servicePath){
		this(DEFAULT_BASE_URL, servicePath);
	}
	public UniversalClient(final String url, final String servicePath){
		final ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        config.getClasses().add(ObjectMapperProvider.class);
        final Client client = Client.create(config);
        service = client.resource(url +servicePath );
	}
	
	
	 public Collection<Article> call(final Object request) {
	        ClientResponse response = null;
	        try {
	            response = service.type(MediaType.APPLICATION_JSON)
	                              .accept(MediaType.APPLICATION_JSON)
	                              .post(ClientResponse.class, request);
	            
	            return response.getEntity(new GenericType<Collection<Article>>() {});
	        }
	        finally {
	            // belt and braces
	            if (response != null) {
	                //noinspection OverlyBroadCatchBlock
	                try { response.close(); }
	                catch (final Throwable ignored) { /* do nothing */ }
	            }
	        }
	    }
}
