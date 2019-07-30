package aem.example.jee.jaxrsapi.core.filter;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Priority(1)
public class ContentTypeFilter implements ContainerRequestFilter {
    private final Logger logger = Logger.getLogger(ContentTypeFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        logger.info("Validating content type");
        String contentType = requestContext.getHeaderString(HttpHeaders.CONTENT_TYPE);
        if (contentType == null || contentType.isEmpty() || contentType.trim().length() == 0) {
            logger.log(Level.SEVERE, "No Content-Type provided");
            requestContext.abortWith(Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE)
                    .entity("{\"error\":\"Missing Content-Type\"}")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build());
        }
    }
}
