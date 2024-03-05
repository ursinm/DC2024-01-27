package by.bsuir.poit.dc.rest.controllers;

import by.bsuir.poit.dc.rest.AbstractTestContainerTest;
import by.bsuir.poit.dc.rest.TestContainersConfiguration;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Paval Shlyk
 * @since 24/02/2024
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestContainersConfiguration.class)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles({"test"})
public class UserControllerTest extends AbstractTestContainerTest {
    @Autowired
    MockMvc mockMvc;

    //    @Container
//    @ServiceConnection
//    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
//								  .withUsername("test")
//								  .withPassword("test")
//								  .withDatabaseName("test");
    @Test
    @Order(0)
    public void JsonTest() throws Exception {
	mockMvc.perform(get("/api/v1.0/users/1"))
	    .andExpectAll(
		status().is2xxSuccessful(),
		content().json("""
		    {
			"id": 1,
			"login": "m_rgan",
			"firstname": "Morgan",
			"lastname": "Paulsen"
		    }
		    """)
	    );
    }

    @Test
    @Order(1)
    public void findAllUsers_ReturnUserList() throws Exception {
	mockMvc.perform(delete("/api/v1.0/users/1"))
	    .andExpect(
		status().is(204)
	    );
    }
}
