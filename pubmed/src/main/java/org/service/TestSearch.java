package org.service;


import org.client.PubMedClient;
import org.utils.PubMedParameter;

public class TestSearch {

	public static void main(String[] args) {
		PubMedClient pubmedClient = new PubMedClient();
		PubMedParameter param = new PubMedParameter();
		param.setRetMax(1150);
		param.setDatabase("pubmed");
		param.setTerm("mouse");
		pubmedClient.call(param);

	}
}
