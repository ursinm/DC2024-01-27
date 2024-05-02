import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MessageControllerTests {

    private final Header header = new Header("Accept-Language","ru,ru-RU;q=0.9,en;q=0.8,en-US;q=0.7,uk;q=0.6,und;q=0.5,de;q=0.4");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24130;
    }

    @Test
    public void testGetMessages() {
        given()
                .when()
                .header(header)
                .get("/api/v1.0/messages")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetMessageById() {
        String body = "{\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"content6588\"\n" +
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .header(header)
                .body(body)
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(200)
                .extract().response();

        long messageId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", messageId)
                .header(header)
                .when()
                .get("/api/v1.0/messages/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", messageId)
                .header(header)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteMessage() {
        String body = "{\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"content6588\"\n" +
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .header(header)
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(200)
                .extract().response();

        long messageId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", messageId)
                .header(header)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateMessage() {
        String body = "{\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"content6588\"\n" +
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .header(header)
                .when()
                .post("/api/v1.0/messages")
                .then()
                .statusCode(200)
                .extract().response();

        long messageId = response.jsonPath().getLong("id");

        body = "{ \"id\": " + messageId + ",\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"Updated message\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .header(header)
                .when()
                .put("/api/v1.0/messages")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated message"));

        given()
                .pathParam("id", messageId)
                .header(header)
                .when()
                .delete("/api/v1.0/messages/{id}")
                .then()
                .statusCode(200);
    }
}