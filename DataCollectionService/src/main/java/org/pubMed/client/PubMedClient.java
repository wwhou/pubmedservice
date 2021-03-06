package org.pubMed.client;

import java.util.Collection;

import org.pubMed.utils.PubMedParameter;
import org.utils.UniversalClient;
import org.utils.jaxb.Article;

public class PubMedClient extends UniversalClient {

	private static final String PUBMED_SERVICE_PATH = "rest/search/pubmed/article";

	// use default url
	public PubMedClient() {
		super(PUBMED_SERVICE_PATH);
	}

	public PubMedClient(String url) {
		super(url, PUBMED_SERVICE_PATH);
	}

	public Collection<Article> callPubMedService(final PubMedParameter pubmedParameter) {
		return call(pubmedParameter);		
	}
}
