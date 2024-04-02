package by.bsuir.dc.publisher.message;

import by.bsuir.dc.publisher.configuration.MyTestConfiguration;
import by.bsuir.dc.rest_basics.dal.impl.MessageDao;
import by.bsuir.dc.rest_basics.entities.Message;
import by.bsuir.dc.rest_basics.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.MessageResponseTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.MessageMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Import(MyTestConfiguration.class)
class MessageControllerTests {

    @Autowired
    private String uri;

    @MockBean
    private MessageDao mockMessageDao;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void getExistingMessage() {
        Message message = new Message(
                1L,
                2L,
                "SomeContent"
        );

        Mockito.when(mockMessageDao.getById(message.getId())).thenReturn(Optional.of(message));

        MessageResponseTo createdMessageResponseTo = RestAssured
                .given()
                    .pathParam("id", message.getId())
                .when()
                    .get(uri + "/messages/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(MessageResponseTo.class);

        Assertions.assertEquals(
                messageMapper.modelToResponse(message),
                createdMessageResponseTo
        );
    }

    @Test
    public void create() {
        MessageRequestTo messageRequestTo = new MessageRequestTo(
                null,
                2L,
                "SomeContent"
        );

        Message expectedMessage = messageMapper.requestToModel(messageRequestTo);
        expectedMessage.setId(1L);

        Mockito.when(mockMessageDao
                        .save(Mockito.any()))
                .thenReturn(
                        Optional.of(expectedMessage));

        MessageResponseTo messageResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(messageRequestTo)
                .when()
                    .post(uri + "/messages")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .as(MessageResponseTo.class);

        Assertions.assertEquals(
                messageMapper.modelToResponse(expectedMessage), messageResponseTo);
    }

    @Test
    public void update() {
        Message updatedMessage = new Message(
                1L,
                2L,
                "UpdatedContent"
        );

        MessageRequestTo messageRequestTo = new MessageRequestTo(
                updatedMessage.getId(),
                updatedMessage.getStoryId(),
                updatedMessage.getContent()
        );

        Message updateBodyMessage = messageMapper.requestToModel(messageRequestTo);

        Mockito.when(mockMessageDao.update(eq(updateBodyMessage)))
                .thenReturn(Optional.of(updatedMessage));

        MessageResponseTo messageResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(messageRequestTo)
                .when()
                    .put(uri + "/messages")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(MessageResponseTo.class);

        Assertions.assertEquals(
                messageMapper.modelToResponse(updatedMessage), messageResponseTo);
    }

    @Test
    public void deleteExistingMessage() {
        Message messageForDeleting = new Message(
                1L,
                2L,
                "SomeValue"
        );

        Mockito.when(mockMessageDao.delete(messageForDeleting.getId()))
                .thenReturn(Optional.of(messageForDeleting));

        RestAssured
                .given()
                    .pathParam("id", messageForDeleting.getId())
                .when()
                    .delete(uri + "/messages/{id}")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteNotExistingMessage() {
        final Long id = 1L;

        ApiExceptionInfo expectedApiExceptionInfo = new ApiExceptionInfo(
                HttpStatus.NOT_FOUND.value(),
                GeneralSubCode.WRONG_ID.getSubCode(),
                GeneralSubCode.WRONG_ID.getMessage()
        );

        Mockito.when(mockMessageDao.delete(eq(id)))
                .thenReturn(Optional.empty());

        ApiExceptionInfo apiExceptionInfo = RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .delete(uri + "/messages/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                    .as(ApiExceptionInfo.class);

        Assertions.assertEquals(expectedApiExceptionInfo, apiExceptionInfo);
    }

    @Test
    public void getAll() {
        Message firstMessage = new Message(
                1L,
                2L,
                "FirstContent"
        );

        Message secondMessage = new Message(
                2L,
                3L,
                "SecondContent"
        );

        Mockito.when(mockMessageDao.getAll())
                .thenReturn(List.of(firstMessage, secondMessage));

        MessageResponseTo[] savedMessages = {
                messageMapper.modelToResponse(firstMessage),
                messageMapper.modelToResponse(secondMessage),
        };

        MessageResponseTo[] messages = RestAssured
                .given()
                .when()
                    .get(uri + "/messages")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(MessageResponseTo[].class);

        Assertions.assertArrayEquals(savedMessages, messages);
    }

}
