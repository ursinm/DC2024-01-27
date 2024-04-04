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
class TagControllerTest {
    final String api = "/api/v1.0/tags";
    final String storyAPI = "/api/v1.0/storys";
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
                .body("{ \"name\": \"Test content\" }")
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", tagId)
                .when()
                .get(api + "/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void delete() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"Test content\" }")
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", tagId)
                .when()
                .delete(api + "/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void save() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"Test content\" }")
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
                .body("{ \"name\": \"Test content\" }")
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = response.jsonPath().getLong("id");
        String body = "{ \"id\": " + tagId + ", \"name\": \"Updated message\" }";
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(api)
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated message"));
    }

    @Test
    void getByStoryId() {
        Response storyResponse = given()
                .contentType(ContentType.JSON)
                .body("{ \"userId\": 5, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post(storyAPI)
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = storyResponse.jsonPath().getLong("id");

        String body = "{ \"name\": \"Test content\", \"storyId\":  " + storyId + "}";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();


        given()
                .pathParam("id", storyId)
                .when()
                .get(api + "/story/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test content"));
    }
}