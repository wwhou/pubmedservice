package org.pubMed.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.utils.Query;

public class ESearchQuery implements Query {

	private PubMedParameter pubMedParam;

	public ESearchQuery(PubMedParameter pubMedParam) {
		this.pubMedParam = pubMedParam;
	}

	@Override
	public String toQuery() {
		String query = "db=" + pubMedParam.getDatabase();
		if (pubMedParam.getTerm().equals("")) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("the term is required: ").build());
		}
		query += "&term=" + pubMedParam.getTerm();
		if (pubMedParam.getRetMax() > 5000) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("the maximum retival number is 5000").build());
		}
		if (!pubMedParam.getField().equals("")) {
			query += "&field=" + pubMedParam.getField();
		}
		query += "&retmax=" + pubMedParam.getRetMax();
		if (!pubMedParam.webEnv.equals("") && !pubMedParam.useHistory) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("when use webEvn, useHistory must be true").build());
		} else if (!pubMedParam.getWebEnv().equals("")
				&& pubMedParam.isUseHistory()) {
			if (pubMedParam.getQueryKey() != 0) {
				query += "&webEvn=" + pubMedParam.getWebEnv() + "&usehistory=y";
			} else {
				query += "&query_key=" + pubMedParam.getQueryKey() + "&webEvn="
						+ pubMedParam.getWebEnv() + "&usehistory=y";
			}
		} else if (pubMedParam.isUseHistory()) {
			query += "&usehistory=y";
		} else if (!pubMedParam.isUseHistory()) {
			query += "&usehistory=n";
		}

		if (!pubMedParam.getDateType().equals("")) {
			if (pubMedParam.getReIdate() != 0) {
				query += "&reIdate=" + pubMedParam.getReIdate();
			}
			query += setDateType(pubMedParam.getDateType());
		} else if (pubMedParam.reIdate != 0) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("to use reIdate, please set datetype").build());
		}

		if (!pubMedParam.startDate.equals("") && pubMedParam.endDate.equals("")) {
			DatePair datePair = new DatePair(pubMedParam.getStartDate());
			query += datePair.toString();
		} else if (!pubMedParam.startDate.equals("")
				&& !pubMedParam.endDate.equals("")) {
			DatePair datePair = new DatePair(pubMedParam.getStartDate(),
					pubMedParam.getEndDate());
			query += datePair.toString();
		}
		return query;
	}

	private String setDateType(String dateType) {
		String s = dateType.toLowerCase();
		if (s.equals("mdat") || s.equals("pdat") || s.equals("edat")) {
			return "&datetype=" + s;
		} else {
			throw new IllegalArgumentException("this is the wrong format");
		}
	}
	//
	// // the database type must be the first query parameter
	// public void setDataBase(String dataBase) {
	// queryList.add("db=" + dataBase);
	// }
	//
	// // the term must follow the database type
	// @Override
	// public void setTerm(String term) {
	// queryList.add("&term=" + term);
	// }
	//
	// public void setUseHistory(boolean useHistory) {
	// if (useHistory) {
	// queryList.add("&usehistory=" + "y");
	// } else {
	// queryList.add("&usehistory=" + "n");
	// }
	// }
	//
	// public void setwebenv(String webenv) {
	// queryList.add("&webEvn=" + webenv);
	// }
	//
	// public void setQueryKey(int queryKey) {
	// queryList.add("&query_key=" + queryKey);
	// }
	//
	// public void setReStart(int reStart) {
	// queryList.add("&restart=" + reStart);
	// }
	//
	// public void setField(String field) {
	// queryList.add("&field=" + field);
	// }
	//
	// public void setReIdate(int reIdate) {
	// queryList.add("&reIdate=" + reIdate);
	// }
	//
	// public void setDatePair(DatePair datePair) {
	// queryList.add(datePair.toString());
	// }
	//
	// @Override
	// public void setRetMax(int retMax) {
	// // TODO Auto-generated method stub
	//
	// queryList.add("&retmax=" + retMax);
	// }

}
