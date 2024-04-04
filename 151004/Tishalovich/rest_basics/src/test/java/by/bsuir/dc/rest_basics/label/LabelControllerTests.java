package by.bsuir.dc.rest_basics.label;

import by.bsuir.dc.rest_basics.configuration.MyTestConfiguration;
import by.bsuir.dc.rest_basics.dal.impl.LabelDao;
import by.bsuir.dc.rest_basics.entities.Label;
import by.bsuir.dc.rest_basics.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.LabelResponseTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.LabelMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Import(MyTestConfiguration.class)
public class LabelControllerTests {

    @Autowired
    private String uri;

    @MockBean
    private LabelDao mockLabelDao;

    @Autowired
    private LabelMapper labelMapper;

    @Test
    public void getExistingLabel() {
        Label label = new Label(
                1L,
                "LabelName"
        );

        Mockito.when(mockLabelDao.getById(label.getId())).thenReturn(Optional.of(label));

        LabelResponseTo createdLabelResponseTo = RestAssured
                .given()
                    .pathParam("id", label.getId())
                .when()
                    .get(uri + "/labels/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(LabelResponseTo.class);

        Assertions.assertEquals(
                labelMapper.modelToResponse(label),
                createdLabelResponseTo
        );
    }

    @Test
    public void create() {
        LabelRequestTo labelRequestTo = new LabelRequestTo(
                null,
                "SomeName"
        );

        Label expectedLabel = labelMapper.requestToModel(labelRequestTo);

        final Long labelId = 1L;

        Mockito.when(mockLabelDao
                        .save(eq(expectedLabel)))
                .thenReturn(
                        Optional.of(new Label(labelId, expectedLabel.getName())));

        LabelResponseTo labelResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(labelRequestTo)
                .when()
                    .post(uri + "/labels")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .as(LabelResponseTo.class);

        expectedLabel.setId(labelId);

        Assertions.assertEquals(
                labelMapper.modelToResponse(expectedLabel), labelResponseTo);
    }

    @Test
    public void update() {
        final Long id = 1L;

        Label updatedLabel = new Label(
                id,
                "SomeName"
        );

        LabelRequestTo labelRequestTo = new LabelRequestTo(
                id,
                updatedLabel.getName()
        );

        Label updateBodyLabel = labelMapper.requestToModel(labelRequestTo);

        Mockito.when(mockLabelDao.update(eq(updateBodyLabel)))
                .thenReturn(Optional.of(updatedLabel));

        LabelResponseTo labelResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(labelRequestTo)
                .when()
                    .put(uri + "/labels")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(LabelResponseTo.class);

        Assertions.assertEquals(
                labelMapper.modelToResponse(updatedLabel), labelResponseTo);
    }

    @Test
    public void deleteExistingLabel() {
        Label labelForDeleting = new Label(
                1L,
                "SomeLabel"
        );

        Mockito.when(mockLabelDao.delete(labelForDeleting.getId()))
                .thenReturn(Optional.of(labelForDeleting));

        RestAssured
                .given()
                    .pathParam("id", labelForDeleting.getId())
                .when()
                    .delete(uri + "/labels/{id}")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteNotExistingLabel() {
        final Long id = 1L;

        ApiExceptionInfo expectedApiExceptionInfo = new ApiExceptionInfo(
                HttpStatus.NOT_FOUND.value(),
                GeneralSubCode.WRONG_ID.getSubCode(),
                GeneralSubCode.WRONG_ID.getMessage()
        );

        Mockito.when(mockLabelDao.delete(eq(id)))
                .thenReturn(Optional.empty());

        ApiExceptionInfo apiExceptionInfo = RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .delete(uri + "/labels/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                    .as(ApiExceptionInfo.class);

        Assertions.assertEquals(expectedApiExceptionInfo, apiExceptionInfo);
    }

    @Test
    public void getAll() {
        Label firstLabel = new Label(
                1L,
                "FirstLabelName"
        );

        Label secondLabel = new Label(
                2L,
                "SecondLabelName"
        );

        Mockito.when(mockLabelDao.getAll())
                .thenReturn(List.of(firstLabel, secondLabel));

        LabelResponseTo[] savedLabels = {
                labelMapper.modelToResponse(firstLabel),
                labelMapper.modelToResponse(secondLabel),
        };

        LabelResponseTo[] labels = RestAssured
                .given()
                .when()
                    .get(uri + "/labels")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(LabelResponseTo[].class);

        Assertions.assertArrayEquals(savedLabels, labels);
    }

}
