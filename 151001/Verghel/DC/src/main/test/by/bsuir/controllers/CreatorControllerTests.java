package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CreatorControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetcreators() {
        given()
                .when()
                .get("/api/v1.0/creators")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCreatorById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"Creator2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        long CreatorId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", CreatorId)
                .when()
                .get("/api/v1.0/creators/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteCreator() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"Creator2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        long CreatorId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", CreatorId)
                .when()
                .delete("/api/v1.0/creators/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveCreator() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"Creator2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateCreator() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"Creator2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        long CreatorId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + CreatorId + ", \"login\": \"updatedCreator109\", \"password\": \"updatedPass5907\", \"firstname\": \"updatedFirstname7007\", \"lastname\": \"updatedLastname3768\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/creators")
                .then()
                .statusCode(200)
                .body("login", equalTo("updatedCreator109"));
    }

    @Test
    public void testGetCreatorByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/creators/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Creator not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteCreatorWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/creators/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The Creator has not been deleted"))
                .body("errorCode", equalTo(40003));
    }

    @Test
    public void testSaveCreatorWithWrongLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"x\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(400);
    }
}
