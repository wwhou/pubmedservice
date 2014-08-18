package org.lens.client;

import org.utils.UniversalClient;

public class PatentClient extends UniversalClient{
	private static final String PATENT_SERVICE_PATH="rest/search/patent/article";
	
	// use default url
	public PatentClient(){
		super(PATENT_SERVICE_PATH);
	}
	
	public PatentClient(String url){
		super(url,PATENT_SERVICE_PATH);
	}
}
