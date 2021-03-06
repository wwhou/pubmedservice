package org.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Classification extends Search {

	private String searchObject;

	public Classification(String type, String symbol) {
		super(BASE_URL + type + "/ancestorsAndSelf?" + "symbol=" + symbol);
		if (type.equals("IPC"))
			searchObject = "textBody";
		else
			searchObject = "classTitle";

		// TODO Auto-generated constructor stub
	}

	private static final String BASE_URL = "http://pat-clas.t3as.org/pat-clas-service/rest/v1.0/";

	public String translateCodeToText() {

		if (!this.streamIsEmpty()) {
			InputStream inputStream = this.getSearchResult();
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));

			try {
				String s = "";
				String result = "";
				while ((s = br.readLine()) != null) {
					result += s;
				}
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject textBody = jsonArray.getJSONObject(i);

					sb.append(textBody.get(searchObject).toString() + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return sb.toString();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		Classification classi = new Classification("CPC", "A61N");
		System.out.print(classi.translateCodeToText());

	}
}
