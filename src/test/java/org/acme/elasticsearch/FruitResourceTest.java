package org.acme.elasticsearch;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class FruitResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/fruits")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

    @Test
    public void testIndex() {
        var id = UUID.randomUUID().toString();
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(new Fruit(id, "testName", "testColol"))
                .post("/fruits")
                .then().statusCode(201);

        given()
                .when()
                .get("/fruits/" + id)
                .then().statusCode(200);
    }
}