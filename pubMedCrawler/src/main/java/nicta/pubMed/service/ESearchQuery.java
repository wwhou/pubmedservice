package nicta.pubMed.service;

import java.util.ArrayList;
import java.util.List;

public class ESearchQuery {

	private List<String> queryList;

	public ESearchQuery() {
		queryList = new ArrayList<String>();
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

	public void setRetMax(int retMax) {
		queryList.add("&retmax=" + retMax);
	}

	// generate query string
	public String toQuery() {

		String queryString = "";
		if (queryList.size() == 0)
			return null;
		for (String query : queryList) {
			queryString += query;
		}
		return queryString;
	}

}
