package org.utils;

import java.util.ArrayList;
import java.util.List;


public abstract class Query {
	
	protected List<String> queryList;
	
	public Query(){
		this.queryList=new ArrayList<String>();
	}
	public abstract void setRetMax(int retMax);
	public abstract void setTerm(String term);
	public abstract String toQuery();
	
}