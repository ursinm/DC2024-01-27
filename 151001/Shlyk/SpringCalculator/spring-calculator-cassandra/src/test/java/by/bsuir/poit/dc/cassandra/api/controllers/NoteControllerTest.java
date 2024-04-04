package by.bsuir.poit.dc.cassandra.api.controllers;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Paval Shlyk
 * @since 08/03/2024
 */
@SpringBootTest(classes = ContainerTestConfiguration.class)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles({"test"})
class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String KEYSPACE_NAME = "test";
    @Container
    public static final CassandraContainer<?> cassandra =
	(CassandraContainer<?>) new CassandraContainer("cassandra:3.11.3")
				    .withExposedPorts(9042);

    @BeforeAll
    public static void beforeAll() {
	cassandra.start();
    }

    @AfterAll
    public static void afterAll() {
	cassandra.stop();
    }

    @BeforeAll
    static void setupCassandraConnectionProperties() {
	System.setProperty("CASSANDRA_KEYSPACE_NAME", KEYSPACE_NAME);
	System.setProperty("CASSANDRA_CONTACT_POINTS", cassandra.getHost());
	System.setProperty("CASSANDRA_PORT", String.valueOf(cassandra.getMappedPort(9042)));
	System.setProperty("CASSANDRA_DATACENTER", cassandra.getLocalDatacenter());
	createKeyspace(cassandra.getCluster());
    }

    static void createKeyspace(Cluster cluster) {
	try (Session session = cluster.connect()) {
	    session.execute(STR."CREATE KEYSPACE IF NOT EXISTS \{KEYSPACE_NAME} WITH replication = \n{'class':'SimpleStrategy','replication_factor':'1'};");
	}
    }

    @Test
    @Order(1)
    void checkContainerIsRunning() {
	assertTrue(cassandra.isRunning());
    }

    @Test
    @Order(2)
    void saveNote() throws Exception {
	mockMvc.perform(
	    post("/api/v1.0/notes")
		.contentType(MediaType.APPLICATION_JSON)
		.content("""
		    		{
		    			"newsId": 1,
		    			"country": "bel",
		    			"content": "The very long text"
		    		}
		    """)
	).andExpectAll(
	    status().is(201),
	    content().json("""
					{
					"id": 1,
					"country": "bel",
					"newsId": 1,
					"content": "The very long text"
					} 
		""")
	);
    }

    @Test
    @Order(3)
    void checkUpdateTest() {
	given()
	    .mockMvc(mockMvc)
	    .contentType(ContentType.JSON)
	    .body("""
			    {
					"country": "bel",
					"newsId": 1,
					"content": "The another very long text"
			    } 
		""")
	    .post("/api/v1.0/notes")
	    .then().assertThat()
	    .status(HttpStatus.CREATED)
	    .body("id", equalTo(2));
	//check that id was changed
	given()
	    .mockMvc(mockMvc)
	    .contentType(ContentType.JSON)
	    .body("""
		   			{
		   			"id": 2,
					"country": "ru",
					"newsId": 1,
					"content": "The small text"
			}
		""")
	    .put("/api/v1.0/notes")
	    .then().assertThat()
	    .status(HttpStatus.BAD_REQUEST);
    }
}