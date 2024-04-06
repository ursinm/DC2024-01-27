package by.bsuir.poit.dc.cassandra.api.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
@SpringBootTest(classes = ContainerTestConfiguration.class)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
public class NoteKafkaControllerTest {
}
