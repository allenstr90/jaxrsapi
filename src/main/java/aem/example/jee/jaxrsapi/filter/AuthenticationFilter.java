package aem.example.jee.jaxrsapi.filter;

import aem.example.jee.jaxrsapi.model.UserJWT;
import aem.example.jee.jaxrsapi.util.JWTService;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());
    private static final List<String> WHITE_LIST = Arrays.asList("auth/token", "auth/refresh");
    private static final String BEARER = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("Intercepting request for authentication");
        if (!WHITE_LIST.contains(requestContext.getUriInfo().getPath())) {
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith(BEARER.toLowerCase() + " ")) {
                LOGGER.info("No authorization header present");
                refuseWithUnauthorized(requestContext, "No authorization header present");
                return;
            }

            String token = authorizationHeader.substring(BEARER.length()).trim();
            if (!JWTService.validateAccessToken(token)) {
                LOGGER.info("No valid token provided");
                refuseWithUnauthorized(requestContext, "No valid token provided");
            } else {
                final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
                UserJWT userJWT = JWTService.getUserJwt(token);
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return userJWT::getUsername;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return Arrays.stream(userJWT.getRoles())
                                .parallel()
                                .anyMatch(s -> s.equalsIgnoreCase(role));
                    }

                    @Override
                    public boolean isSecure() {
                        return currentSecurityContext.isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return BEARER;
                    }
                });

            }
        }
    }

    private void refuseWithUnauthorized(ContainerRequestContext requestContext, String msg) {
        Response unAuth = Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .entity("{\"error\":\"" + msg + "\"}")
                .build();
        requestContext.abortWith(unAuth);
    }
}
