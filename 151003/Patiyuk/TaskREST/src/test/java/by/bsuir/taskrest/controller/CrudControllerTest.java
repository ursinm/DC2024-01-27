package by.bsuir.taskrest.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.RestAssured.given;

abstract class CrudControllerTest<TReq, TRes> extends ControllerTest {

    protected abstract TReq getRandomRequestTo();

    protected abstract TReq getRandomRequestTo(Long id);

    protected abstract String getPath();

    protected abstract void assertReqAndResEquals(TReq request, TRes response);

    protected abstract Long getId(TRes response);


    @Test
    void create_ReturnsStatus201() {
        TReq request = getRandomRequestTo();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void create_ReturnsCreated() {
        TReq request = getRandomRequestTo();

        TRes created = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        assertReqAndResEquals(request, created);
    }

    @Test
    void createAndDelete_ReturnsStatus204() {
        TReq request = getRandomRequestTo();

        Integer id = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .path("id");

        given()
                .when()
                .delete(getPath() + "/" + id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createAndGetAndDelete_ReturnsStatus204() {
        TReq request = getRandomRequestTo();

        Integer id = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .path("id");

        given()
                .when()
                .get(getPath() + "/" + id)
                .then()
                .statusCode(HttpStatus.OK.value());

        given()
                .when()
                .delete(getPath() + "/" + id)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createAndGet_ReturnsCreated() {
        TReq request = getRandomRequestTo();

        TRes created = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        TRes actual = given()
                .when()
                .get(getPath() + "/" + getId(created))
                .then()
                .extract()
                .as(getResponseClass());

        assertReqAndResEquals(request, actual);

        given()
                .when()
                .delete(getPath() + "/" + getId(created))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createAndGetAllAndDelete_ReturnsStatus204() {
        List<TReq> requests = List.of(getRandomRequestTo(), getRandomRequestTo());

        List<TRes> created = requests.stream()
                .map(request -> given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(getPath())
                        .then()
                        .extract()
                        .as(getResponseClass()))
                .toList();

        given()
                .when()
                .get(getPath())
                .then()
                .statusCode(HttpStatus.OK.value());

        created.forEach(response -> given()
                .when()
                .delete(getPath() + "/" + getId(response))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void createAndGetAll_ReturnsCreated() {
        List<TReq> requests = List.of(getRandomRequestTo(), getRandomRequestTo());

        List<TRes> expected = requests.stream()
                .map(request -> given()
                        .contentType(ContentType.JSON)
                        .body(request)
                        .when()
                        .post(getPath())
                        .then()
                        .extract()
                        .as(getResponseClass()))
                .toList();

        List<TRes> actual = given()
                .when()
                .get(getPath())
                .then()
                .extract()
                .jsonPath()
                .getList(".", getResponseClass());

        actual.forEach(response -> assertReqAndResEquals(requests.get(actual.indexOf(response)), response));

        expected.forEach(response -> given()
                .when()
                .delete(getPath() + "/" + getId(response))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void createAndUpdate_ReturnsUpdated() {
        TReq request = getRandomRequestTo();

        TRes created = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        TReq updated = getRandomRequestTo(getId(created));

        TRes actual = given()
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        assertReqAndResEquals(updated, actual);

        given()
                .when()
                .delete(getPath() + "/" + getId(actual))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createAndUpdateAndGet_ReturnsUpdated() {
        TReq request = getRandomRequestTo();

        TRes created = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        TReq updated = getRandomRequestTo(getId(created));

        TRes actual = given()
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        assertReqAndResEquals(updated, actual);

        TRes get = given()
                .when()
                .get(getPath() + "/" + getId(actual))
                .then()
                .extract()
                .as(getResponseClass());

        assertReqAndResEquals(updated, get);

        given()
                .when()
                .delete(getPath() + "/" + getId(actual))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createAndGet_IncorrectId_ReturnsStatus404() {
        TReq request = getRandomRequestTo();

        TRes created = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        given()
                .when()
                .get(getPath() + "/" + getId(created) + 1)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given()
                .when()
                .delete(getPath() + "/" + getId(created))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createAndUpdate_IncorrectId_ReturnsStatus404() {
        TReq request = getRandomRequestTo();

        TRes created = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        TReq updated = getRandomRequestTo(getId(created) + 1);

        given()
                .contentType(ContentType.JSON)
                .body(updated)
                .when()
                .put(getPath())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        given()
                .when()
                .delete(getPath() + "/" + getId(created))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void createAndDelete_IncorrectId_ReturnsStatus404() {
        TReq request = getRandomRequestTo();

        TRes created = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(getPath())
                .then()
                .extract()
                .as(getResponseClass());

        given()
                .when()
                .delete(getPath() + "/" + getId(created))
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .when()
                .delete(getPath() + "/" + getId(created))
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @SuppressWarnings("unchecked")
    private Class<TRes> getResponseClass() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<TRes>) paramType.getActualTypeArguments()[1];
    }
}
