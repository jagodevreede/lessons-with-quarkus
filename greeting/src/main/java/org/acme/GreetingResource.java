package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@Transactional
public class GreetingResource {

    @Inject
    GreetingProvider greetingProvider;

    @Inject
    GreetingRepository greetingRepository;

    @GET
    public String hello() {
        return greetingProvider.getGreeting();
    }

    @GET
    @Path("{id}")
    public String getFromDb(@PathParam("id") UUID id) {
        return greetingRepository.findByIdOptional(id).map(Greeting::getText).orElse("not found");
    }

    @POST
    public Response createGreeting(String text) {
        Greeting greeting = new Greeting();
        greeting.setText(text);
        greeting.setId(UUID.randomUUID());
        greetingRepository.persist(greeting);
        return Response.ok(greeting.getId()).build();
    }
}
