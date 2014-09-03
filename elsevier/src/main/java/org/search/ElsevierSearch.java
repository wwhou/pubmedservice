package org.search;

import java.io.InputStream;
import java.net.URL;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ElsevierSearch {

	URL searchURL;
	private static final String BASE_URL = "http://api.elsevier.com/content/search/index:scopus?query=";

	private static final String BASE_AUTHENTICATE_URL = "http://api.elsevier.com/authenticate?";

	private static final String appKey = "771cfd7df1b4c631f2076722e965de65";

	public String getAuthToken() {
		Client client = Client.create();

		String url = BASE_AUTHENTICATE_URL + "httpAccept=application/json";
		WebResource webResource = client.resource(url);
		ClientResponse response = null;
		response = webResource
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("X-ELS-APIKey", appKey).get(ClientResponse.class);
		JSONObject jsonOb = new JSONObject(new JSONTokener(
				response.getEntityInputStream()));
		return jsonOb.getJSONObject("authenticate-response").get("authtoken")
				.toString();
	}

	public InputStream getSearchResult(final String query, final int count
		,String value) {
		Client client = Client.create();

		String url = BASE_URL + "KEY%28" + query + "%29&count=" + count
				+"&httpAccept=application/json";
		WebResource webResource = client.resource(url);

		//.header("Authorization", auToken)
		ClientResponse response = null;
		
		response = webResource
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("X-ELS-APIKey", appKey).header("X-ELS-Authtoken", value)
				.get(ClientResponse.class);

		System.out.print(response.getHeaders().toString());
		String jsonStr = response.getEntity(String.class);
		System.out.print(jsonStr);
		
		return null;
	}
	
	public void search(){
		Client client = Client.create();
		String url="http://api.elsevier.com/content/abstract/scopus_id:84902477804";
		WebResource webResource = client.resource(url);
		ClientResponse response = null;
		response = webResource
				.header("Content-Type", "application/json;charset=UTF-8")
				.header("X-ELS-APIKey", appKey)
				.get(ClientResponse.class);
		System.out.print(response.getHeaders().toString());
		String jsonStr = response.getEntity(String.class);
		System.out.print(jsonStr);
	}

	public static void main(String[] args) {
		ElsevierSearch search = new ElsevierSearch();
		//search.search();
		System.out.println(search.getAuthToken());
		search.getSearchResult("java", 2, search.getAuthToken());
	}

}
