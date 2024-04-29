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
public class StickerControllerTests {
    
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetStickers() {
        given()
                .when()
                .get("/api/v1.0/stickers")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetStickerById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", stickerId)
                .when()
                .get("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(200);
        given()
                .pathParam("id", stickerId)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteSticker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", stickerId)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateSticker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + stickerId + ", \"issueId\": 8, \"name\": \"updatedname45295\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/stickers")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname45295"));

        given()
                .pathParam("id", stickerId)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);    }

    @Test
    public void testGetStickerByIdWithWrongArgument() {
        given()
                .pathParam("id", 8888)
                .when()
                .get("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Sticker not found!"))
                .body("errorCode", equalTo(40004));
    }
}

