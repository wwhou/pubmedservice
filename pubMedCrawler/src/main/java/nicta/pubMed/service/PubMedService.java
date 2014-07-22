package nicta.pubMed.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

import nicta.pubMed.jaxb.eSearch.Id;
import nicta.pubMed.utils.pubMedSAXHandler;
import nicta.utils.Article;
import nicta.utils.ArticleMeta;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

@Path("/search")
public class PubMedService {
	@POST
	@Path("/postform")
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/articlemeta")
	public Collection<ArticleMeta> getArticle(final PubMedParameter pubMedParam){
		ESearchResult eSearchResult=new ESearchResult(pubMedParam);
			try {
				List<Id> idList1 = eSearchResult.getIdList();
				if(idList1!=null){
				if (idList1.size() > 100) {
					PubMedMultiThreadsCall mCall = new PubMedMultiThreadsCall(
							idList1, pubMedParam.getDatabase());
					List<Future<pubMedSAXHandler>> futures=mCall.getFutures();
					List<ArticleMeta> articleList=new ArrayList<ArticleMeta>();
					for(Future<pubMedSAXHandler> future:futures ){
						pubMedSAXHandler phandler;
						try {
							phandler = future.get();
							articleList.addAll(phandler.getArticleMetas());
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}
					return articleList;
				} else {
					String idString1 = "db="+pubMedParam.getDatabase()+"&id=";
					int index1 = 0;
					for (Id id : idList1) {
						if (index1 > 0)
							idString1 += ",";
						idString1 += id.getContent();
						index1++;
					}
					EFetch efetch = new EFetch(idString1, true);
					XMLReader xr = XMLReaderFactory.createXMLReader();
					pubMedSAXHandler handler = new pubMedSAXHandler();
					xr.setContentHandler(handler);
					xr.setErrorHandler(handler);
					InputStream inputStream;
					try {
						inputStream = efetch.getEFetchResult();
						try {
							xr.parse(new InputSource(inputStream));
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					List<ArticleMeta> collection = (List<ArticleMeta>) handler.getArticleMetas();
					return collection;					
				}
				}else{
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
	@Path("/inputstream")
	// the satrtDate and endDate must be in format of YYYY/MM/DD
	public InputStream getPubMedSearchResult(final PubMedParameter pubMedParam) {
		ESearchResult eSearchResult=new ESearchResult(pubMedParam);
		List<Id> idList = eSearchResult.getIdList();
		String idString = "db=" + pubMedParam.database + "&id=";
		int index = 0;
		if(idList.size()<500){
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
		}else{
			return null;
		}
	}

	public static void main(String[] agrs) {

	}
}
