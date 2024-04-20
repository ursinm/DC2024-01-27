package com.example.publicator.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class LabelControllerTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/labels").then().statusCode(200);
    }

    @Test
    public void testGetLabelById() {
        Map<String, String> label = new HashMap<>();
        label.put("issueId", "1");
        label.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(label)
                .when().post("/api/v1.0/labels").then()
                .statusCode(201)
                .extract().response();
        System.out.println(response);
        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/labels/{id}").then()
                //.body("issueId", equalTo(1))
                .body("name", equalTo("1111"))
                .statusCode(200);
    }

    @Test
    public void testUpdateLabelById() {
        Map<String, String> label = new HashMap<>();
        label.put("issueId", "1");
        label.put("name", "1111");
        Map<String, String> newLabel = new HashMap<>();
        newLabel.put("issueId", "1");
        newLabel.put("name", "new1111");

        Response response = given()
                .contentType("application/json")
                .body(label)
                .when().post("/api/v1.0/labels").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newLabel.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newLabel)
                .when().put("/api/v1.0/labels").then()
                //.body("issueId", equalTo(1))
                .body("name", equalTo("new1111"))
                .statusCode(200);
    }

    @Test
    public void testDeleteLabelById() {
        Map<String, String> label = new HashMap<>();
        label.put("issueId", "1");
        label.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(label)
                .when().post("/api/v1.0/labels").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/labels/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostLabelWithBadRequest() {
        Map<String, String> label = new HashMap<>();
        label.put("issueId", "1");
        label.put("name", "1");

        given()
                .contentType("application/json")
                .body(label)
                .when().post("/api/v1.0/labels").then()
                .body("message", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetLabelWithIncorrectId() {
        Map<String, String> label = new HashMap<>();
        label.put("issueId", "1");
        label.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(label)
                .when().post("/api/v1.0/labels").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/labels/{id}").then().
                body("message", equalTo("Label not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }
}
