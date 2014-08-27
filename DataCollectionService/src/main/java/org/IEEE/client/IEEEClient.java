package org.IEEE.client;


import java.util.Collection;

import org.IEEE.utils.IEEEParameter;
import org.utils.UniversalClient;
import org.utils.jaxb.Article;


public class IEEEClient extends UniversalClient{
	//private final WebResource service;
//	private static final String DEFAULT_BASE_URL="http://localhost:8080/DataCollectionService/";
	private static final String IEEE_SERVICE_PATH="rest/search/ieee/article";
	
	public IEEEClient(final String url){
		super( url, IEEE_SERVICE_PATH);
	}
	// use default url
	public IEEEClient(){
		super(IEEE_SERVICE_PATH);
	}

	public Collection<Article> callIEEEService(final IEEEParameter IeeeParam){
		return call(IeeeParam);
	}
}
