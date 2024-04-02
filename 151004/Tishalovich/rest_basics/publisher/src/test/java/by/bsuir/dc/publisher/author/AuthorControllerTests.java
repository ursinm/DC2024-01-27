package by.bsuir.dc.publisher.author;

import by.bsuir.dc.publisher.configuration.MyTestConfiguration;
import by.bsuir.dc.rest_basics.dal.AuthorDao;
import by.bsuir.dc.rest_basics.entities.Author;
import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.impl.mappers.AuthorMapper;
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
class AuthorControllerTests {

    @Autowired
    private String uri;

    @MockBean
    private AuthorDao mockAuthorDao;

    @Autowired
    private AuthorMapper authorMapper;

    @Test
    public void getExistingAuthor() {
        Long authorId = 1L;

        Author newAuthor = new Author(
                "AuthorLogin",
                "Password",
                "FirstName",
                "LastName"
        );

        Mockito.when(mockAuthorDao.findById(authorId)).thenReturn(Optional.of(newAuthor));

        AuthorResponseTo createdAuthorResponseTo = RestAssured
                .given()
                .pathParam("id", authorId)
                .when()
                    .get(uri + "/authors/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(AuthorResponseTo.class);

        Assertions.assertEquals(
                authorMapper.modelToResponse(newAuthor),
                createdAuthorResponseTo
        );
    }

    @Test
    public void create() {
        AuthorRequestTo authorRequestTo = new AuthorRequestTo(
                null,
                "SomeLogin",
                "SomePassword",
                "First Name",
                "Last Name"
        );

        Author expectedAuthor = authorMapper.requestToModel(authorRequestTo);
        expectedAuthor.setId(1L);

        Mockito.when(mockAuthorDao
                .save(Mockito.any()))
                .thenReturn(
                        Optional.of(expectedAuthor));

        AuthorResponseTo authorResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(authorRequestTo)
                .when()
                    .post(uri + "/authors")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.CREATED.value())
                .extract()
                    .as(AuthorResponseTo.class);

        Assertions.assertEquals(
                authorMapper.modelToResponse(expectedAuthor), authorResponseTo);
    }

    @Test
    public void update() {
        Author updatedAuthor = new Author(
                "UpdateAuthorLogin",
                "Password",
                "UpdateFirstName",
                "LastName"
        );

        AuthorRequestTo authorRequestTo = new AuthorRequestTo(
                1L,
                updatedAuthor.getLogin(),
                null,
                updatedAuthor.getFirstName(),
                null
        );

        Author updateBodyAuthor = authorMapper.requestToModel(authorRequestTo);

        Mockito.when(mockAuthorDao.save(eq(updateBodyAuthor)))
                .thenReturn(updatedAuthor);

        AuthorResponseTo authorResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .body(authorRequestTo)
                .when()
                    .put(uri + "/authors")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(AuthorResponseTo.class);

        Assertions.assertEquals(
                authorMapper.modelToResponse(updatedAuthor), authorResponseTo);
    }

    @Test
    public void deleteExistingAuthor() {
        Author authorForDeleting = new Author(
                1L,
                "SomeValue",
                "SomeValue",
                "SomeValue",
                "SomeValue"
        );

        Mockito.when(mockAuthorDao.delete(authorForDeleting.getId()))
                        .thenReturn(Optional.of(authorForDeleting));

        RestAssured
                .given()
                    .pathParam("id", authorForDeleting.getId())
                .when()
                    .delete(uri + "/authors/{id}")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteNotExistingAuthor() {
        final Long id = 1L;

        ApiExceptionInfo expectedApiExceptionInfo = new ApiExceptionInfo(
                HttpStatus.NOT_FOUND.value(),
                GeneralSubCode.WRONG_ID.getSubCode(),
                GeneralSubCode.WRONG_ID.getMessage()
        );

        Mockito.when(mockAuthorDao.delete(eq(id)))
                .thenReturn(Optional.empty());

        ApiExceptionInfo apiExceptionInfo = RestAssured
                .given()
                    .pathParam("id", id)
                .when()
                    .delete(uri + "/authors/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                    .as(ApiExceptionInfo.class);

        Assertions.assertEquals(expectedApiExceptionInfo, apiExceptionInfo);
    }

    @Test
    public void getAll() {
        Author firstAuthor = new Author(
                "FirstAuthorLogin",
                "FirstAuthorPassword",
                "FirstAuthorFirstName",
                "FirstAuthorLastName"
        );

        Author secondAuthor = new Author(
                "SecondAuthorLogin",
                "SecondAuthorPassword",
                "SecondAuthorSecondName",
                "SecondAuthorLastName"
        );

        Mockito.when(mockAuthorDao.getAll())
                .thenReturn(List.of(firstAuthor, secondAuthor));

        AuthorResponseTo[] savedAuthors = {
                authorMapper.modelToResponse(firstAuthor),
                authorMapper.modelToResponse(secondAuthor),
        };

        AuthorResponseTo[] authors = RestAssured
                .given()
                .when()
                    .get(uri + "/authors")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(AuthorResponseTo[].class);

        Assertions.assertArrayEquals(savedAuthors, authors);
    }

}
