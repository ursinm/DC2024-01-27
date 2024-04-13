package controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TweetControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetTweets() {
        given()
                .when()
                .get("/api/v1.0/tweets")
                .then()
                .statusCode(200);
    }

    @Test
    public void testSaveTweetWithWrongTitle() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"authorId\": 9, \"title\": \"x\", \"content\": \"content777\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetTweetByContentCriteria() {
        String uri = "/api/v1.0/tweets/byCriteria?content=" + "content123";
        String firstContent = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].content");

        assertEquals("content123", firstContent);
    }
}
