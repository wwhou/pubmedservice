package org.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.IEEE.client.IeeeClient;
import org.IEEE.utils.IEEEParameter;

public class TestSearch {

	public static void main(String[] args) throws IOException {
		IeeeClient ieeeClient = new IeeeClient();
		IEEEParameter param = new IEEEParameter();
		param.setRetMax(1000);
		param.setTerm("java");

		InputStream stream = ieeeClient.call(param);
		
		BufferedReader br1 = new BufferedReader(new InputStreamReader(stream));
		try {
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
			System.out.print(br1.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ieeeClient.closeResponse();
	}
}
