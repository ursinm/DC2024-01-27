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
class UserControllerTest {
    final String testUser = "{ \"login\": \"editor2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
    final String testUserWithId = ", \"login\": \"UPDATED EDITOR\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
    final String api = "/api/v1.0/users";
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
                .body(testUser)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long userId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", userId)
                .when()
                .get(api + "/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void delete() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(testUser)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long userId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", userId)
                .when()
                .delete(api + "/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void save() {
        given()
                .contentType(ContentType.JSON)
                .body(testUser)
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
                .body(testUser)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = response.jsonPath().getLong("id");
        String body = "{ \"id\": " + tagId + testUserWithId;
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(api)
                .then()
                .statusCode(200)
                .body("login", equalTo("UPDATED EDITOR"));
    }

    @Test
    void getByStoryId() {
        Response userResponse = given()
                .contentType(ContentType.JSON)
                .body(testUser)
                .when()
                .post(api)
                .then()
                .statusCode(201)
                .extract().response();
        long userId = userResponse.jsonPath().getLong("id");
        Response storyResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"userId\" : \"" + userId + "\" , " + "\"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post(storyAPI)
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = storyResponse.jsonPath().getLong("id");



        given()
                .pathParam("id", storyId)
                .when()
                .get(api + "/story/{id}")
                .then()
                .statusCode(200)
                .body("login", equalTo("editor2019"));
    }
}