package com.example.lab1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class StickerControllerTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/stickers").then().statusCode(200);
    }

    @Test
    public void testGetStickerById() {
        Map<String, String> sticker = new HashMap<>();
        sticker.put("storyId", "1");
        sticker.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(sticker)
                .when().post("/api/v1.0/stickers").then()
                .statusCode(201)
                .extract().response();
        System.out.println(response);
        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/stickers/{id}").then()
                .body("storyId", equalTo(1))
                .body("name", equalTo("1111"))
                .statusCode(200);
    }

    @Test
    public void testUpdateStickerById() {
        Map<String, String> sticker = new HashMap<>();
        sticker.put("storyId", "1");
        sticker.put("name", "1111");
        Map<String, String> newSticker = new HashMap<>();
        newSticker.put("storyId", "11");
        newSticker.put("name", "new1111");

        Response response = given()
                .contentType("application/json")
                .body(sticker)
                .when().post("/api/v1.0/stickers").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newSticker.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newSticker)
                .when().put("/api/v1.0/stickers").then()
                .body("storyId", equalTo(11))
                .body("name", equalTo("new1111"))
                .statusCode(200);
    }

    @Test
    public void testDeleteStickerById() {
        Map<String, String> sticker = new HashMap<>();
        sticker.put("storyId", "1");
        sticker.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(sticker)
                .when().post("/api/v1.0/stickers").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/stickers/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostStickerWithBadRequest() {
        Map<String, String> sticker = new HashMap<>();
        sticker.put("storyId", "1");
        sticker.put("name", "1");

        given()
                .contentType("application/json")
                .body(sticker)
                .when().post("/api/v1.0/stickers").then()
                .body("message", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetStickerWithIncorrectId() {
        Map<String, String> sticker = new HashMap<>();
        sticker.put("storyId", "1");
        sticker.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(sticker)
                .when().post("/api/v1.0/stickers").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/stickers/{id}").then().
                body("message", equalTo("Sticker not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }


}
