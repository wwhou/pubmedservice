package org.IEEE.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.IEEE.utils.IEEEParameter;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.utils.ObjectMapperProvider;
import org.utils.jaxb.Article;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class IeeeClient {
	private final WebResource service;
	private static final String DEFAULT_BASE_URL = "http://localhost:8080/ieee/";
	private static final String PUBMED_SERVICE_PATH = "rest/search/stream";

	public IeeeClient() {
		this(DEFAULT_BASE_URL);
	}

	public IeeeClient(final String url) {
		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		config.getClasses().add(ObjectMapperProvider.class);
		final Client client = Client.create(config);
		service = client.resource(url + PUBMED_SERVICE_PATH);
	}

	private ClientResponse response = null;
	public InputStream call(final IEEEParameter request) {
		
			response = service.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, request);
			InputStream stream =response.getEntity(new GenericType<InputStream>() {});
            return stream;
	}
	
	public void closeResponse(){
		response.close();
	}
}
