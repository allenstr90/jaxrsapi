package aem.example.jee.jaxrsapi.core.reader;

import aem.example.jee.jaxrsapi.core.type.Pageable;
import aem.example.jee.jaxrsapi.core.util.PaginatorUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class PaginationReader implements MessageBodyReader<Pageable> {

    private final Logger logger = Logger.getLogger(PaginationReader.class.getName());

    @Context
    private UriInfo uriInfo;

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type.isAssignableFrom(Pageable.class);
    }

    @Override
    public Pageable readFrom(Class<Pageable> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        Pageable pageable = Pageable.builder().build();
        try {
            pageable = PaginatorUtil.from(queryParams, Class.forName(((ParameterizedType) genericType).getActualTypeArguments()[0].getTypeName()));
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        return pageable;
    }
}
