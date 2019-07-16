package aem.example.jee.jaxrsapi.controller;

import aem.example.jee.jaxrsapi.dto.LoginDTO;
import aem.example.jee.jaxrsapi.dto.TokenDTO;
import aem.example.jee.jaxrsapi.model.Role;
import aem.example.jee.jaxrsapi.model.User;
import aem.example.jee.jaxrsapi.service.AuthService;
import aem.example.jee.jaxrsapi.util.JWTService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthApi {
    @Inject
    private AuthService authService;

    @POST
    @PermitAll
    public Response login(LoginDTO ldto) {
        Optional<User> userOptional = authService.authenticateUser(ldto.getJ_username(), ldto.getJ_password());
        return userOptional
                .map(user -> Response
                        .ok(TokenDTO
                                .builder()
                                .token(JWTService
                                        .generateToken(
                                                user.getUsername(),
                                                user.getRoles()
                                                        .stream()
                                                        .map(Role::getName)
                                                        .collect(Collectors.toList())
                                                        .toArray(new String[user.getRoles().size()])))
                                .build())
                        .build()
                ).orElse(Response.status(Response.Status.FORBIDDEN).build());

    }

}
