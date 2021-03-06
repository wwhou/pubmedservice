package org.IEEE.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.utils.Query;


public class IEEESearchQuery implements Query {

	IEEEParameter ieeeParam;

	public IEEESearchQuery(IEEEParameter ieeeParam) {
		this.ieeeParam = ieeeParam;
	}

	//
	// public void setPublishYear(int year){
	// queryList.add("&py="+year);
	// }
	// //default is 25 maximum is 1000
	// @Override
	// public void setRetMax(int retMax) {
	// if(retMax!=0)
	// queryList.add("&hc="+retMax);
	// }
	//
	//
	// @Override
	// public void setTerm(String term) {
	// if(!term.equals(""))
	// queryList.add("querytext=" + term);
	// }
	//
	// public void setStartYear(int year) {
	// if(year!=0)
	// queryList.add("&pys="+year);
	// }
	//
	// public void setEndYear(int year) {
	// if(year!=0)
	// queryList.add("&pye="+year);
	// }

	@Override
	public String toQuery() {
		String term = ieeeParam.getTerm();
		String query="";
		if (term.equals("")) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("Need query text for searching!!").build());
		} else {
			query="querytext="+term+"&hc="+ieeeParam.getRetMax();
		}
		if(ieeeParam.getStartYear()!=0)
			query+="&pys="+ieeeParam.getStartYear();
		if(ieeeParam.getEndYear()!=0)
			query+="&pye="+ieeeParam.getEndYear();
		return query;
	}
}
