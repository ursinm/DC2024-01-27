package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LabelControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetLabels() {
        given()
                .when()
                .get("/api/v1.0/labels")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetLabelById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", labelId)
                .when()
                .get("/api/v1.0/labels/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteLabel() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", labelId)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveLabel() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateLabel() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + labelId + ", \"issueId\": 8, \"name\": \"updatedname4529\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/labels")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname4529"));
    }

    @Test
    public void testGetLabelByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/labels/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Label not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteLabelWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The label has not been deleted"))
                .body("errorCode", equalTo(40003));
    }
}

