package org.lens.utils;

import org.json.JSONObject;
import org.utils.Parameter;

public class PatentParameter extends Parameter{

	private String sortField;

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	public String toString(){
		JSONObject jsonObject=new JSONObject();
		if(sortField.equals(""))
			jsonObject.put("sort_keys", sortField);
		jsonObject.put("query", term);
		jsonObject.put("pageSize", retMax);
		return jsonObject.toString();
	}
}
