package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CommentControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetComments() {
        given()
                .when()
                .get("/api/v1.0/comments")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCommentById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/comments")
                .then()
                .statusCode(201)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", commentId)
                .when()
                .get("/api/v1.0/comments/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteComment() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/comments")
                .then()
                .statusCode(201)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", commentId)
                .when()
                .delete("/api/v1.0/comments/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveComment() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/comments")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateComment() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/comments")
                .then()
                .statusCode(201)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");

        String body = "{ \"id\": "+ commentId +", \"content\": \"Updated comment\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/comments")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated comment"));
    }

    @Test
    public void testGetCommentByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/comments/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Comment not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteCommentWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/comments/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The comment has not been deleted"))
                .body("errorCode", equalTo(40003));
    }
}
