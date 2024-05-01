package com.example.lab1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class CommentControllerTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/comments").then().statusCode(200);
    }


    @Test
    public void testGetCommentById() {
        Map<String, String> comment = new HashMap<>();
        comment.put("commentId", "1");
        comment.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(comment)
                .when().post("/api/v1.0/comments").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/comments/{id}").then().
                body("commentId", equalTo(1))
                .body("content", equalTo("1111")).statusCode(200);
    }

    @Test
    public void testUpdateCommentById() {
        Map<String, String> comment = new HashMap<>();
        comment.put("commentId", "1");
        comment.put("content", "1111");
        Map<String, String> newComment = new HashMap<>();
        newComment.put("id", "0");
        newComment.put("commentId", "11");
        newComment.put("content", "new1111");

        Response response = given()
                .contentType("application/json")
                .body(comment)
                .when().post("/api/v1.0/comments").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newComment.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newComment)
                .when().put("/api/v1.0/comments").then()
                .body("commentId", equalTo(11))
                .body("content", equalTo("new1111")).statusCode(200);
    }

    @Test
    public void testDeleteCommentById() {
        Map<String, String> comment = new HashMap<>();
        comment.put("commentId", "1");
        comment.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(comment)
                .when().post("/api/v1.0/comments").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/comments/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostCommentWithBadRequest() {
        Map<String, String> comment = new HashMap<>();
        comment.put("commentId", "1");
        comment.put("content", "1");

        given()
                .contentType("application/json")
                .body(comment)
                .when().post("/api/v1.0/comments").then()
                .body("message", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetCommentWithIncorrectId() {
        Map<String, String> comment = new HashMap<>();
        comment.put("commentId", "1");
        comment.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(comment)
                .when().post("/api/v1.0/comments").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/comments/{id}").then().
                body("message", equalTo("Comment not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }
}
