package com.example.lab2.Controller;
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
public class StoryControllerTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/storys").then().statusCode(200);
    }

    @Test
    public void testGetStoryById() {
        Map<String, String> story = new HashMap<>();
        story.put("editorId", "1");
        story.put("title", "1111");
        story.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(story)
                .when().post("/api/v1.0/storys").then()
                .statusCode(201)
                .extract().response();
        System.out.println(response);
        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/storys/{id}").then()
                .body("editorId", equalTo(1))
                .body("title", equalTo("1111"))
                .body("content", equalTo("1111"))
                .statusCode(200);
    }

    @Test
    public void testUpdateStoryById() {
        Map<String, String> story = new HashMap<>();
        story.put("editorId", "1");
        story.put("title", "1112");
        story.put("content", "1111");
        Map<String, String> newStory = new HashMap<>();
        newStory.put("editorId", "1");
        newStory.put("title", "new1112");
        newStory.put("content", "new1111");
        Response response = given()
                .contentType("application/json")
                .body(story)
                .when().post("/api/v1.0/storys").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newStory.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newStory)
                .when().put("/api/v1.0/storys").then()
                .body("editorId", equalTo(1))
                .body("title", equalTo("new1112"))
                .body("content", equalTo("new1111"))
                .statusCode(200);
    }

    @Test
    public void testDeleteStoryById() {
        Map<String, String> story = new HashMap<>();
        story.put("editorId", "1");
        story.put("title", "1113");
        story.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(story)
                .when().post("/api/v1.0/storys").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/storys/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostStoryWithBadRequest() {
        Map<String, String> story = new HashMap<>();
        story.put("editorId", "1");
        story.put("title", "1");
        story.put("content", "1");

        given()
                .contentType("application/json")
                .body(story)
                .when().post("/api/v1.0/storys").then()
                .body("message", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetStoryWithIncorrectId() {
        Map<String, String> story = new HashMap<>();
        story.put("editorId", "1");
        story.put("title", "1114");
        story.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(story)
                .when().post("/api/v1.0/storys").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/storys/{id}").then().
                body("message", equalTo("Story not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }
}
