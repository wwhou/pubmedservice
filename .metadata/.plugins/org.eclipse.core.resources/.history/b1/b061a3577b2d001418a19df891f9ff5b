package org.pubMed.utils;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;

public class MyXMLResolver implements XMLResolver {

    private String publicID;
    private String systemID;
    private String baseURI;
    private String namespace;

    public String getPublicID() {
        return publicID;
    }

    public String getSystemID() {
        return systemID;
    }

    public String getBaseURI() {
        return baseURI;
    }

    public String getNamespace() {
        return namespace;
    }

	public Object resolveEntity(String arg0, String arg1, String arg2,
			String arg3) throws XMLStreamException {
		this.publicID = arg0;
        this.systemID = arg1;
        this.baseURI = arg2;
        this.namespace = arg3;
        return null;
	}

}