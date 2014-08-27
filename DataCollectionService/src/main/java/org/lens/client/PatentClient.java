package org.lens.client;

import java.util.Collection;

import org.lens.utils.PatentParameter;
import org.utils.UniversalClient;
import org.utils.jaxb.Article;

public class PatentClient extends UniversalClient{
	private static final String PATENT_SERVICE_PATH="rest/search/patent/article";
	
	// use default url
	public PatentClient(){
		super(PATENT_SERVICE_PATH);
	}
	
	public PatentClient(String url){
		super(url,PATENT_SERVICE_PATH);
	}
	
	public Collection<Article> callPantentService(final PatentParameter patentParam){
		return call(patentParam);
	}
}
