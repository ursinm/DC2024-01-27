package by.bsuir.taskrest.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ControllerTest {
    @BeforeAll
    static void setUp() {
        RestAssured.port = 24110;
        RestAssured.basePath = "/api/v1.0";
    }
}
