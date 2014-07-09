package nicta.pubMed.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EPost {

	private HttpURLConnection conn;
	private URL obj;
	private String data;

	// the query compose with database and ids 
	//e.g. db=pubmed&id=15718680,157427902,119703751
	public EPost(String query) {
		try {
			
			obj = new URL(
					"http://eutils.ncbi.nlm.nih.gov/entrez/eutils/epost.fcgi?"+query);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public InputStream getEPostResults() throws IOException {
		conn = (HttpURLConnection) obj.openConnection();
		conn.setRequestProperty("Content-Type",
				"application/xml;application/x-www-form-urlencoded");
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
//		OutputStreamWriter out = new OutputStreamWriter(
//				conn.getOutputStream());
//		out.write(data);
//		out.close();
		return conn.getInputStream();
	}
}
