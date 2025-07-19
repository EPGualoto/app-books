package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.AuthorDto;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BooksRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.stork.Stork;
import io.smallrye.stork.api.Service;
import io.smallrye.stork.api.ServiceInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional
public class BookRest {

    @Inject
    BooksRepository booksRepository;

    @Inject
    ModelMapper mapper;

    @Inject
    @RestClient
    private AuthorRestClient authorRestClient;

    @GET
    @Path("/{isbn}")
    public Response findById(@PathParam("isbn") String isbn) {
        var obj = booksRepository.findByIsbnBook(isbn);
        if (obj.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        BookDto dto = new BookDto();
        mapper.map(obj.get(), dto);

        try {
            var authors = authorRestClient.findByBook(isbn)
                    .stream()
                    .map(AuthorDto::getName)
                    .toList();
            dto.setAuthors(authors);
        } catch (Exception e) {
            dto.setAuthors(Collections.emptyList());
        }

        return Response.ok(dto).build();
    }

    @GET
    public Response findAll() {
        var list = booksRepository.streamAll()
                .map(book -> {
                    var dto = new BookDto();
                    mapper.map(book, dto);
                    return dto;
                })
                .map(book -> {
                    try {
                        var authors = authorRestClient.findByBook(book.getIsbn())
                                .stream()
                                .map(AuthorDto::getName)
                                .toList();
                        book.setAuthors(authors);
                        return book;
                    } catch (Exception e) {
                        book.setAuthors(Collections.emptyList());
                        return book;
                    }
                })
                .toList();

        return Response.ok(list).build();
    }

    @POST
    public Response insert(Book book) {
        booksRepository.persist(book);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, Book book) {
        if (booksRepository.findByIdOptional(isbn).isPresent()) {
            booksRepository.update(isbn, book);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn) {
        if (booksRepository.findByIdOptional(isbn).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            booksRepository.deleteLineItemsByIsbn(isbn);
            booksRepository.deleteBookAuthorsById(isbn);
            booksRepository.deleteInventoryByIsbn(isbn);
            booksRepository.deleteById(isbn);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity("Error al eliminar: " + e.getMessage()).build();
        }
    }
}
