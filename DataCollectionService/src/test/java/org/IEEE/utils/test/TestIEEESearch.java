package org.IEEE.utils.test;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.IEEE.crawler.IEEESearch;
import org.junit.Test;

public class TestIEEESearch {

	@Test
	public void testIEEESearch(){
		IEEESearch ieeeSearch=new IEEESearch("querytext=java&hc=1");
		InputStream inputStream =ieeeSearch.getSearchResult();
		assertNotNull(inputStream);
		BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
		try {
			String s="";
			while((s=br.readLine())!=null){				
					System.out.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
