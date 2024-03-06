package by.bsuir.poit.dc.rest.controllers;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */

public class NewsControllerTest extends AbstractControllerTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public void init() {
	RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @Order(0)
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
    @Order(1)
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
    @Order(2)
    public void changeNotesTest() throws Exception {
	given()
	    .when()
	    .get("/api/v1.0/news/1/notes")
	    .then().assertThat()
	    .statusCode(200)
	    .body("id", hasItems("1", "2"));
//	given()
//	    .when()
//	    .post("/api/v1.0/notes", """
//	""")
    }
}
