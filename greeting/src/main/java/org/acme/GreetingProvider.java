package org.acme;

import io.micrometer.core.annotation.Timed;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GreetingProvider {
    @ConfigProperty(name = "message")
    String message;

    @WithSpan
    @Timed("MyGreeting")
    public String getGreeting() {
        return message;
    }
}
