package org.utils;

import org.parsers.IpcCodeParser;

public class PatentCodeFinder {

	private String type=null;
	private String code=null;
	public PatentCodeFinder(String typeString, String code){
		this.type=typeString;
		this.code=code;
	} 
	
	public void check(){
		switch (type) {
		case "US":
			type="USPC";
			break;
		case "IPC":
			this.code=IpcCodeParser.parseCode(code);
			break;
		}
	}
	public String getCode() {
		
		return this.code;
	}
	
	public String getType(){
		return this.type;
	}
}
