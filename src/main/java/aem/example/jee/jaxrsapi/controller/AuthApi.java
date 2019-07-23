package aem.example.jee.jaxrsapi.controller;

import aem.example.jee.jaxrsapi.dto.LoginDTO;
import aem.example.jee.jaxrsapi.dto.TokenDTO;
import aem.example.jee.jaxrsapi.dto.UserDTO;
import aem.example.jee.jaxrsapi.service.AuthService;
import aem.example.jee.jaxrsapi.service.TokenService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthApi {
    @Inject
    private AuthService authService;

    @Inject
    private TokenService tokenService;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(LoginDTO ldto) {
        Optional<UserDTO> userOptional = authService.authenticateUser(ldto.getUsername(), ldto.getPassword());
        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get();
            TokenDTO tokenDTO = tokenService.generateCredentials(userDTO.getUsername(), userDTO.getRoles());
            return Response.ok(tokenDTO).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .entity("{\"error\":\"Invalid credentials. Login failed.\"}")
                .build();

    }

}
