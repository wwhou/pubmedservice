package org.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Search {

	protected String url;
	
	public Search(String url){
		this.url=url;
	}
	
	public InputStream getSearchResult(){
		URL searchURL;
		InputStream inputStream=null;
		try {
			searchURL = new URL(url);
			
			try {
				inputStream = searchURL.openStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return inputStream;
	}
}
