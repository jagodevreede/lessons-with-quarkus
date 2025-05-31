package org.acme;

import io.quarkus.runtime.annotations.ConfigItem;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

    @Inject
    GreetingProvider greetingProvider;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return greetingProvider.getGreeting();
    }
}
