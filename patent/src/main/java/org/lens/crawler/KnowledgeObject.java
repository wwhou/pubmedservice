package org.lens.crawler;
import java.lang.*;

public class KnowledgeObject {
    private String name;
    private String type;
    //private String url;

    
    public  KnowledgeObject(String nameStr, String type){
        this.name = nameStr;
        this.type = type;
	}
    
    public  KnowledgeObject(){
        this("-empty-","-unknown-");
	}
    
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setType(String type){
        this.type = type;
    }

	public String toString(){
//        return super.toString() + " " + " " + this.url ;
        return this.name + " " + this.type;
		
    }
    
    
	public String getName(){
        //        return super.toString() + " " + " " + this.url ;
        return this.name;
		
    }

    public String getType(){
        //        return super.toString() + " " + " " + this.url ;
        return this.type;
		
    }
    

}
