package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GreetingProvider {
    @ConfigProperty(name = "message")
    String message;

    public String getGreeting() {
        return message;
    }
}
