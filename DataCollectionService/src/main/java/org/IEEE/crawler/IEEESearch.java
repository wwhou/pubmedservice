package org.IEEE.crawler;

import org.utils.Search;

public class IEEESearch extends Search {

	private static final String ieeeSearchBase = "http://ieeexplore.ieee.org/gateway/ipsSearch.jsp?";

	public IEEESearch(String query) {
		super(ieeeSearchBase + query);
	}

}
