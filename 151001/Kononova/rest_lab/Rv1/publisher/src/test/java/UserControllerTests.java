import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTests {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetUsers() {
        given()
                .when()
                .get("/api/v1.0/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUserWithWrongId() {
        given()
                .pathParam("id", 8888)
                .when()
                .get("/api/v1.0/users/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("User not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteUserWithWrongId() {
        given()
                .pathParam("id", 8888)
                .when()
                .delete("/api/v1.0/users/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("User not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testSaveUserWithWrongLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"x\", \"password\": \"pass6459\", \"firstname\": \"user88\", \"lastname\": \"user888\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(400);
    }
}
