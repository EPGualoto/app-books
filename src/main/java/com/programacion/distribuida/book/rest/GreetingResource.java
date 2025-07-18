package com.programacion.distribuida.book.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/helloB")
public class GreetingResource {

    //  http://localhost:8080/api/helloB
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World Books";
    }
}
