package org.pubMed.crawlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.utils.Search;


public class EFetch extends Search{

	private static final String base = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?";
	private URL url;

	public EFetch(String query) {
		super(base+query);
//		try {
//			this.url = new URL(base + query);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public EFetch(String query, boolean isXML) {
		super(base+query);
		if (isXML) {
			try {
				this.url = new URL(base + query + "&retmode=xml");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				this.url = new URL(base + query);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public InputStream getSearchResult(){

		try {
			return url.openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
