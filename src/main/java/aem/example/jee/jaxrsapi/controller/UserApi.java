package aem.example.jee.jaxrsapi.controller;

import aem.example.jee.jaxrsapi.dto.UserSearchForm;
import aem.example.jee.jaxrsapi.model.User;
import aem.example.jee.jaxrsapi.repository.UserRepository;
import aem.example.jee.jaxrsapi.util.PasswordEncoder;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.logging.Logger;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin"})
public class UserApi {

    private static final Logger LOGGER = Logger.getLogger(UserApi.class.getName());

    @Inject
    UserRepository userRepository;

    @Context
    SecurityContext securityContext;

    @GET
    public Response getAllUsers(@QueryParam("username") String username, @QueryParam("inRole") String inRole) {
        List<User> users = userRepository
                .findByUserSearchForm(UserSearchForm.builder().username(username).inRole(inRole).build());
        return Response.ok(users).build();
    }

    @POST
    public Response addUser(User user) {
        user.setPassword(PasswordEncoder.encodeSha256(user.getPassword()));
        userRepository.saveUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }


}
