package application.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorControllerTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetAuthors() {
        given()
                .when()
                .get("/api/v1.0/authors")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteAuthor() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"author333\", \"password\": \"pass3333\", \"firstname\": \"firstname3333\", \"lastname\": \"lastname3333\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        long authorId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", authorId)
                .when()
                .delete("/api/v1.0/authors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveAuthorWithWrongLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"x\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(400);
    }
}