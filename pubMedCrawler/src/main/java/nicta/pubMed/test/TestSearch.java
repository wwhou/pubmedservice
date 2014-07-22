package nicta.pubMed.test;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import nicta.pubMed.service.ObjectMapperProvider;
import nicta.pubMed.service.PubMedParameter;
import nicta.utils.Article;
import nicta.utils.ArticleMeta;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TestSearch {

	public static void main(String[] args) throws IOException {

//		final ClientConfig config = new DefaultClientConfig();
//		config.getClasses().add(JacksonJsonProvider.class);
//		
//		config.getClasses().add(ObjectMapperProvider.class);
//		final Client client = Client.create(config);
		final ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJsonProvider.class);
        config.getClasses().add(ObjectMapperProvider.class);
        final Client client = Client.create(config);
		PubMedParameter pram = new PubMedParameter();
		pram.setDatabase("pubmed");
		pram.setTerm("mouse");
		pram.setRetMax(20);
		//pram.setQueryKey(1);
		
	
		WebResource service = client
				.resource("http://localhost:8080/pubMedCrawler/rest/search/article");
		System.out.print(pram.toString());
		ClientResponse response = service.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, pram);
//		InputStream inputstream=response.getEntityInputStream();
//		 BufferedReader br = new BufferedReader(new InputStreamReader(
//				inputstream));
//				 while (br.readLine() != null) {
//				 System.out.print(br.readLine());
//				 }
		Collection<ArticleMeta> articles = response.getEntity(new GenericType<Collection<ArticleMeta>>() {});
		for(ArticleMeta article:articles){
			System.out.println(article.getTitle());
		}
		System.out.print(articles.size());
		// //
		// final Map<String, String> namespacePrefixMapper = new HashMap<String,
		// String>();
		// namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance",
		// "xsi");
		//
		// final MoxyJsonConfig moxyJsonConfig =new MoxyJsonConfig()
		// .setNamespacePrefixMapper(namespacePrefixMapper)
		// .setNamespaceSeparator(':');
		//
		// final ContextResolver<MoxyJsonConfig> jsonConfigResolver =
		// moxyJsonConfig.resolver();
		// Client client = ClientBuilder.newClient()
		// .register(MoxyJsonFeature.class).register(jsonConfigResolver);
		//
		// //
		// //
		// WebTarget target = client.target("http://localhost:8080").path(
		// "pubMedCrawler/rest/search/pubmed");
		// PubMedParameter pram = new PubMedParameter();
		// pram.setDatabase("pubmed");
		// pram.setTerm("mouse");
		// target.request(MediaType.APPLICATION_JSON_TYPE).post(
		// Entity.entity(pram.toString(),MediaType.APPLICATION_JSON_TYPE));

		// URL obj = new URL(
		// "http://localhost:8080/pubMedCrawler/rest/search/pubmed");
		// HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		// conn.setRequestProperty("Content-Type",
		// "application/json;charset=utf8");
		// conn.setDoOutput(true);
		// conn.setRequestMethod("POST");
		// String data = "{\"db\":\"pubmed\",\"term\":\"mouse\"}";
		// System.out.println(data);
		//
		// OutputStreamWriter out = new
		// OutputStreamWriter(conn.getOutputStream());
		// out.write(data);
		// out.close();
		//
		// BufferedReader br = new BufferedReader(new InputStreamReader(
		// conn.getInputStream()));
		// while (br.readLine() != null) {
		// System.out.print(br.readLine());
		// }

		// try {
		//
		// Client client = Client.create();
		//
		// WebResource webResource = client
		// .resource("http://localhost:8080/pubMedCrawler/rest/search");
		//
		// String input =
		// "{\"db\":\"pubmed\",\"term\":\"mouse\", \"retMax\":\"10\"}";
		//
		// ClientResponse response = webResource.type("application/json")
		// .post(ClientResponse.class, input);
		//
		// if (response.getStatus() != 201) {
		// throw new RuntimeException("Failed : HTTP error code : "
		// + response.getStatus());
		// }
		//
		// System.out.println("Output from Server .... \n");
		// InputStream output = response.getEntity(InputStream.class);
		// System.out.println(output.toString());
		//
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		//
		// }
	}
}
