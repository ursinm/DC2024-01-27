package com.example.restapplication.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class StoryControllerTest {

    final String testStory = "{ \"userId\": 1, \"title\": \"title3190\", \"content\": \"content9594\" }";
    final String testStoryUpdated = "\"userId\": 1, \"title\": \"title\", \"content\": \"content9594\" }";
    final String api = "/api/v1.0/storys";
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    void getAll() {
        given().when().get(api).then().statusCode(200);
    }

    @Test
    void getById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(testStory)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", storyId)
                .when()
                .get(api + "/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void delete() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(testStory)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", storyId)
                .when()
                .delete(api + "/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void save() {
        given()
                .contentType(ContentType.JSON)
                .body(testStory)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();
    }

    @Test
    void update() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(testStory)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = response.jsonPath().getLong("id");
        String body = "{ \"id\": " + storyId + testStoryUpdated;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(api)
                .then()
                .statusCode(200)
                .body("title", equalTo("title"));
    }

    @Test
    void getByData() {
    }
}