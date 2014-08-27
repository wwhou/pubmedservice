package org.service;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.IEEE.crawler.IEEESearch;
import org.IEEE.utils.IEEEParameter;
import org.IEEE.utils.IEEESearchQuery;

@Path("/search")
public class IeeeService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("stream")
	public InputStream getStream(final IEEEParameter param) {
		IEEESearchQuery searchQuery = new IEEESearchQuery(param);
		IEEESearch search = new IEEESearch(searchQuery.toQuery());
		return search.getSearchResult();
	}
}
