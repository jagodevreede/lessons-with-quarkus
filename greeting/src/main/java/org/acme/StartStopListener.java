package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class StartStopListener {

    void onStart(@Observes StartupEvent ev) {
        Log.info("Starting application");
    }

    void onStop(@Observes ShutdownEvent ev) {
        Log.info("Application is stopping...");
    }
}
