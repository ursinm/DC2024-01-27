package controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
    public void testGetTweetById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"authorId\": 7, \"title\": \"title11\", \"content\": \"content11\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        long tweetId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", tweetId)
                .when()
                .get("/api/v1.0/tweets/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteTweet() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"authorId\": 7, \"title\": \"title333\", \"content\": \"content333\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        long tweetId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", tweetId)
                .when()
                .delete("/api/v1.0/tweets/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveTweet() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"authorId\": 7, \"title\": \"title777\", \"content\": \"content888\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateTweet() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"authorId\": 7, \"title\": \"title8989\", \"content\": \"content8989\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        long tweetId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + tweetId + ", \"authorId\": 7, \"title\": \"updatedTitle699\", \"content\": \"updatedContent9402\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/tweets")
                .then()
                .statusCode(200)
                .body("title", equalTo("updatedTitle699"));
    }

    @Test
    public void testDeleteTweetWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/tweets/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The Tweet has not been deleted"))
                .body("errorCode", equalTo(40003));
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
    public void testGetTweetByTitleAndContentCriteria() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"authorId\": 90, \"title\": \"title44\", \"content\": \"content44\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        String title = response.jsonPath().getString("title");
        String content = response.jsonPath().getString("content");
        String uri = "/api/v1.0/tweets/byCriteria?title=" + title + "&content=" + content;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .body("title", equalTo(title))
                .body("content", equalTo(content));
    }

    @Test
    public void testGetTweetByWrongTitleAndContentCriteria() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"authorId\": 9, \"title\": \"title3456\", \"content\": \"content3456\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        String title = response.jsonPath().getString("title") + "titleee";
        String content = response.jsonPath().getString("content") + "contenttt";
        String uri = "/api/v1.0/tweets/byCriteria?title=" + title + "&content=" + content;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(400)
                .body("errorCode", equalTo(40005))
                .body("errorMessage", equalTo("tweet not found!"));
    }
}
