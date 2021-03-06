package org.lens.client;

import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.lens.crawler.PatentParameter;
import org.utils.ObjectMapperProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class PatentClient {
	private final WebResource service;
	private static final String DEFAULT_BASE_URL = "http://localhost:8080/patent/";
	private static final String PUBMED_SERVICE_PATH = "rest/search/stream";

	public PatentClient() {
		this(DEFAULT_BASE_URL);
	}

	public PatentClient(final String url) {
		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		config.getClasses().add(ObjectMapperProvider.class);
		final Client client = Client.create(config);
		service = client.resource(url + PUBMED_SERVICE_PATH);
	}

	private ClientResponse response = null;
	public InputStream call(final PatentParameter request) {
		
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
