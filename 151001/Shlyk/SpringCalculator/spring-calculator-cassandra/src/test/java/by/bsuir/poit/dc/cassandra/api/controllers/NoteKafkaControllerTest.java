package by.bsuir.poit.dc.cassandra.api.controllers;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * @author Paval Shlyk
 * @since 06/04/2024
 */
@SpringBootTest(classes = ContainerTestConfiguration.class)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles({"test"})
public class NoteKafkaControllerTest {
    @Container
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));

    @BeforeAll
    public static void setUpKafka() {
	System.setProperty("KAFKA_BOOTSTRAP_SERVER", kafka.getBootstrapServers());
    }
}

