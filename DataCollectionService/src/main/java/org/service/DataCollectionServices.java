package org.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.ParserConfigurationException;

import org.IEEE.utils.IEEEParameter;
import org.pubMed.crawlers.EFetch;
import org.pubMed.crawlers.ESearch;
import org.pubMed.jaxb.eSearch.Id;
import org.pubMed.utils.DatePair;
import org.pubMed.utils.ESearchQuery;
import org.pubMed.utils.ESearchResult;
import org.pubMed.utils.PubMedMultiThreadsCall;
import org.pubMed.utils.PubMedParameter;
import org.pubMed.utils.PubMedSAXHandler;
import org.utils.jaxb.Article;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

@Path("/search")
public class DataCollectionServices {
	
	
	@POST
	@Path("/ieee/article")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	public Collection<Article> getIeeeArticles(final IEEEParameter ieeeParam){
		
		
		return null;
		
	}
	@POST
	@Path("/pubmed/postform")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	// the satrtDate and endDate must be in format of YYYY/MM/DD
	public InputStream getPubMedSearch(
			@DefaultValue("pubmed") @FormParam("db") String database,
			@FormParam("term") String term,
			@DefaultValue("true") @FormParam("usehistory") boolean useHistory,
			@FormParam("webEnv") @DefaultValue("") String webEnv,
			@FormParam("query_key") @DefaultValue("0") int queryKey,
			@DefaultValue("20") @FormParam("retmax") int retMax,
			@FormParam("field") @DefaultValue("") String field,
			@FormParam("datetype") @DefaultValue("") String dateType,
			@FormParam("reIdate") @DefaultValue("0") int reIdate,
			@FormParam("mindate") @DefaultValue("") String startDate,
			@FormParam("maxdate") @DefaultValue("") String endDate) {
		
		PubMedParameter pubMedParam=new PubMedParameter();
		pubMedParam.setDatabase(database);
		pubMedParam.setTerm(term);
		pubMedParam.setUseHistory(useHistory);
		pubMedParam.setWebEnv(webEnv);
		pubMedParam.setQueryKey(queryKey);
		pubMedParam.setRetMax(retMax);
		pubMedParam.setField(field);
		pubMedParam.setDateType(dateType);
		pubMedParam.setReIdate(reIdate);
		pubMedParam.setStartDate(startDate);
		pubMedParam.setEndDate(endDate);
		ESearchResult eResult=new ESearchResult(pubMedParam);
		List<Id> idList = eResult.getIdList();
		String idString = "db=" + database + "&id=";
		int index = 0;
		for (Id id : idList) {
			if (index > 0)
				idString += ",";
			idString += id.getContent();
			index++;
		}
		EFetch efetch = new EFetch(idString);
	
			return efetch.getSearchResult();
//		if (term.equals("")) {
//			throw new WebApplicationException(Response
//					.status(Status.BAD_REQUEST)
//					.entity("the term is required: ").build());
//		}
//		if (retMax > 500) {
//			throw new WebApplicationException(Response
//					.status(Status.BAD_REQUEST)
//					.entity("the maximum retival number is 500").build());
//		}
//		ESearchQuery ep = new ESearchQuery();
//		ep.setDataBase(database);
//		ep.setTerm(term);
//		if (!field.equals("")) {
//			ep.setField(field);
//		}
//		ep.setRetMax(retMax);
//		if (!webEnv.equals("") && !useHistory) {
//			throw new WebApplicationException(Response
//					.status(Status.BAD_REQUEST)
//					.entity("when use webEvn, useHistory must be true").build());
//		} else if (!webEnv.equals("") && useHistory) {
//			if (queryKey != 0) {
//				ep.setwebenv(webEnv);
//				ep.setUseHistory(true);
//			} else {
//				ep.setQueryKey(queryKey);
//				ep.setwebenv(webEnv);
//				ep.setUseHistory(true);
//			}
//		} else if (useHistory) {
//			ep.setUseHistory(true);
//		}
//
//		if (!dateType.equals("")) {
//			if (reIdate != 0) {
//				ep.setReIdate(reIdate);
//			}
//			ep.setDateType(dateType);
//		} else if (reIdate != 0) {
//			throw new WebApplicationException(Response
//					.status(Status.BAD_REQUEST)
//					.entity("to use reIdate, please set datetype").build());
//		}
//
//		if (!startDate.equals("") && endDate.equals("")) {
//			DatePair datePair = new DatePair(startDate);
//			ep.setDatePair(datePair);
//		} else if (!startDate.equals("") && !endDate.equals("")) {
//			DatePair datePair = new DatePair(startDate);
//			ep.setDatePair(datePair);
//		}
//
//		String query = ep.toQuery();
//		ESearch eSearch = new ESearch(query);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("pubmed/article")
	public Collection<Article> getArticle(final PubMedParameter pubMedParam) {
		ESearchResult eSearchResult = new ESearchResult(pubMedParam);
		try {
			List<Id> idList1 = eSearchResult.getIdList();
			if (idList1 != null) {
				if (idList1.size() > 100) {
					PubMedMultiThreadsCall mCall = new PubMedMultiThreadsCall(
							idList1, pubMedParam.getDatabase());
					List<Future<PubMedSAXHandler>> futures = mCall.getFutures();
					List<Article> articleList = new ArrayList<Article>();
					for (Future<PubMedSAXHandler> future : futures) {
						PubMedSAXHandler phandler;
						try {
							phandler = future.get();
							articleList.addAll(phandler.getArtciles());
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}
					return articleList;
				} else {
					String idString1 = "db=" + pubMedParam.getDatabase()
							+ "&id=";
					int index1 = 0;
					for (Id id : idList1) {
						if (index1 > 0)
							idString1 += ",";
						idString1 += id.getContent();
						index1++;
					}
					EFetch efetch = new EFetch(idString1, true);
					XMLReader xr = XMLReaderFactory.createXMLReader();
					PubMedSAXHandler handler = new PubMedSAXHandler();
					xr.setContentHandler(handler);
					xr.setErrorHandler(handler);
					InputStream inputStream;
				
						inputStream = efetch.getSearchResult();
						try {
							xr.parse(new InputSource(inputStream));
						} catch (IOException e) {
							e.printStackTrace();
						}
					
					List<Article> collection = (List<Article>) handler.getArtciles();
					return collection;
				}
			} else {
				throw new IllegalArgumentException("No Result");
			}
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("pubmed/inputstream")
	// the satrtDate and endDate must be in format of YYYY/MM/DD
	public InputStream getPubMedSearchResult(final PubMedParameter pubMedParam) {
		if (pubMedParam.getRetMax() > 500) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("the maximum retival number is 500").build());	
		} else {
			ESearchResult eSearchResult = new ESearchResult(pubMedParam);
			List<Id> idList = eSearchResult.getIdList();
			String idString = "db=" +pubMedParam.getDatabase() + "&id=";
			int index = 0;

			for (Id id : idList) {
				if (index > 0)
					idString += ",";
				idString += id.getContent();
				index++;
			}
			EFetch efetch = new EFetch(idString);
				return efetch.getSearchResult();

		}
	}

	public static void main(String[] agrs) {

	}
}