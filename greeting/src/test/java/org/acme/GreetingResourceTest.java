package org.acme;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTest
@TestTransaction
class GreetingResourceTest {
    @Order(1)
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from config"));
    }

    @Order(2)
    @Test
    void testGreetingEndpoint() {
        given()
                .when().get("/hello/" + UUID.randomUUID())
                .then()
                .statusCode(200)
                .body(is("not found"));
    }

    @Order(3)
    @Test
    void testCreateGreetingEndpoint() {
        String responseBody =
                given()
                        .body("from test")
                        .when().post("/hello")
                        .then()
                        .statusCode(200)
                        .extract().asString();

        given()
                .when().get("/hello/" + responseBody)
                .then()
                .statusCode(200)
                .body(is("from test"));
    }

    @Inject
    GreetingRepository greetingRepository;

    //@Disabled
    @Order(4)
    @Test
    void testCreateGreetingEndpoint2() {
        UUID id = UUID.randomUUID();
        createGreeting(id);

        given()
                .when().get("/hello/" + id.toString())
                .then()
                .statusCode(200)
                .body(is("from test insert"));
    }

    void createGreeting(UUID id) {
        Greeting greeting = new Greeting();
        greeting.setText("from test insert");
        greeting.setId(id);
        greetingRepository.persist(greeting);
    }

}