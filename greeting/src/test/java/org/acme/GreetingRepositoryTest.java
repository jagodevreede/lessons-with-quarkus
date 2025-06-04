package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
class GreetingRepositoryTest {

    @Inject
    GreetingRepository greetingRepository;

    @Test
    void testPersist() {
        Greeting greeting = new Greeting();
        greeting.setText("Hello");
        greeting.setId(UUID.randomUUID());

        greetingRepository.persist(greeting);

        Greeting formDB = greetingRepository.findById(greeting.getId());

        assertNotNull(formDB);
    }

    @Test
    void testPersistThenCount() {
        long count = greetingRepository.count();

        assertTrue(count == 0);

        Greeting greeting = new Greeting();
        greeting.setText("Hello");
        greeting.setId(UUID.randomUUID());

        greetingRepository.persist(greeting);

        count = greetingRepository.count();

        assertTrue(count == 1);
    }

}