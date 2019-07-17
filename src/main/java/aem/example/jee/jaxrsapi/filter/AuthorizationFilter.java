package aem.example.jee.jaxrsapi.filter;

import aem.example.jee.jaxrsapi.repository.UserRepository;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class.getName());

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("Validating user roles on resource");

        Method resourceMethod = resourceInfo.getResourceMethod();

        if (resourceMethod.isAnnotationPresent(DenyAll.class))
            refuseRequest("Resource annotated with " + DenyAll.class.getName());

        RolesAllowed rolesAllowed = resourceMethod.getAnnotation(RolesAllowed.class);
        if (rolesAllowed != null) {
            checkAuthorization(rolesAllowed.value(), requestContext);
            return;
        }

        if (resourceMethod.isAnnotationPresent(PermitAll.class)) {
            return;
        }
        rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
        if (rolesAllowed != null) {
            checkAuthorization(rolesAllowed.value(), requestContext);
        }

        if (resourceInfo.getResourceClass().isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (!isAuthenticated(requestContext)) {
            refuseRequest("User not authenticated");
        }
    }

    private void checkAuthorization(String[] value, ContainerRequestContext requestContext) {
        if (value.length > 0 && !isAuthenticated(requestContext)) {
            refuseRequest("User no authenticated");
            return;
        }
        String username = requestContext.getSecurityContext().getUserPrincipal().getName();
        List<String> userRoles = userRepository.getUserRoles(username);

        if (Arrays
                .stream(value)
                .parallel()
                .anyMatch(s -> userRoles
                        .stream()
                        .parallel()
                        .anyMatch(s1 -> s1.equalsIgnoreCase(s))))
            return;
        refuseRequest("User no have any permitted role");
    }

    private boolean isAuthenticated(ContainerRequestContext requestContext) {
        return requestContext.getSecurityContext().getUserPrincipal() != null;
    }


    private void refuseRequest(String cause) {
        LOGGER.log(Level.SEVERE, "Refuse resources consummation :=> {0}", cause);
        throw new NotAuthorizedException("You don't have permissions to perform this action.", Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .entity("{\"error\":\"You don't have permissions to perform this action.\"}")
                .build());
    }
}
