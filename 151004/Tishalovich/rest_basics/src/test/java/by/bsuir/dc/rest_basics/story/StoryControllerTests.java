package by.bsuir.dc.rest_basics.story;

import by.bsuir.dc.rest_basics.configuration.MyTestConfiguration;
import by.bsuir.dc.rest_basics.dal.impl.StoryDao;
import by.bsuir.dc.rest_basics.entities.Story;
import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.StoryResponseTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.StoryMapper;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
public class StoryControllerTests {

    @Autowired
    private String uri;

    @MockBean
    private StoryDao mockStoryDao;

    @Autowired
    private StoryMapper storyMapper;

    @Test
    public void getExistingStory() {
        final Long storyId = 1L;

        Story newStory = new Story(
                1L,
                "SomeTitle",
                "SomeContent",
                null,
                null
        );

        Mockito.when(mockStoryDao.getById(storyId))
                .thenReturn(Optional.of(newStory));

        StoryResponseTo createdStoryResponseTo = RestAssured
                .given()
                    .pathParam("id", storyId)
                .when()
                    .get(uri + "/storys/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(StoryResponseTo.class);

        Assertions.assertEquals(
                storyMapper.modelToResponse(newStory),
                createdStoryResponseTo
        );
    }

    @Test
    public void create() {
        StoryRequestTo storyRequestTo = new StoryRequestTo(
                null,
                10L,
                "SomeTitle",
                "SomeContent",
                null,
                null
        );

        Story expectedStory = storyMapper.requestToModel(storyRequestTo);
        expectedStory.setId(1L);

        Mockito.when(mockStoryDao
                        .save(Mockito.any()))
                .thenReturn(
                        Optional.of(expectedStory));

        StoryResponseTo storyResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(storyRequestTo)
                .when()
                    .post(uri + "/storys")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .as(StoryResponseTo.class);

        Assertions.assertEquals(
                storyMapper.modelToResponse(expectedStory), storyResponseTo);
    }

    @Test
    public void update() {
        Story updatedStory = new Story(
                1L,
                1L,
                "SomeTitle",
                null,
                null,
                null
        );

        StoryRequestTo storyRequestTo = new StoryRequestTo(
                updatedStory.getId(),
                null,
                updatedStory.getTitle(),
                null,
                null,
                null
        );

        Story updateBodyStory = storyMapper.requestToModel(storyRequestTo);

        Mockito.when(mockStoryDao.update(eq(updateBodyStory)))
                .thenReturn(Optional.of(updatedStory));

        StoryResponseTo storyResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(storyRequestTo)
                .when()
                    .put(uri + "/storys")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(StoryResponseTo.class);

        Assertions.assertEquals(
                storyMapper.modelToResponse(updatedStory), storyResponseTo);
    }

    @Test
    public void deleteExistingStory() {
        Story storyForDeleting = new Story(
                1L,
                1L,
                "SomeTitle",
                "SomeContent",
                null,
                null
        );

        Mockito.when(mockStoryDao.delete(storyForDeleting.getId()))
                .thenReturn(Optional.of(storyForDeleting));

        RestAssured
                .given()
                    .pathParam("id", storyForDeleting.getId())
                .when()
                    .delete(uri + "/storys/{id}")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteNotExistingStory() {
        final Long id = 1L;

        ApiExceptionInfo expectedApiExceptionInfo = new ApiExceptionInfo(
                HttpStatus.NOT_FOUND.value(),
                GeneralSubCode.WRONG_ID.getSubCode(),
                GeneralSubCode.WRONG_ID.getMessage()
        );

        Mockito.when(mockStoryDao.delete(eq(id)))
                .thenReturn(Optional.empty());

        ApiExceptionInfo apiExceptionInfo = RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .delete(uri + "/storys/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                    .as(ApiExceptionInfo.class);

        Assertions.assertEquals(expectedApiExceptionInfo, apiExceptionInfo);
    }

    @Test
    public void getAll() {
        Story firstStory = new Story(
                1L,
                2L,
                "FirstStoryLogin",
                "FirstStoryPassword",
                null,
                null
        );

        Story secondStory = new Story(
                2L,
                3L,
                "SecondStory",
                "SomeContent",
                null,
                null
        );

        Mockito.when(mockStoryDao.getAll())
                .thenReturn(List.of(firstStory, secondStory));

        StoryResponseTo[] savedStorys = {
                storyMapper.modelToResponse(firstStory),
                storyMapper.modelToResponse(secondStory),
        };

        StoryResponseTo[] storys = RestAssured
                .given()
                .when()
                    .get(uri + "/storys")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(StoryResponseTo[].class);

        Assertions.assertArrayEquals(savedStorys, storys);
    }

}
