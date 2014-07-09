package nicta.pubMed.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class PubMedID {

	private final String base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/";
	private String urlString = "";

	public PubMedID(String query, String numberOfResults) {
		urlString = base + "esearch.fcgi?db=pubmed&term=" + query
				+ "&usehistory=y&retmax=5000";
	}

	public String[] getIdList() throws IOException {
		URL url = new URL(urlString);
		InputStream inputStream = url.openStream();
		String line = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(
				inputStream));
		
			return null;
	}
}
