package nicta.pubMed.service;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import nicta.pubMed.jaxb.eSearch.Id;

public class ESearchResult {
	
	private PubMedParameter pubMedParam;

	public ESearchResult(PubMedParameter pubMedParam){
		this.pubMedParam=pubMedParam;
	}
	
	public List<Id> getIdList(){
		if (pubMedParam.term.equals("")) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("the term is required: ").build());
		}
		if (pubMedParam.retMax > 5000) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("the maximum retival number is 5000").build());
		}
		ESearchQuery ep = new ESearchQuery();
		ep.setDataBase(pubMedParam.database);
		ep.setTerm(pubMedParam.term);
		if (!pubMedParam.field.equals("")) {
			ep.setField(pubMedParam.field);
		}
		ep.setRetMax(pubMedParam.retMax);
		if (!pubMedParam.webEnv.equals("") && !pubMedParam.useHistory) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("when use webEvn, useHistory must be true").build());
		} else if (!pubMedParam.webEnv.equals("") && pubMedParam.useHistory) {
			if (pubMedParam.queryKey != 0) {
				ep.setwebenv(pubMedParam.webEnv);
				ep.setUseHistory(true);
			} else {
				ep.setQueryKey(pubMedParam.queryKey);
				ep.setwebenv(pubMedParam.webEnv);
				ep.setUseHistory(true);
			}
		} else if (pubMedParam.useHistory) {
			ep.setUseHistory(true);
		}

		if (!pubMedParam.dateType.equals("")) {
			if (pubMedParam.reIdate != 0) {
				ep.setReIdate(pubMedParam.reIdate);
			}
			ep.setDateType(pubMedParam.dateType);
		} else if (pubMedParam.reIdate != 0) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("to use reIdate, please set datetype").build());
		}

		if (!pubMedParam.startDate.equals("") && pubMedParam.endDate.equals("")) {
			DatePair datePair = new DatePair(pubMedParam.startDate);
			ep.setDatePair(datePair);
		} else if (!pubMedParam.startDate.equals("")
				&& !pubMedParam.endDate.equals("")) {
			DatePair datePair = new DatePair(pubMedParam.startDate);
			ep.setDatePair(datePair);
		}
		ESearch eSearch = new ESearch(ep.toQuery());
		try {
			return eSearch.getListOfId();
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}