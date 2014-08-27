package org.lens.crawler;

import org.utils.Search;

public class PatentSearch extends Search {

	final static String BASE_XML = "http://textserver.api.lens.org/frontpage.json?patent=";

	public PatentSearch(String dockey) {
		super(BASE_XML + dockey);
	}

}
