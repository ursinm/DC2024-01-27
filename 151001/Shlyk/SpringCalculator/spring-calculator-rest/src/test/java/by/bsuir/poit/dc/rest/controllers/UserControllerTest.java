package by.bsuir.poit.dc.rest.controllers;

import by.bsuir.poit.dc.rest.TestContainersConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Paval Shlyk
 * @since 24/02/2024
 */
@SpringBootTest(classes = TestContainersConfiguration.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void findAllUsers_ReturnUserList() throws Exception {
	this.mockMvc.perform(get("/api/v1.0/users"))
	    .andExpectAll(
		status().isOk(),
		content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
		content().json("""
		    [
		        {id: 1, "username": "Paval"},
		        {id: 2, "username": "Paval"},
		        {id: 3, "username": "Paval"},
		        {id: 4, "username": "Paval"}
		    ]
		    """)
	    );
    }
}
