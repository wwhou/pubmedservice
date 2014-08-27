package org.utils;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        final ObjectMapper om = new ObjectMapper();
        om.setDeserializationConfig(
                om.getDeserializationConfig().withAnnotationIntrospector(new JaxbAnnotationIntrospector()));
        OBJECT_MAPPER = om;
    }

	public ObjectMapper getContext(Class<?> arg0) {
		// TODO Auto-generated method stub
		return OBJECT_MAPPER;
	}

    
}