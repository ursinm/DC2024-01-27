package com.example.rw.controller;

import com.example.rw.model.dto.message.MessageRequestTo;
import com.example.rw.model.dto.message.MessageResponseTo;
import com.example.rw.model.entity.implementations.Message;
import com.example.rw.service.db_operations.interfaces.MessageService;
import com.example.rw.service.dto_converter.interfaces.MessageToConverter;
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
class MessageControllerTest {

    @SpyBean
    private MessageService messageService;
    @SpyBean
    private MessageToConverter messageToConverter;
    @Value("${request.prefix}")
    private String requestPrefix;

    @Test
    void givenValidMessageRequestTo_whenCreateMessage_thenStatusCreated() {
        String requestUrl = createMessageCreationUrl();
        MessageRequestTo messageRequestTo = new MessageRequestTo();
        messageRequestTo.setContent("content");
        messageRequestTo.setNewsId(1L);

        Mockito.doNothing().when(messageService).save(any());

        Response response = given()
                .contentType(ContentType.JSON)
                .body(messageRequestTo)
                .when()
                .post(requestUrl);
        response
                .then()
                .statusCode(201);

        MessageResponseTo actualResponse = response.getBody().as(MessageResponseTo.class);

        Assertions.assertEquals(messageRequestTo.getContent(), actualResponse.getContent());
        Assertions.assertEquals(messageRequestTo.getNewsId(), actualResponse.getNewsId());
    }

    @Test
    void givenInvalidMessageRequestTo_whenCreateMessage_thenStatus400() {
        String requestUrl = createMessageCreationUrl();
        MessageRequestTo invalidRequest = new MessageRequestTo();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createMessageCreationUrl() {
        return String.format("%s/message/create", requestPrefix);
    }

    @Test
    void givenNoMessages_whenReceiveAllMessages_thenReturnEmptyList() {
        String requestUrl = createReceiveAllMessagesUrl();

        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        List<?> actualBody = response.getBody().as(List.class);

        Assertions.assertTrue(actualBody.isEmpty());
    }

    @Test
    void givenMessagesList_whenReceiveAllMessages_thenReturnList() {
        String requestUrl = createReceiveAllMessagesUrl();
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message());
        List<MessageResponseTo> responseToList = new ArrayList<>();
        responseToList.add(new MessageResponseTo());

        Mockito.when(messageService.findAll()).thenReturn(messageList);
        for (int i = 0; i < messageList.size(); i++) {
            Mockito.when(messageToConverter.convertToDto(messageList.get(i)))
                    .thenReturn(responseToList.get(i));
        }

        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        TypeRef<List<MessageResponseTo>> typeRef = new TypeRef<>() {
        };
        List<MessageResponseTo> actualBody = response.body().as(typeRef);

        Assertions.assertEquals(responseToList, actualBody);
    }

    private String createReceiveAllMessagesUrl() {
        return String.format("%s/message/list", requestPrefix);
    }

    @Test
    void givenExistingMessageId_whenReceiveMessageById_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        Message message = new Message();
        message.setId(1L);
        MessageResponseTo responseTo = new MessageResponseTo();

        Mockito.doReturn(message).when(messageService).findById(id);
        Mockito.when(messageToConverter.convertToDto(message)).thenReturn(responseTo);

        Response response = get(requestUrl);
        response
                .then()
                .statusCode(200);

        MessageResponseTo actualResponse = response.getBody().as(MessageResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenNonExistingMessageId_whenReceiveMessageById_thenReturn404() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        get(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createFindByIdUrl(Long id) {
        return String.format("%s/message/%d", requestPrefix, id);
    }

    @Test
    void givenValidRequestTo_whenUpdateMessage_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createUpdateMessageUrl(id);

        MessageRequestTo requestTo = new MessageRequestTo();
        requestTo.setContent("content");
        Message message = new Message();
        message.setId(id);
        MessageResponseTo responseTo = new MessageResponseTo();
        responseTo.setContent(requestTo.getContent());

        Mockito.doNothing().when(messageService).save(message);
        Mockito.when(messageToConverter.convertToEntity(requestTo)).thenReturn(message);
        Mockito.when(messageToConverter.convertToDto(message)).thenReturn(responseTo);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl);
        response
                .then()
                .statusCode(200);

        MessageResponseTo actualResponse = response.getBody().as(MessageResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenInvalidRequestTo_whenUpdateMessage_thenReturn400() {
        Long id = 1L;
        String requestUrl = createUpdateMessageUrl(id);

        MessageRequestTo requestTo = new MessageRequestTo();


        given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createUpdateMessageUrl(Long id) {
        return String.format("%s/message/update/%d", requestPrefix, id);
    }

    @Test
    void givenExistingMessageId_whenDeleteMessageById_thenReturn204() {
        Long id = 1L;
        String requestUrl = createDeleteMessageUrl(id);

        Mockito.doNothing().when(messageService).deleteById(id);

        delete(requestUrl)
                .then()
                .statusCode(204);
    }

    @Test
    void givenNonExistingMessageId_whenDeleteMessageById_thenReturn204() {
        Long id = 1L;
        String requestUrl = createDeleteMessageUrl(id);

        delete(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createDeleteMessageUrl(Long id) {
        return String.format("%s/message/delete/%d", requestPrefix, id);
    }
}