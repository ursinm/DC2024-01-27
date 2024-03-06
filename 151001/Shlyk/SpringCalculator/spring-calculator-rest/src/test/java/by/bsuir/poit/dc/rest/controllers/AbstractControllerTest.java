package by.bsuir.poit.dc.rest.controllers;

import by.bsuir.poit.dc.rest.TestContainersConfiguration;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@DirtiesContext
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@SpringBootTest(classes = TestContainersConfiguration.class)
@TestMethodOrder(OrderAnnotation.class)
public class AbstractControllerTest {

}
