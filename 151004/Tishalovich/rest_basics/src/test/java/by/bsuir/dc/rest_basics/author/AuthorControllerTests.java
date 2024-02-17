package by.bsuir.dc.rest_basics.author;

import by.bsuir.dc.rest_basics.configuration.MyTestConfiguration;
import by.bsuir.dc.rest_basics.configuration.TestAuthorDao;
import by.bsuir.dc.rest_basics.entities.Author;
import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.entities.dtos.response.AuthorResponseTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
import by.bsuir.dc.rest_basics.services.exceptions.AuthorSubCode;
import by.bsuir.dc.rest_basics.services.impl.AuthorMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Import(MyTestConfiguration.class)
class AuthorControllerTests {

    private static final String uri = "http://localhost:24110/api/v1.0";

    @Autowired
    private TestAuthorDao testAuthorDao;

    @Test
    public void getExistingAuthor() {
        Author newAuthor = new Author(
                "AuthorLogin",
                "Password",
                "FirstName",
                "LastName"
        );

        Optional<Author> savedAuthor = testAuthorDao.save(newAuthor);
        Author author = savedAuthor.orElseThrow();

        AuthorResponseTo createdAuthorResponseTo = RestAssured
                .given()
                .pathParam("id", author.getId())
                .when()
                    .get(uri + "/authors/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(AuthorResponseTo.class);

        Assertions.assertEquals(
                AuthorMapper.INSTANCE.modelToResponse(author),
                createdAuthorResponseTo
        );
    }

    @Test
    public void create() {
        AuthorRequestTo authorRequestTo = new AuthorRequestTo(
                "SomeLogin",
                "SomePassword",
                "First Name",
                "Last Name"
        );

        Long id = testAuthorDao.getNextId();

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

        authorRequestTo.setId(id);
        AuthorResponseTo expectedAuthorResponseTo = TestAuthorMapper
                .INSTANCE.requestToResponse(authorRequestTo);

        Assertions.assertEquals(expectedAuthorResponseTo, authorResponseTo);
    }

    @Test
    public void update() {
        Author newAuthor = new Author(
                "AuthorLogin",
                "Password",
                "FirstName",
                "LastName"
        );

        Author savedAuthor = testAuthorDao
                .save(newAuthor)
                .orElseThrow();

        AuthorRequestTo authorRequestTo = new AuthorRequestTo(
                "UpdatedLogin",
                null,
                "UpdateFirstName",
                null
        );

        AuthorResponseTo authorResponseTo = RestAssured
                .given()
                    .contentType("application/json")
                    .pathParam("id", savedAuthor.getId())
                    .body(authorRequestTo)
                .when()
                    .put(uri + "/authors/{id}")
                .then()
                    .contentType("application/json")
                    .statusCode(HttpStatus.OK.value())
                .extract()
                    .as(AuthorResponseTo.class);

        AuthorResponseTo expectedAuthorResponseTo = new AuthorResponseTo(
                savedAuthor.getId(),
                authorRequestTo.getLogin(),
                authorRequestTo.getFirstName(),
                savedAuthor.getLastName()
        );

        Assertions.assertEquals(expectedAuthorResponseTo, authorResponseTo);
    }

    @Test
    public void deleteExistingAuthor() {
        Author authorForDeleting = new Author(
                "SomeValue",
                "SomeValue",
                "SomeValue",
                "SomeValue"
        );

        Optional<Author> savingRes = testAuthorDao.save(authorForDeleting);
        Author savedAuthor = savingRes.orElseThrow();

        RestAssured
                .given()
                    .pathParam("id", savedAuthor.getId())
                .when()
                    .delete(uri + "/authors/{id}")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deleteNotExistingAuthor() {
        testAuthorDao.clear();

        final Long id = 1L;

        ApiExceptionInfo expectedApiExceptionInfo = new ApiExceptionInfo(
                HttpStatus.NOT_FOUND.value(),
                AuthorSubCode.WRONG_ID.getSubCode(),
                "There is no author with id = " + id
        );

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
        testAuthorDao.clear();

        Author firstAuthor = new Author(
                "FirstAuthorLogin",
                "FirstAuthorPassword",
                "FirstAuthorFirstName",
                "FirstAuthorLastName"
        );

        Optional<Author> firstSavedAuthor = testAuthorDao.save(firstAuthor);

        Author secondAuthor = new Author(
                "SecondAuthorLogin",
                "SecondAuthorPassword",
                "SecondAuthorSecondName",
                "SecondAuthorLastName"
        );

        Optional<Author> secondSavedAuthor = testAuthorDao.save(secondAuthor);

        AuthorResponseTo[] savedAuthors = {
                AuthorMapper.INSTANCE.modelToResponse(firstSavedAuthor.orElseThrow()),
                AuthorMapper.INSTANCE.modelToResponse(secondSavedAuthor.orElseThrow()),
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
