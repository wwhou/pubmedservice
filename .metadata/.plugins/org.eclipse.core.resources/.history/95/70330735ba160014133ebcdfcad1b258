package org.IEEE.utils;

import org.pubMed.utils.DatePair;
import org.utils.Query;

public class IEEESearchQuery extends Query {
	
	public IEEESearchQuery(){
		super();
	}

	public void setPublishYear(int year){
		queryList.add("&py="+year);
	}
	//default is 25 maximum is 1000
	@Override
	public void setRetMax(int retMax) {
		queryList.add("&hc="+retMax);
	}

	
	@Override
	public void setTerm(String term) {
		queryList.add("querytext=" + term);
	}

	@Override
	public String toQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStartYear(int year) {
		queryList.add("&pys="+year);
	}
	
	public void setEndYear(int year) {
		queryList.add("&pye="+year);
	}
}
