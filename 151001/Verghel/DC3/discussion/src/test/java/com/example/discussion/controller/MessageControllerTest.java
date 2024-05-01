package com.example.discussion.controller;
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

public class MessageControllerTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24130;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/messages").then().statusCode(200);
    }

    @Test
    public void testGetMessageById() {
        Map<String, String> message = new HashMap<>();
        message.put("issueId", "1");
        message.put("id", "1");
        message.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(message)
                .header("Accept-Language", "RU")
                .when().post("/api/v1.0/messages").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/messages/{id}").then().
                body("issueId", equalTo(1))
                .body("content", equalTo("1111")).statusCode(200);
    }

    @Test
    public void testUpdateMessageById() {
        Map<String, String> message = new HashMap<>();
        message.put("issueId", "1");
        message.put("content", "1111");
        Map<String, String> newMessage = new HashMap<>();
        newMessage.put("id", "0");
        newMessage.put("issueId", "1");
        newMessage.put("content", "new1111");

        Response response = given()
                .contentType("application/json")
                .body(message)
                .header("Accept-Language", "RU")
                .when().post("/api/v1.0/messages").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newMessage.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newMessage)
                .header("Accept-Language", "RU")
                .when().put("/api/v1.0/messages")
                .then()
                .body("issueId", equalTo(1))
                .body("content", equalTo("new1111")).statusCode(200);
    }

    @Test
    public void testDeleteMessageById() {
        Map<String, String> message = new HashMap<>();
        message.put("issueId", "1");
        message.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(message)
                .header("Accept-Language", "RU")
                .when().post("/api/v1.0/messages").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/messages/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostMessageWithBadRequest() {
        Map<String, String> message = new HashMap<>();
        message.put("issueId", "1");
        message.put("content", "1");

        given()
                .contentType("application/json")
                .body(message)
                .header("Accept-Language", "RU")
                .when().post("/api/v1.0/messages").then()
                //.body("message", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetMessageWithIncorrectId() {
        Map<String, String> message = new HashMap<>();
        message.put("issueId", "1");
        message.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(message)
                .header("Accept-Language", "RU")
                .when().post("/api/v1.0/messages").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/messages/{id}").then().
                body("message", equalTo("Message not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }
}
