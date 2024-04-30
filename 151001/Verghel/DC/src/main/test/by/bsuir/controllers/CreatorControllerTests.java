package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CreatorControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetCreators() {
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
                .body("{ \"login\": \"newCreator12\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        long creatorId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", creatorId)
                .when()
                .get("/api/v1.0/creators/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", creatorId)
                .when()
                .delete("/api/v1.0/creators/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteCreator() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"newCreator1111\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        long creatorId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", creatorId)
                .when()
                .delete("/api/v1.0/creators/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateCreator() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"newCreator111\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        long creatorId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + creatorId + ", \"login\": \"updatedCreator1091\", \"password\": \"updatedPass5907\", \"firstname\": \"updatedFirstname7007\", \"lastname\": \"updatedLastname3768\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/creators")
                .then()
                .statusCode(200)
                .body("login", equalTo("updatedCreator1091"));

        given()
                .pathParam("id", creatorId)
                .when()
                .delete("/api/v1.0/creators/{id}")
                .then()
                .statusCode(204);
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
                .body("errorMessage", equalTo("Creator not found!"))
                .body("errorCode", equalTo(40004));
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

    @Test
    public void testFindAllOrderById(){
        String body = "{ \"login\": \"111\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        Integer creatorId1 = response.jsonPath().getInt("id");

        body = "{ \"login\": \"zzzzz-is-very-very-very-very-very-very-long-more-than-64-symbols\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/creators")
                .then()
                .statusCode(201)
                .extract().response();

        Integer creatorId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/creators?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        Integer content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(creatorId2, content);

        given()
                .pathParam("id", creatorId1)
                .when()
                .delete("/api/v1.0/creators/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", creatorId2)
                .when()
                .delete("/api/v1.0/creators/{id}")
                .then()
                .statusCode(204);
    }
}
