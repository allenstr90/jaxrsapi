package aem.example.jee.jaxrsapi.core.mapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BookAppExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof NotFoundException)
            return from(Response.Status.NOT_FOUND, exception.getMessage());
        return from(Response.Status.BAD_REQUEST, exception.getMessage());
    }

    private Response from(Response.Status status, String msg) {
        return Response.status(status)
                .entity("{\"error\":\"" + msg + "\"}")
                .build();
    }
}
