package by.bsuir.poit.dc.rest.controllers;

import by.bsuir.poit.dc.rest.dao.NewsRepository;
import com.sun.source.tree.ModuleTree;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */

public class NewsControllerTest extends AbstractControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    public void insertNews() throws Exception {
	mockMvc.perform(
		post("/api/v1.0/news")
		    .contentType(MediaType.APPLICATION_JSON)
		    .content("""
			{
			  "title": "The 5 top presidents of USA",
			  "content": "The most popular president of the USA is Yosef Stalin! ",
			  "userId": 3
			}
			""")
	    )
	    .andExpectAll(
		status().is(201)
	    );
    }

    @Test
    @Order(2)
    public void duplicateNewsTest() throws Exception {
	mockMvc.perform(
	    post("/api/v1.0/news")
		.contentType(MediaType.APPLICATION_JSON)
		.content("""
		    {
		       "title": "The 5 top presidents of USA",
		       "content": "Not! Trump is the best president! ",
		       "userId": 2
		    }
		          """)
	).andExpectAll(
	    status().is(403)
	);
    }

    @Test
    @Order(3)
    public void deleteNoteTest() throws Exception {
	//retrieve data base content
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/news/1/notes")
	    .then().assertThat()
	    .status(HttpStatus.OK)
	    .body("id", hasItems(1, 2));
	//first remove of content
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .delete("/api/v1.0/notes/1")
	    .then().assertThat()
	    .status(HttpStatus.NO_CONTENT);
	//second remove of content
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .delete("/api/v1.0/notes/1")
	    .then().assertThat()
	    .status(HttpStatus.NOT_FOUND);
	//retrieve data again
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/news/1/notes")
	    .then().assertThat()
	    .status(HttpStatus.OK)
	    .body("id", hasItems(2));
    }

    private NewsRepository newsRepository;
    @Order(4)
    public void databaseConnectionFailedTest() throws Exception {
	Mockito.when(newsRepository.findById(anyLong()))
	    .thenThrow(DataAccessException.class);
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/news/1")
	    .then().assertThat()
	    .status(HttpStatus.INTERNAL_SERVER_ERROR);

	Mockito.when(newsRepository.findById(anyLong()))
	    .thenReturn(Optional.empty());

	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/news/1")
	    .then().assertThat()
	    .status(HttpStatus.NOT_FOUND);

    }
}
