package aem.example.jee.jaxrsapi.controller;

import aem.example.jee.jaxrsapi.model.User;
import aem.example.jee.jaxrsapi.repository.UserRepository;
import aem.example.jee.jaxrsapi.type.Pageable;
import aem.example.jee.jaxrsapi.type.UserSearchForm;
import aem.example.jee.jaxrsapi.util.PasswordEncoder;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Arrays;
import java.util.Collection;
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

        List<Pageable.Order> orders = order
                .stream()
                .filter(o -> !o.isEmpty())
                .filter(o -> o.split(",").length <= 2)
                .filter(o -> {
                    try {
                        return User.class.getDeclaredField(o.split(",")[0]) != null
                                && !User.class.getDeclaredField(o.split(",")[0]).getType().isAssignableFrom(Collection.class);
                    } catch (NoSuchFieldException e) {
                        return false;
                    }
                })
                .map(o -> o.split(","))
                .map(strings -> {
                    String property = strings[0];
                    Pageable.Sort sort = Pageable.Sort.ASC;
                    if (strings[1] != null) {
                        sort = Arrays.stream(Pageable.Sort.values())
                                .parallel()
                                .filter(sort1 -> sort1.name().equalsIgnoreCase(strings[1]))
                                .findAny()
                                .orElse(Pageable.Sort.ASC);
                    }
                    return new Pageable.Order(property, sort);
                })
                .collect(Collectors.toList());

        Pageable pageable = Pageable.builder().page(page).size(size).order(orders).build();

        List<User> users = userRepository
                .findByUserSearchForm(UserSearchForm.builder().username(username).inRole(inRole).build(), pageable);
        return Response.ok(users).build();
    }

    @POST
    public Response addUser(User user) {
        user.setPassword(PasswordEncoder.encodeSha256(user.getPassword()));
        userRepository.saveUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }


}
