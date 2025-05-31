package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@TestProfile(GreetingResourceOtherPropertyTest.FromTest.class)
@QuarkusTest
class GreetingResourceOtherPropertyTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from test profile"));
    }

    public static class FromTest implements QuarkusTestProfile {
        public Map<String, String> getConfigOverrides() {
            return Map.of("message", "Hello from test profile");
        }
    }

}