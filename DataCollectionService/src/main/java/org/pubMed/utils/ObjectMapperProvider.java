package org.pubMed.utils;

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

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return OBJECT_MAPPER;
    }
}