package org.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Search {

	protected String url;

	public Search(String url) {
		this.url = url;
	}

	public InputStream getSearchResult() {
		URL searchURL;
		InputStream inputStream = null;
		try {
			searchURL = new URL(url);

			try {
				inputStream = searchURL.openConnection().getInputStream();

			} catch (IOException e) {

				return null;
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return inputStream;
	}

	public boolean streamIsEmpty() {
		URL searchURL;
		boolean flag = true;
		try {
			searchURL = new URL(url);

			try {
				searchURL.openConnection().getInputStream();
				flag = false;
			} catch (IOException e) {

			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return flag;
	}
}
