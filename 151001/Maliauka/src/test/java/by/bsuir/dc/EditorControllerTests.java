package by.bsuir.dc;

import by.bsuir.dc.features.editor.EditorRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest
public class EditorControllerTests {
    @LocalServerPort
    private Integer port;

    @Autowired
    EditorRepository editorRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        editorRepository.deleteAll();
    }
}
