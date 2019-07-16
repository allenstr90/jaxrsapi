package aem.example.jee.jaxrsapi.controller;

import aem.example.jee.jaxrsapi.dto.UserDTO;
import aem.example.jee.jaxrsapi.model.User;
import aem.example.jee.jaxrsapi.repository.UserRepository;
import aem.example.jee.jaxrsapi.type.Pageable;
import aem.example.jee.jaxrsapi.type.UserSearchForm;
import aem.example.jee.jaxrsapi.util.PaginatorUtil;
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
import java.util.stream.Collectors;

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
    public Response getAllUsers(
            @QueryParam("username") String username,
            @QueryParam("inRole") String inRole,
            @QueryParam("page") int page,
            @DefaultValue("5") @QueryParam("size") int size,
            @QueryParam("order") List<String> order) {

        Pageable pageable = PaginatorUtil.build(page, size, order, User.class);
        List<UserDTO> users = userRepository
                .findByUserSearchForm(UserSearchForm.builder().username(username).inRole(inRole).build(), pageable)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        return Response.ok(users).build();
    }

    @POST
    public Response addUser(User user) {
        user.setPassword(PasswordEncoder.encodeSha256(user.getPassword()));
        userRepository.saveUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }


}
