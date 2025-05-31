package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@QuarkusTest
class GreetingResourceMockTest {

    @InjectMock
    GreetingProvider greetingProvider;

    @Test
    void testHelloEndpoint() {
        when(greetingProvider.getGreeting()).thenReturn("Hello from mock");
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from mock"));
    }

}