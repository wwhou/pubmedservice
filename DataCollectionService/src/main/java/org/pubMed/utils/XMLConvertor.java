package org.pubMed.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.SAXException;

public class XMLConvertor <T> {
	
	private T object;
	private InputStream stream;
	public XMLConvertor(T object, InputStream stream){
		this.object=object;
		
		this.stream=stream;
	}

	public T convertXMLtoObject() throws JAXBException, ParserConfigurationException, SAXException, FileNotFoundException, XMLStreamException{
		
		XMLInputFactory xif = XMLInputFactory.newInstance();
//        MyXMLResolver resolver = new MyXMLResolver();
//        xif.setXMLResolver(resolver);
        XMLStreamReader xsr = xif.createXMLStreamReader(new InputStreamReader(stream));
        JAXBContext jc = JAXBContext.newInstance(object.getClass());
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        @SuppressWarnings("unchecked")
		T ob= (T) unmarshaller.unmarshal(xsr);
        return ob;
	}
}
