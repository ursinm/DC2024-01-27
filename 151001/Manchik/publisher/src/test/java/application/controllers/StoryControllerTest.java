package application.controllers;

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
    void getByData() {
    }
}