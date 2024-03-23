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
public class MessageControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetMessages() {
        given()
                .when()
                .get("/api/v1.0/messages")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetMessageById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(201)
                .extract().response();

        long messageId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", messageId)
                .when()
                .get("/api/v1.0/messages/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteMessage() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(201)
                .extract().response();

        long messageId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", messageId)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveMessage() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateMessage() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(201)
                .extract().response();

        long messageId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + messageId + ", \"content\": \"Updated message\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/messages")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated message"));
    }

    @Test
    public void testGetMessageByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/messages/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("message not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteMessageWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The Message has not been deleted"))
                .body("errorCode", equalTo(40003));
    }
}
