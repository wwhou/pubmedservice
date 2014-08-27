package org.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.utils.ObjectMapperProvider;
import org.utils.PubMedParameter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class PubMedClient {
	private final WebResource service;
	private static final String DEFAULT_BASE_URL = "http://localhost:8080/pubmed/";
	private static final String PUBMED_SERVICE_PATH = "rest/search/stream";

	public PubMedClient() {
		this(DEFAULT_BASE_URL);
	}

	public PubMedClient(final String url) {
		final ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		config.getClasses().add(ObjectMapperProvider.class);
		final Client client = Client.create(config);
		service = client.resource(url + PUBMED_SERVICE_PATH);
	}

	public void c(final PubMedParameter request) {
		ClientResponse response = null;
		try {
			response = service.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, request);

			InputStream inputStream1 = response.getEntityInputStream();
			BufferedReader br1 = new BufferedReader(new InputStreamReader(
					inputStream1));
			try {
				String s1 = "";
				while ((s1 = br1.readLine()) != null) {
					System.out.println(s1 + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			// belt and braces
			if (response != null) {
				// noinspection OverlyBroadCatchBlock
				try {
					response.close();
				} catch (final Throwable ignored) { /* do nothing */
				}
			}
		}
	}

	private ClientResponse response = null;

	public InputStream  call(final PubMedParameter request) {

		response = service.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, request);
		InputStream inputStream1 = response.getEntityInputStream();
		
		return inputStream1 ;

	}

	public void closeResponse() {
		if (response != null)
			response.close();
	}
}
