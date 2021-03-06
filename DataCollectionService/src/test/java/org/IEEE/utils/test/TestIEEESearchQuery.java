package org.IEEE.utils.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.WebApplicationException;

import org.IEEE.utils.IEEEParameter;
import org.IEEE.utils.IEEESearchQuery;
import org.junit.Test;

public class TestIEEESearchQuery {

	@Test(expected=WebApplicationException.class)
	public void testIEEESearchQuery(){
		IEEEParameter ieeeParam=new IEEEParameter();
		ieeeParam.setRetMax(10);
		ieeeParam.setTerm("java");
		ieeeParam.setStartYear(2000);
		ieeeParam.setEndYear(2013);
		IEEESearchQuery searchQuery=new IEEESearchQuery(ieeeParam);
		assertEquals("querytext=java&hc=10&pys=2000&pye=2013", searchQuery.toQuery());
		
		IEEEParameter ieeeParam1=new IEEEParameter();
		ieeeParam1.setRetMax(10);
		ieeeParam1.setTerm("java");	
		IEEESearchQuery searchQuery1=new IEEESearchQuery(ieeeParam1);
		assertEquals("querytext=java&hc=10", searchQuery1.toQuery());
		
		IEEEParameter ieeeParam2=new IEEEParameter();		
		ieeeParam2.setTerm("java");	
		IEEESearchQuery searchQuery2=new IEEESearchQuery(ieeeParam2);
		assertEquals("querytext=java&hc=20", searchQuery2.toQuery());
		
		IEEEParameter ieeeParam3=new IEEEParameter();			
		IEEESearchQuery searchQuery3=new IEEESearchQuery(ieeeParam3);
		searchQuery3.toQuery();
	}
}