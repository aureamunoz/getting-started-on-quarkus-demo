package org.acme;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.MethodProperties;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.Collections;
import java.util.List;

@ResourceProperties(path = "books")
public interface BookRepository extends PanacheEntityResource<Book, Long> {

    @MethodProperties(exposed = false)
    boolean delete(Long id);

    @GET
    @Path("/year/{year}")
    @Produces("application/json")
    default List<Book> findByPublicationYear(@PathParam("year") Integer year) {
        return Book.find("publicationYear = :year", Collections.singletonMap("year", year)).list();
    }
}
