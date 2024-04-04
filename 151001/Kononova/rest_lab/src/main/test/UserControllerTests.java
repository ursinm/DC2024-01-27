import io.restassured.RestAssured;
import io.restassured.response.Response;
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
    public void testGetUserById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"user8\", \"password\": \"pass6459\", \"firstname\": \"user88\", \"lastname\": \"user888\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201)
                .extract().response();

        long userId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", userId)
                .when()
                .get("/api/v1.0/users/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"user8\", \"password\": \"pass6459\", \"firstname\": \"user88\", \"lastname\": \"user888\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201)
                .extract().response();

        long userId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", userId)
                .when()
                .delete("/api/v1.0/users/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"user8\", \"password\": \"pass6459\", \"firstname\": \"user88\", \"lastname\": \"user888\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateUser() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"user8\", \"password\": \"pass6459\", \"firstname\": \"user88\", \"lastname\": \"user888\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201)
                .extract().response();

        long userId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + userId + ", \"login\": \"newUser8\", \"password\": \"pass6459\", \"firstname\": \"newUser88\", \"lastname\": \"newUser888\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/users")
                .then()
                .statusCode(200)
                .body("login", equalTo("newUser8"));
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
                .body("errorMessage", equalTo("The User has not been deleted"))
                .body("errorCode", equalTo(40003));
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

    @Test
    public void testGetUserByIssueId() {
        Response userResponse = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"user8\", \"password\": \"pass6459\", \"firstname\": \"user88\", \"lastname\": \"user888\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201)
                .extract().response();

        long userId = userResponse.jsonPath().getLong("id");
        String body = "{ \"userId\": "+ userId +", \"title\": \"dana\", \"content\": \"kononova\" }";

        Response issueResponse = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = issueResponse.jsonPath().getLong("id");

        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/users/byIssue/{id}")
                .then()
                .statusCode(200)
                .body("login", equalTo("user8"));
    }

    @Test
    public void testGetUserByIssueIdWithWrongArgument() {
        given()
                .pathParam("id", 8888)
                .when()
                .get("/api/v1.0/users/byIssue/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Issue not found!"))
                .body("errorCode", equalTo(40004));
    }
}
