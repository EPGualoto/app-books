package com.programacion.distribuida.book.clients;

import com.programacion.distribuida.book.service.dto.AuthorDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/api/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@RegisterRestClient(baseUri = "stork://authors-api")

public interface AuthorRestClient {

    @GET
    @Path("/find/{isbn}")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(fallbackMethod = "findByIsbnFallback")
    public List<AuthorDto> findByIsbn(@PathParam("isbn") String isbn);

    default List<AuthorDto> findByIsbnFallback(String isbn) {

        var aut = new AuthorDto();
        aut.setId(0);
        aut.setName("---NO DISPONIBLE °°°");
        return List.of(aut);

    }

}
