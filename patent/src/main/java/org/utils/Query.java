package org.utils;

//
//import java.util.ArrayList;
//import java.util.List;

public interface Query {

	

	public String toQuery();
	// protected List<String> queryList;
	//
	// public Query() {
	// this.queryList = new ArrayList<String>();
	// }
	//
	// public abstract void setRetMax(int retMax);
	//
	// public abstract void setTerm(String term);
	//
	// public String toQuery() {
	// String query = "";
	// if (queryList.size() > 0)
	// for (String q : queryList) {
	// query += q;
	// }
	// if (query.equals("")) {
	// throw new IllegalArgumentException("no query!!");
	// }
	// return query;
	// }

}
