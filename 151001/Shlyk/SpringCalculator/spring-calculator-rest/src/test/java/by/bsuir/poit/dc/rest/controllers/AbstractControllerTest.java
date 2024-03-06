package by.bsuir.poit.dc.rest.controllers;

import by.bsuir.poit.dc.rest.TestContainersConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Paval Shlyk
 * @since 06/03/2024
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestContainersConfiguration.class)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles({"test"})
public class AbstractControllerTest {

}
