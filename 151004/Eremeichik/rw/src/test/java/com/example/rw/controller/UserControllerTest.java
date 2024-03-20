package com.example.rw.controller;

import com.example.rw.model.dto.user.UserRequestTo;
import com.example.rw.model.dto.user.UserResponseTo;
import com.example.rw.model.entity.implementations.User;
import com.example.rw.service.db_operations.interfaces.UserService;
import com.example.rw.service.dto_converter.interfaces.UserToConverter;
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
class UserControllerTest {

    @SpyBean
    private UserService userService;
    @SpyBean
    private UserToConverter userToConverter;
    @Value("${request.prefix}")
    private String requestPrefix;

    @Test
    void givenValidUserRequestTo_whenCreateUser_thenStatusCreated() {
        String requestUrl = createUserCreationUrl();
        UserRequestTo userRequestTo = new UserRequestTo();
        userRequestTo.setLogin("username");
        userRequestTo.setPassword("my_super_password");
        userRequestTo.setFirstname("First");
        userRequestTo.setLastname("Last");

        Mockito.doNothing().when(userService).save(any());

        Response response = given()
                .contentType(ContentType.JSON)
                .body(userRequestTo)
                .when()
                .post(requestUrl);
        response
                .then()
                .statusCode(201);

        UserResponseTo actualResponse = response.getBody().as(UserResponseTo.class);

        Assertions.assertEquals(userRequestTo.getLogin(), actualResponse.getLogin());
        Assertions.assertEquals(userRequestTo.getFirstname(), actualResponse.getFirstname());
        Assertions.assertEquals(userRequestTo.getLastname(), actualResponse.getLastname());
    }

    @Test
    void givenInvalidUserRequestTo_whenCreateUser_thenStatus400() {
        String requestUrl = createUserCreationUrl();
        UserRequestTo invalidRequest = new UserRequestTo();

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createUserCreationUrl() {
        return String.format("%s/users", requestPrefix);
    }

    @Test
    void givenNoUsers_whenReceiveAllUsers_thenReturnEmptyList() {
        String requestUrl = createReceiveAllUsersUrl();

        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        List<?> actualBody = response.getBody().as(List.class);

        Assertions.assertTrue(actualBody.isEmpty());
    }

    @Test
    void givenUsersList_whenReceiveAllUsers_thenReturnList() {
        String requestUrl = createReceiveAllUsersUrl();
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        List<UserResponseTo> responseToList = new ArrayList<>();
        responseToList.add(new UserResponseTo());

        Mockito.when(userService.findAll()).thenReturn(userList);
        for (int i = 0; i < userList.size(); i++) {
            Mockito.when(userToConverter.convertToDto(userList.get(i)))
                    .thenReturn(responseToList.get(i));
        }

        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        TypeRef<List<UserResponseTo>> typeRef = new TypeRef<>() {};
        List<UserResponseTo> actualBody = response.body().as(typeRef);

        Assertions.assertEquals(responseToList, actualBody);
    }

    private String createReceiveAllUsersUrl() {
        return String.format("%s/users", requestPrefix);
    }

    @Test
    void givenExistingUserId_whenReceiveUserById_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        User user = new User();
        user.setId(1L);
        UserResponseTo responseTo = new UserResponseTo();

        Mockito.doReturn(user).when(userService).findById(id);
        Mockito.when(userToConverter.convertToDto(user)).thenReturn(responseTo);

        Response response = get(requestUrl);
        response
                .then()
                .statusCode(200);

        UserResponseTo actualResponse = response.getBody().as(UserResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenNonExistingUserId_whenReceiveUserById_thenReturn404() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        get(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createFindByIdUrl(Long id) {
        return String.format("%s/users/%d", requestPrefix, id);
    }

    @Test
    void givenValidRequestTo_whenUpdateUser_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createUpdateUserUrl(id);

        UserRequestTo requestTo = new UserRequestTo();
        requestTo.setLogin("newUsername");
        requestTo.setFirstname("firstName");
        requestTo.setLastname("lastName");
        requestTo.setPassword("super_password");
        User user = new User();
        user.setId(id);
        UserResponseTo responseTo = new UserResponseTo();
        responseTo.setLogin(requestTo.getLogin());

        Mockito.doNothing().when(userService).save(user);
        Mockito.when(userToConverter.convertToEntity(requestTo)).thenReturn(user);
        Mockito.when(userToConverter.convertToDto(user)).thenReturn(responseTo);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl);
        response
                .then()
                .statusCode(200);

        UserResponseTo actualResponse = response.getBody().as(UserResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenInvalidRequestTo_whenUpdateUser_thenReturn400() {
        Long id = 1L;
        String requestUrl = createUpdateUserUrl(id);

        UserRequestTo requestTo = new UserRequestTo();

        given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createUpdateUserUrl(Long id) {
        return String.format("%s/user/update/%d", requestPrefix, id);
    }

    @Test
    void givenExistingUserId_whenDeleteUserById_thenReturn204() {
        Long id = 1L;
        String requestUrl = createDeleteUserUrl(id);

        Mockito.doNothing().when(userService).deleteById(id);

        delete(requestUrl)
                .then()
                .statusCode(204);
    }

    @Test
    void givenNonExistingUserId_whenDeleteUserById_thenReturn404() {
        Long id = 1L;
        String requestUrl = createDeleteUserUrl(id);

        delete(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createDeleteUserUrl(Long id) {
        return String.format("%s/user/delete/%d", requestPrefix, id);
    }
}