package controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorControllerTests {
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

    @Test
    public void testFindAllOrderById() {
        String body = "{ \"login\": \"111\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer authorId1 = response.jsonPath().getInt("id");

        body = "{ \"login\": \"zzzzz-is-very-very-very-very-very-very-long-more-than-64-symbols\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer authorId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/authors?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        Integer content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(authorId2, content);

        given()
                .pathParam("id", authorId1)
                .when()
                .delete("/api/v1.0/authors/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", authorId2)
                .when()
                .delete("/api/v1.0/authors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindAllOrderByLogin() {
        String body = "{ \"login\": \"111\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer authorId1 = response.jsonPath().getInt("id");

        body = "{ \"login\": \"zzzzz-is-very-very-very-very-very-very-long-more-than-64-symbols\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer authorId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/authors?pageNumber=0&pageSize=10&sortBy=login&sortOrder=desc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].login");

        assertEquals("zzzzz-is-very-very-very-very-very-very-long-more-than-64-symbols", content);

        uri = "/api/v1.0/authors?pageNumber=0&pageSize=10&sortBy=login&sortOrder=asc";
        content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].login");

        assertEquals("111", content);

        given()
                .pathParam("id", authorId1)
                .when()
                .delete("/api/v1.0/authors/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", authorId2)
                .when()
                .delete("/api/v1.0/authors/{id}")
                .then()
                .statusCode(204);
    }
}
