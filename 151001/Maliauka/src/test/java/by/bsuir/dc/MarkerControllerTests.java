package by.bsuir.dc;

import by.bsuir.dc.features.marker.Marker;
import by.bsuir.dc.features.marker.MarkerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.http.HttpStatus.*;

public class MarkerControllerTests extends AbstractContainerTest{
    @LocalServerPort
    private Integer port;

    @Autowired
    MarkerRepository markerRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        markerRepository.deleteAll();
    }

    @Test
    void shouldGetMarker() {
        var marker = new Marker(1L, "marker");
        markerRepository.save(marker);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1.0/markers/1")
                .then()
                .assertThat()
                .statusCode(OK.value());
    }

    @Test
    void shouldReturnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1.0/markers/1")
                .then()
                .assertThat()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void shouldGetAllMarkers() {
        var markers = List.of(
                new Marker(1L, "marker 1"),
                new Marker(2L, "marker 2")
        );
        markerRepository.saveAll(markers);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1.0/markers")
                .then()
                .assertThat()
                .statusCode(OK.value())
                .body(".", hasSize(2))
                .time(lessThan(1500L));
    }

    @Test
    void shouldDoNotAddInvalidMarker() {
        var marker = new Marker(1L, "a");

        given()
                .contentType(ContentType.JSON)
                .body(marker)
                .when()
                .post("/api/v1.0/markers")
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void shouldAddMarker() {
        var marker = new Marker(1L, "marker 1");

        given()
                .contentType(ContentType.JSON)
                .body(marker)
                .when()
                .post("/api/v1.0/markers")
                .then()
                .assertThat()
                .statusCode(CREATED.value());
    }

    @Test
    void shouldDoNotAddDuplicate() {
        var marker = new Marker(1L, "askgh");

        markerRepository.save(marker);

        given()
                .contentType(ContentType.JSON)
                .body(marker)
                .when()
                .post("/api/v1.0/markers")
                .then()
                .assertThat()
                .statusCode(BAD_REQUEST.value());
    }

    @Test
    void shouldDelete() {
        var marker = new Marker(1L, "marker");

        markerRepository.save(marker);

        given()
                .when()
                .delete("/api/v1.0/markers/1")
                .then()
                .statusCode(NO_CONTENT.value());
    }
}
