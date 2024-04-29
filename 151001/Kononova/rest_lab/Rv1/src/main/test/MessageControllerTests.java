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
        String body = "{ \"content\": \"Test content\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
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

        given()
                .pathParam("id", messageId)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteMessage() {
        String body = "{ \"content\": \"Test content\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
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
    public void testUpdateMessage() {
        String body = "{ \"content\": \"Test content\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(201)
                .extract().response();

        long messageId = response.jsonPath().getLong("id");

        body = "{ \"id\": " + messageId + ", \"content\": \"Updated message\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/messages")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated message"));
        given()
                .pathParam("id", messageId)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetMessageByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/messages/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Message not found!"))
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
                .body("errorMessage", equalTo("Message not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testFindAllOrderByContent(){
        String body = "{ \"content\": \"aaa\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(201)
                .extract().response();

        Integer messageId1 = response.jsonPath().getInt("id");

        body = "{ \"content\": \"zzzzzzzzzzzzz\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(201)
                .extract().response();

        Integer messageId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/messages?pageNumber=0&pageSize=10&sortBy=content&sortOrder=asc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].content");

        assertEquals("aaa", content);
        uri = "/api/v1.0/messages?pageNumber=0&pageSize=10&sortBy=content&sortOrder=desc";
        content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].content");

        assertEquals("zzzzzzzzzzzzz", content);

        given()
                .pathParam("id", messageId1)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", messageId2)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(204);
    }
}
