package org.lens.crawler;

//import httpclient.*;
//import org.apache.commons.httpclient.methods.*;

import java.lang.*;
import java.io.*;

public class PatentException extends Throwable {
    //String message;
    
    public PatentException(String message){
        super("PatentException: " + message);
    }
    
    public PatentException(){
        this("PatentException: ");
    }
    
}