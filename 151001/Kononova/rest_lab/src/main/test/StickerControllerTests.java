import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
                .body("{ \"issueId\": 9, \"name\": \"user8\" }")
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
    }

    @Test
    public void testDeleteSticker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 9, \"name\": \"user8\" }")
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
    public void testSaveSticker() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 9, \"name\": \"user8\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateSticker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 9, \"name\": \"user8\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + stickerId + ", \"issueId\": 9, \"name\": \"user8\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/stickers")
                .then()
                .statusCode(200)
                .body("name", equalTo("user8"));
    }

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

    @Test
    public void testDeleteStickerWithWrongArgument() {
        given()
                .pathParam("id", 8888)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The Sticker has not been deleted"))
                .body("errorCode", equalTo(40003));
    }
}

