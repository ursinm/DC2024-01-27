package com.example.rw.controller;

import com.example.rw.model.dto.sticker.StickerRequestTo;
import com.example.rw.model.dto.sticker.StickerResponseTo;
import com.example.rw.model.entity.implementations.Sticker;
import com.example.rw.service.db_operations.interfaces.StickerService;
import com.example.rw.service.dto_converter.interfaces.StickerToConverter;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:/properties/test/test.properties")
class StickerControllerTest {

    @SpyBean
    private StickerService stickerService;
    @SpyBean
    private StickerToConverter stickerToConverter;
    @Value("${request.prefix}")
    private String requestPrefix;

    @Test
    void givenValidStickerRequestTo_whenCreateSticker_thenStatusCreated() {
        String requestUrl = createStickerCreationUrl();
        StickerRequestTo stickerRequestTo = new StickerRequestTo();
        stickerRequestTo.setName("name");

        Mockito.doNothing().when(stickerService).save(any());

        Response response = given()
                .contentType(ContentType.JSON)
                .body(stickerRequestTo)
                .when()
                .post(requestUrl);
        response
                .then()
                .statusCode(201);

        StickerResponseTo actualResponse = response.getBody().as(StickerResponseTo.class);

        Assertions.assertEquals(stickerRequestTo.getName(), actualResponse.getName());
    }

    @Test
    void givenInvalidStickerRequestTo_whenCreateSticker_thenStatus400() {
        String requestUrl = createStickerCreationUrl();
        StickerRequestTo invalidRequest = new StickerRequestTo();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createStickerCreationUrl() {
        return String.format("%s/sticker/create", requestPrefix);
    }

    @Test
    void givenNoStickers_whenReceiveAllStickers_thenReturnEmptyList() {
        String requestUrl = createReceiveAllStickersUrl();

        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        List<?> actualBody = response.getBody().as(List.class);

        Assertions.assertTrue(actualBody.isEmpty());
    }

    @Test
    void givenStickersList_whenReceiveAllStickers_thenReturnList() {
        String requestUrl = createReceiveAllStickersUrl();
        List<Sticker> stickerList = new ArrayList<>();
        stickerList.add(new Sticker());
        List<StickerResponseTo> responseToList = new ArrayList<>();
        responseToList.add(new StickerResponseTo());

        Mockito.when(stickerService.findAll()).thenReturn(stickerList);
        for (int i = 0; i < stickerList.size(); i++) {
            Mockito.when(stickerToConverter.convertToDto(stickerList.get(i)))
                    .thenReturn(responseToList.get(i));
        }

        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        TypeRef<List<StickerResponseTo>> typeRef = new TypeRef<>() {
        };
        List<StickerResponseTo> actualBody = response.body().as(typeRef);

        Assertions.assertEquals(responseToList, actualBody);
    }

    private String createReceiveAllStickersUrl() {
        return String.format("%s/sticker/list", requestPrefix);
    }

    @Test
    void givenExistingStickerId_whenReceiveStickerById_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        Sticker sticker = new Sticker();
        sticker.setId(1L);
        StickerResponseTo responseTo = new StickerResponseTo();

        Mockito.doReturn(sticker).when(stickerService).findById(id);
        Mockito.when(stickerToConverter.convertToDto(sticker)).thenReturn(responseTo);

        Response response = get(requestUrl);
        response
                .then()
                .statusCode(200);

        StickerResponseTo actualResponse = response.getBody().as(StickerResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenNonExistingStickerId_whenReceiveStickerById_thenReturn404() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        get(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createFindByIdUrl(Long id) {
        return String.format("%s/sticker/%d", requestPrefix, id);
    }

    @Test
    void givenValidRequestTo_whenUpdateSticker_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createUpdateStickerUrl(id);

        StickerRequestTo requestTo = new StickerRequestTo();
        requestTo.setName("name");
        Sticker sticker = new Sticker();
        sticker.setId(id);
        StickerResponseTo responseTo = new StickerResponseTo();
        responseTo.setName(requestTo.getName());

        Mockito.doNothing().when(stickerService).save(sticker);
        Mockito.when(stickerToConverter.convertToEntity(requestTo)).thenReturn(sticker);
        Mockito.when(stickerToConverter.convertToDto(sticker)).thenReturn(responseTo);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl);
        response
                .then()
                .statusCode(200);

        StickerResponseTo actualResponse = response.getBody().as(StickerResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenInvalidRequestTo_whenUpdateSticker_thenReturn400() {
        Long id = 1L;
        String requestUrl = createUpdateStickerUrl(id);

        StickerRequestTo requestTo = new StickerRequestTo();

        given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createUpdateStickerUrl(Long id) {
        return String.format("%s/sticker/update/%d", requestPrefix, id);
    }

    @Test
    void givenExistingStickerId_whenDeleteStickerById_thenReturn204() {
        Long id = 1L;
        String requestUrl = createDeleteStickerUrl(id);

        Mockito.doNothing().when(stickerService).deleteById(id);

        delete(requestUrl)
                .then()
                .statusCode(204);
    }

    @Test
    void givenNonExistingStickerId_whenDeleteStickerById_thenReturn204() {
        Long id = 1L;
        String requestUrl = createDeleteStickerUrl(id);

        delete(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createDeleteStickerUrl(Long id) {
        return String.format("%s/sticker/delete/%d", requestPrefix, id);
    }
}