package org.acme;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        var greeting = greetingProvider.getGreeting();
        String password = IntStream.range(0, 10)
                .mapToObj(x -> rndChar())
                .collect(Collectors.joining(""));
        return greeting + " your pasword is " + password;
    }

    @GET
    @Path("{id}")
    public String getFromDb(@PathParam("id") UUID id) {
        return greetingRepository.findByIdOptional(id).map(Greeting::getText).orElse("not found");
    }

    @POST
    @WithSpan
    public Response createGreeting(String text) {
        Greeting greeting = new Greeting();
        greeting.setText(text);
        greeting.setId(UUID.randomUUID());
        greetingRepository.persist(greeting);
        return Response.ok(greeting.getId()).build();
    }

    @WithSpan
    String rndChar () {
        int rnd = (int) (Math.random() * 52);
        char base = (rnd < 26) ? 'A' : 'a';
        return String.valueOf(base + rnd % 26);
    }
}
