package org.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.client.PubMedClient;
import org.crawlers.EFetch;
import org.jaxb.eSearch.Id;
import org.utils.ESearchResult;
import org.utils.PubMedParameter;
import org.utils.PubmedPackage;

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
