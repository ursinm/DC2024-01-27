package com.example.lab2.Repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.Model.Editor;
import jakarta.transaction.Transactional;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EditorRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();
    @Autowired
    private EditorRepository editorRepository;
    @Test
    public void testFindById(){
        Editor editor = new Editor(0,"1010", "1010101010", "vika", "loziuk");
        Editor res = editorRepository.save(editor);
        assertEquals(editorRepository.findById(res.getId()), Optional.of(res));
    }
    @Test
    public void testSave(){
        Editor editor = new Editor(0,"10101", "1010101010", "vika", "loziuk");
        editorRepository.save(editor);
        assertNotEquals(editor.getId(),0);
    }
    @Test
    public void testDeleteById(){
        Editor editor = new Editor(0,"10103", "1010101010", "vika", "loziuk");
        Editor res = editorRepository.save(editor);
        editorRepository.deleteById(res.getId());
        assertFalse(editorRepository.existsById(res.getId()));
    }
}