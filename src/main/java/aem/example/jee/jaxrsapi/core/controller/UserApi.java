package aem.example.jee.jaxrsapi.core.controller;

import aem.example.jee.jaxrsapi.core.dto.UserDTO;
import aem.example.jee.jaxrsapi.core.model.User;
import aem.example.jee.jaxrsapi.core.service.UserService;
import aem.example.jee.jaxrsapi.core.type.Page;
import aem.example.jee.jaxrsapi.core.type.Pageable;
import aem.example.jee.jaxrsapi.core.type.UserSearchForm;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"admin"})
public class UserApi {

    @Inject
    private UserService userService;

    @GET
    public Response getAllUsers(@QueryParam("username") String username, @QueryParam("inRole") String inRole, Pageable<User> pageable) {
        Page<UserDTO> users = userService.findPageByUserSearchForm(UserSearchForm
                .builder().username(username).inRole(inRole).build(), pageable);
        return Response.ok(users).build();
    }

    @POST
    public Response addUser(UserDTO user) {
        user = userService.saveUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
