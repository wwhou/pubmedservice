package org.pubMed.utils;

import org.utils.Query;

public class ESearchQuery extends Query {

	public ESearchQuery() {
		super();
	}

	public void setDateType(String dateType) {
		String s = dateType.toLowerCase();
		if (s.equals("mdat") || s.equals("pdat") || s.equals("edat")) {
			queryList.add("&datetype=" + s);
		} else {
			throw new IllegalArgumentException("this is the wrong format");
		}
	}

	// the database type must be the first query parameter
	public void setDataBase(String dataBase) {
		queryList.add("db=" + dataBase);
	}

	// the term must follow the database type
	@Override
	public void setTerm(String term) {
		queryList.add("&term=" + term);
	}

	public void setUseHistory(boolean useHistory) {
		if (useHistory) {
			queryList.add("&usehistory=" + "y");
		} else {
			queryList.add("&usehistory=" + "n");
		}
	}

	public void setwebenv(String webenv) {
		queryList.add("&webEvn=" + webenv);
	}

	public void setQueryKey(int queryKey) {
		queryList.add("&query_key=" + queryKey);
	}

	public void setReStart(int reStart) {
		queryList.add("&restart=" + reStart);
	}

	public void setField(String field) {
		queryList.add("&field=" + field);
	}

	public void setReIdate(int reIdate) {
		queryList.add("&reIdate=" + reIdate);
	}

	public void setDatePair(DatePair datePair) {
		queryList.add(datePair.toString());
	}

	// generate query string
	@Override
	public String toQuery() {

		String queryString = "";
		if (queryList.size() == 0)
			return null;
		for (String query : queryList) {
			queryString += query;
		}
		return queryString;
	}

	@Override
	public void setRetMax(int retMax) {
		// TODO Auto-generated method stub

		queryList.add("&retmax=" + retMax);
	}

}
