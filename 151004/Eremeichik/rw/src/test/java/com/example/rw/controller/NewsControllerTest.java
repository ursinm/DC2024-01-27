package com.example.rw.controller;

import com.example.rw.model.dto.news.NewsRequestTo;
import com.example.rw.model.dto.news.NewsResponseTo;
import com.example.rw.model.entity.implementations.News;
import com.example.rw.service.db_operations.interfaces.NewsService;
import com.example.rw.service.dto_converter.interfaces.NewsToConverter;
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
class NewsControllerTest {

    @SpyBean
    private NewsService newsService;
    @SpyBean
    private NewsToConverter newsToConverter;
    @Value("${request.prefix}")
    private String requestPrefix;

    @Test
    void givenValidNewsRequestTo_whenCreateNews_thenStatusCreated() {
        String requestUrl = createNewsCreationUrl();
        NewsRequestTo newsRequestTo = new NewsRequestTo();
        newsRequestTo.setContent("content");
        newsRequestTo.setTitle("title");

        Mockito.doNothing().when(newsService).save(any());

        Response response = given()
                .contentType(ContentType.JSON)
                .body(newsRequestTo)
                .when()
                .post(requestUrl);
        response
                .then()
                .statusCode(201);

        NewsResponseTo actualResponse = response.getBody().as(NewsResponseTo.class);

        Assertions.assertEquals(newsRequestTo.getContent(), actualResponse.getContent());
        Assertions.assertEquals(newsRequestTo.getTitle(), actualResponse.getTitle());
    }

    @Test
    void givenInvalidNewsRequestTo_whenCreateNews_thenStatus400() {
        String requestUrl = createNewsCreationUrl();
        NewsRequestTo invalidRequest = new NewsRequestTo();
        given()
                .contentType(ContentType.JSON)
                .body(invalidRequest)
                .when()
                .post(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createNewsCreationUrl() {
        return String.format("%s/news/create", requestPrefix);
    }

    @Test
    void givenNoNews_whenReceiveAllNews_thenReturnEmptyList() {
        String requestUrl = createReceiveAllNewsUrl();
        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        List<?> actualBody = response.getBody().as(List.class);

        Assertions.assertTrue(actualBody.isEmpty());
    }

    @Test
    void givenNewsList_whenReceiveAllNews_thenReturnList() {
        String requestUrl = createReceiveAllNewsUrl();
        List<News> newsList = new ArrayList<>();
        newsList.add(new News());
        List<NewsResponseTo> responseToList = new ArrayList<>();
        responseToList.add(new NewsResponseTo());
        Mockito.when(newsService.findAll()).thenReturn(newsList);
        for (int i = 0; i < newsList.size(); i++) {
            Mockito.when(newsToConverter.convertToDto(newsList.get(i)))
                    .thenReturn(responseToList.get(i));
        }

        Response response = get(requestUrl);

        response
                .then()
                .statusCode(200);

        TypeRef<List<NewsResponseTo>> typeRef = new TypeRef<>() {
        };
        List<NewsResponseTo> actualBody = response.body().as(typeRef);

        Assertions.assertEquals(responseToList, actualBody);
    }

    private String createReceiveAllNewsUrl() {
        return String.format("%s/news/list", requestPrefix);
    }

    @Test
    void givenExistingNewsId_whenReceiveNewsById_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        News news = new News();
        news.setId(1L);
        NewsResponseTo responseTo = new NewsResponseTo();

        Mockito.doReturn(news).when(newsService).findById(id);
        Mockito.when(newsToConverter.convertToDto(news)).thenReturn(responseTo);

        Response response = get(requestUrl);
        response
                .then()
                .statusCode(200);

        NewsResponseTo actualResponse = response.getBody().as(NewsResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenNonExistingNewsId_whenReceiveNewsById_thenReturn404() {
        Long id = 1L;
        String requestUrl = createFindByIdUrl(id);

        get(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createFindByIdUrl(Long id) {
        return String.format("%s/news/%d", requestPrefix, id);
    }

    @Test
    void givenValidRequestTo_whenUpdateNews_thenReturnTo() {
        Long id = 1L;
        String requestUrl = createUpdateNewsUrl(id);

        NewsRequestTo requestTo = new NewsRequestTo();
        requestTo.setContent("content");
        requestTo.setTitle("title");
        News news = new News();
        news.setId(id);
        NewsResponseTo responseTo = new NewsResponseTo();
        responseTo.setContent(requestTo.getContent());

        Mockito.doNothing().when(newsService).save(news);
        Mockito.when(newsToConverter.convertToEntity(requestTo)).thenReturn(news);
        Mockito.when(newsToConverter.convertToDto(news)).thenReturn(responseTo);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl);
        response
                .then()
                .statusCode(200);

        NewsResponseTo actualResponse = response.getBody().as(NewsResponseTo.class);

        Assertions.assertEquals(responseTo, actualResponse);
    }

    @Test
    void givenInvalidRequestTo_whenUpdateNews_thenReturn400() {
        Long id = 1L;
        String requestUrl = createUpdateNewsUrl(id);

        NewsRequestTo requestTo = new NewsRequestTo();


        given()
                .contentType(ContentType.JSON)
                .body(requestTo)
                .when()
                .put(requestUrl)
                .then()
                .statusCode(400);
    }

    private String createUpdateNewsUrl(Long id) {
        return String.format("%s/news/update/%d", requestPrefix, id);
    }

    @Test
    void givenExistingNewsId_whenDeleteNewsById_thenReturn204() {
        Long id = 1L;
        String requestUrl = createDeleteNewsUrl(id);

        Mockito.doNothing().when(newsService).deleteById(id);

        delete(requestUrl)
                .then()
                .statusCode(204);
    }

    @Test
    void givenNonExistingNewsId_whenDeleteNewsById_thenReturn204() {
        Long id = 1L;
        String requestUrl = createDeleteNewsUrl(id);

        delete(requestUrl)
                .then()
                .statusCode(404);
    }

    private String createDeleteNewsUrl(Long id) {
        return String.format("%s/news/delete/%d", requestPrefix, id);
    }
}