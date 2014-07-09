package nicta.pubMed.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import nicta.pubMed.jaxb.eSearch.Id;

import org.xml.sax.SAXException;

import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/search")
public class PubMedService {

	/*
	 * @DefaultValue("pubmed") @FormParam("db") String database,
	 * 
	 * @FormParam("term") String term,
	 * 
	 * @DefaultValue("true") @FormParam("usehistory") boolean useHistory,
	 * 
	 * @FormParam("webEnv") String webEvn,
	 * 
	 * @FormParam("query_key") int queryKey,
	 * 
	 * @FormParam("restart") boolean restart,
	 * 
	 * @DefaultValue("20") @FormParam("retmax") int retMax,
	 * 
	 * @FormParam("field") String field,
	 * 
	 * @FormParam("datetype") String dateType,
	 * 
	 * @FormParam("reIdate") int reIdate,
	 * 
	 * @FormParam("mindate") String startDate,
	 * 
	 * @FormParam("maxdate") String endDate
	 */

	@POST
	@Path("/postform")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	// the satrtDate and endDate must be in format of YYYY/MM/DD
	public InputStream getPubMedSearch(
			@DefaultValue("pubmed") @FormParam("db") String database,
			@FormParam("term") String term,
			@DefaultValue("true") @FormParam("usehistory") boolean useHistory,
			@FormParam("webEnv") @DefaultValue("") String webEnv,
			@FormParam("query_key") @DefaultValue("0") int queryKey,
		//	@FormParam("restart") boolean restart,
			@DefaultValue("20") @FormParam("retmax") int retMax,
			@FormParam("field") @DefaultValue("") String field,
			@FormParam("datetype") @DefaultValue("") String dateType,
			@FormParam("reIdate") @DefaultValue("0") int reIdate,
			@FormParam("mindate") @DefaultValue("") String startDate,
			@FormParam("maxdate") @DefaultValue("") String endDate) {
		if (term.equals("")) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("the term is required: ").build());
		}
		if (retMax > 5000) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("the maximum retival number is 5000").build());
		}
		ESearchQuery ep = new ESearchQuery();
		ep.setDataBase(database);
		ep.setTerm(term);
		if (!field.equals("")) {
			ep.setField(field);
		}
		ep.setRetMax(retMax);
		if (!webEnv.equals("") && !useHistory) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("when use webEvn, useHistory must be true").build());
		} else if (!webEnv.equals("") && useHistory) {
			if (queryKey != 0) {
				ep.setwebenv(webEnv);
				ep.setUseHistory(true);
			} else {
				ep.setQueryKey(queryKey);
				ep.setwebenv(webEnv);
				ep.setUseHistory(true);
			}
		} else if (useHistory) {
			ep.setUseHistory(true);
		}

		if (!dateType.equals("")) {
			if (reIdate != 0) {
				ep.setReIdate(reIdate);
			}
			ep.setDateType(dateType);
		} else if (reIdate != 0) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("to use reIdate, please set datetype").build());
		}

		if (!startDate.equals("") && endDate.equals("")) {
			DatePair datePair = new DatePair(startDate);
			ep.setDatePair(datePair);
		} else if (!startDate.equals("") && !endDate.equals("")) {
			DatePair datePair = new DatePair(startDate);
			ep.setDatePair(datePair);
		}

		String query = ep.toQuery();
		ESearch eSearch = new ESearch(query);
		try {
			List<Id> idList = eSearch.getListOfId();
			String idString = "db=" + database + "&id=";
			int index = 0;
			for (Id id : idList) {
				if (index > 0)
					idString += ",";
				idString += id.getContent();
				index++;
			}
			EFetch efetch = new EFetch(idString);
			try {
				return efetch.getEFetchResult();

			} catch (IOException e) {
				throw new WebApplicationException(Response
						.status(Status.BAD_REQUEST).entity(e.getMessage())
						.build());
			}

		} catch (ParserConfigurationException e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
		} catch (SAXException e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

	}

	@POST
	@Path("/pubmed")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	// the satrtDate and endDate must be in format of YYYY/MM/DD
	public InputStream getPubMedSearchResult(final PubMedParameter pubMedParam) {
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

		String query = ep.toQuery();
		ESearch eSearch = new ESearch(query);
		try {
			List<Id> idList = eSearch.getListOfId();
			String idString = "db=" + pubMedParam.database + "&id=";
			int index = 0;
			for (Id id : idList) {
				if (index > 0)
					idString += ",";
				idString += id.getContent();
				index++;
			}
			EFetch efetch = new EFetch(idString);
			try {
				return efetch.getEFetchResult();

			} catch (IOException e) {
				throw new WebApplicationException(Response
						.status(Status.BAD_REQUEST).entity(e.getMessage())
						.build());
			}

		} catch (ParserConfigurationException e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
		} catch (SAXException e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST).entity(e.getMessage()).build());
		}

	}

	public static void main(String[] agrs) {

	}
}
