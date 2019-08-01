package aem.example.jee.jaxrsapi.bussines.web.controller;

import aem.example.jee.jaxrsapi.bussines.model.Book;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookApi {

    @PersistenceContext(unitName = "bookStorePU")
    EntityManager em;

    @GET
    @RolesAllowed({"user"})
    public Response getAllBooks() {
        List<Book> books = em.createNamedQuery("Book.findAll").getResultList();
        return Response.ok(books).build();
    }

    @POST
    @Transactional(Transactional.TxType.REQUIRED)
    @RolesAllowed({"admin"})
    public Response addBook(Book book) {
        em.persist(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }
}
