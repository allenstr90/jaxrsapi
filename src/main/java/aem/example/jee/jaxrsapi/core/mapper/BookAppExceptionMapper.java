package aem.example.jee.jaxrsapi.core.mapper;

import aem.example.jee.jaxrsapi.core.exception.UserLockException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class BookAppExceptionMapper implements ExceptionMapper<Throwable> {
    Logger logger = Logger.getLogger(BookAppExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof NotFoundException)
            return from(Response.Status.NOT_FOUND, exception.getMessage());
        if (exception instanceof UserLockException)
            return from(Response.Status.UNAUTHORIZED, exception.getMessage());
        logger.log(Level.SEVERE, "Error", exception);
        return from(Response.Status.BAD_REQUEST, "Error. Contact administrator.");
    }

    private Response from(Response.Status status, String msg) {
        return Response.status(status)
                .entity("{\"error\":\"" + msg + "\"}")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .build();
    }
}
