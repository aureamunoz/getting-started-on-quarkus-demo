package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@DisabledOnOs(OS.WINDOWS)
@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given().when().get("/hello").then().statusCode(200).body(is("Hello World"));
    }

}
