package com.example.lab2.Controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EditorControllerTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/editors").then().statusCode(200);
    }

    @Test
    public void testGetEditorById() {
        Map<String, String> editor = new HashMap<>();
        editor.put("login", "lion1");
        editor.put("password", "11111111");
        editor.put("firstname", "11");
        editor.put("lastname", "11");

        Response response = given()
                .contentType("application/json")
                .body(editor)
                .when().post("/api/v1.0/editors").then()
                .statusCode(201)
                .extract().response();
        System.out.println(response);
        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/editors/{id}").then()
                .body("login", equalTo("lion1"))
                .body("password", equalTo("11111111"))
                .body("firstname", equalTo("11"))
                .body("lastname", equalTo("11"))
                .statusCode(200);
    }

    @Test
    public void testUpdateEditorById() {
        Map<String, String> editor = new HashMap<>();
        editor.put("login", "lion11");
        editor.put("password", "11111111");
        editor.put("firstname", "11");
        editor.put("lastname", "11");
        Map<String, String> newEditor = new HashMap<>();
        newEditor.put("login", "new11");
        newEditor.put("password", "new11111111");
        newEditor.put("firstname", "new11");
        newEditor.put("lastname", "new11");

        Response response = given()
                .contentType("application/json")
                .body(editor)
                .when().post("/api/v1.0/editors").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newEditor.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newEditor)
                .when().put("/api/v1.0/editors").then()
                .body("login", equalTo("new11"))
                .body("password", equalTo("new11111111"))
                .body("firstname", equalTo("new11"))
                .body("lastname", equalTo("new11"))
                .statusCode(200);
    }

    @Test
    public void testDeleteEditorById() {
        Map<String, String> editor = new HashMap<>();
        editor.put("login", "lion111");
        editor.put("password", "11111111");
        editor.put("firstname", "11");
        editor.put("lastname", "11");


        Response response = given()
                .contentType("application/json")
                .body(editor)
                .when().post("/api/v1.0/editors").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/editors/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostEditorWithBadRequest() {
        Map<String, String> editor = new HashMap<>();
        editor.put("login", "1");
        editor.put("password", "1234567");
        editor.put("firstname", "1");
        editor.put("lastname", "1");

        given()
                .contentType("application/json")
                .body(editor)
                .when().post("/api/v1.0/editors").then()
                .body("message", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetEditorWithIncorrectId() {
        Map<String, String> editor = new HashMap<>();
        editor.put("login", "lion1111");
        editor.put("password", "11111111");
        editor.put("firstname", "11");
        editor.put("lastname", "11");


        Response response = given()
                .contentType("application/json")
                .body(editor)
                .when().post("/api/v1.0/editors").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/editors/{id}").then().
                body("message", equalTo("Editor not found")).
                body("status", equalTo(404))
                .statusCode(404);
    }
}
