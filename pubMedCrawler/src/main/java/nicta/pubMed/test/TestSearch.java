package nicta.pubMed.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class TestSearch {

	public static void main(String[] args) throws IOException {

		URL obj = new URL("http://localhost:8080/pubMedCrawler/rest/search/pubmed");
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		String data = "{\"db\":\"pubmed\",\"term\":\"mouse\"}";
		// System.out.println(data);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		out.write(data);
		out.close();
		System.out.print(conn.getInputStream().toString());

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
