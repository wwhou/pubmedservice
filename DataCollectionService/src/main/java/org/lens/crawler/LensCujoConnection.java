package org.lens.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/*
 From Ben Warren
 $(curl http://www.lens.org/cujo/search --header 'Content-Type:application/json' --data '{"query":"rice"}') | python -mjson.tool
 */

/*
 Call HTML from www.lens.org
 receive list of patents and then extract. Chase each patent for full front-page details
 Generate graph.
 */

public class LensCujoConnection {
    
    /*Build connection to CUJO, then download a list of patents.
     */
    int numResults;
    String query;
    String flags;
    String[] commands;
    //String charset = "UTF-8";
    //Graph patentGraph;
   List<String> docDB_keys;
    
   // HashSet<String> docDB_keys;
    //boolean validGraph;
    boolean valid;
    
    
    int MAX_NUM_PATENTS = 200;
    //http://cujo.api.lens.org/search
    String cmd_base = "curl http://www.lens.org/cujo/search --header 'Content-Type:application/json'";
    String sortField;
    String url="http://cujo.api.lens.org/search";
    URL obj;
    HttpURLConnection conn;
    public LensCujoConnection(int numResults, String query, String sortField){
        this.numResults = numResults;
        this.sortField=sortField;
       // this.query =  " --data '{\"query\" :\"" + query + "\"";
        this.query="{\"query\" :\"" + query + "\"";
       
        try {
			obj=new URL(url);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.valid = false;
        this.flags = ", \"facet\": \"false\", \"pageSize\": \""+this.numResults+"\"}'";
        
        this.docDB_keys = new ArrayList<String>();
    }
    
    public LensCujoConnection(String fullcommand){
        this.query = fullcommand;
        
        try {
			obj=new URL(url);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.docDB_keys = new ArrayList<String>();
        
    }
    
    public boolean connect(){
        boolean success = false;
        
        try {
			conn=(HttpURLConnection) obj.openConnection();
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
		String data =  query+ ", \"facet\": \"false\",\"sortField\": \""+this.sortField+"\",\"pageSize\": \""+this.numResults+"\",\"language\": \"en\"}'";
		//System.out.println(data);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
	    out.write(data);
	    out.close();
	    success=parseJSON(conn.getInputStream());
	    this.valid = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
        return success;
    }
    
    private void addKey(String key) {
        // System.out.println(key);
        this.docDB_keys.add(key);
    }
    
    
    private boolean parseJSON(InputStream instream){
        boolean success = false;
        try{   
            JSONObject jsonObject = new JSONObject(new JSONTokener(conn.getInputStream()));
            JSONArray jsonArrayResults = jsonObject.getJSONArray("results");
            for (int i=0; i<jsonArrayResults.length(); i++){
                JSONObject _temp_jsonObject = jsonArrayResults.getJSONObject(i);
                String key=_temp_jsonObject.getString("docdbKey"); 
                this.docDB_keys.add(key);
            }
            success = true;           
        }catch (JSONException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return success;
    }
 
    public List<String> getKeys() throws NullPointerException{
        if (this.valid)
            return this.docDB_keys;
        
        throw new NullPointerException("LensCujoConnection: No keys");
    }
    
    public static void main(String[] args) throws PatentException {
    	
    	LensCujoConnection lb=new LensCujoConnection(55, "NICTA", "pub_date");
    	lb.connect();
    	List<String> docKeys=lb.getKeys();
    	int i=0;
    	
 //   	MultithreadsJSONTextConnection multiJSON=new MultithreadsJSONTextConnection(docKeys);
//    	ArrayList<Future<String>> results=multiJSON.getXMLList();
//    	for(Future<String> result:results){
//    		try {
//				System.out.println(result.get().length());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	}
   // 	System.out.print(results.size());
    	
    }
}