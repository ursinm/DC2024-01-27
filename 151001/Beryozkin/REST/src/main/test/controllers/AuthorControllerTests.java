package controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetAuthors() {
        given()
                .when()
                .get("/api/v1.0/authors")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetAuthorById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"author111\", \"password\": \"pass1111\", \"firstname\": \"firstname111\", \"lastname\": \"lastname111\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        long authorId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", authorId)
                .when()
                .get("/api/v1.0/authors/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteAuthor() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"author333\", \"password\": \"pass3333\", \"firstname\": \"firstname3333\", \"lastname\": \"lastname3333\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        long authorId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", authorId)
                .when()
                .delete("/api/v1.0/authors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveAuthor() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"author345\", \"password\": \"pass6734\", \"firstname\": \"firstname4455\", \"lastname\": \"lastname4444\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateAuthor() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"author888\", \"password\": \"pass888\", \"firstname\": \"firstname676\", \"lastname\": \"lastname788\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        long authorId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + authorId + ", \"login\": \"updatedAuthor109\", \"password\": \"updatedPass5907\", \"firstname\": \"updatedFirstname7007\", \"lastname\": \"updatedLastname3768\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/authors")
                .then()
                .statusCode(200)
                .body("login", equalTo("updatedAuthor109"));
    }

    @Test
    public void testGetAuthorByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/authors/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("author not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteAuthorWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/authors/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The Author has not been deleted"))
                .body("errorCode", equalTo(40003));
    }

    @Test
    public void testSaveAuthorWithWrongLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"x\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetAuthorByTweetId() {
        Response authorResponse = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"author2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/authors")
                .then()
                .statusCode(201)
                .extract().response();

        long authorId = authorResponse.jsonPath().getLong("id");
        String body = "{ \"authorId\": "+ authorId +", \"title\": \"title3190\", \"content\": \"content9594\" }";

        Response tweetResponse = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        long tweetId = tweetResponse.jsonPath().getLong("id");

        given()
                .pathParam("id", tweetId)
                .when()
                .get("/api/v1.0/authors/byTweet/{id}")
                .then()
                .statusCode(200)
                .body("login", equalTo("author2019"));
    }

    @Test
    public void testGetAuthorByTweetIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/authors/byTweet/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("tweet not found!"))
                .body("errorCode", equalTo(40004));
    }
}
