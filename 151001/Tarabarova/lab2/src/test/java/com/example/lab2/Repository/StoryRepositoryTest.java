package com.example.lab2.Repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.Model.Editor;
import com.example.lab2.Model.Story;
import jakarta.transaction.Transactional;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StoryRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private EditorRepository editorRepository;

    @Test
    public void testFindById(){
        Editor editor = new Editor(0,"1010", "1010101010", "vika", "loziuk");
        Editor resCr = editorRepository.save(editor);

        Story story = new Story(0,resCr,"1100","1111",null,null,null);
        Story res = storyRepository.save(story);
        assertEquals(storyRepository.findById(res.getId()), Optional.of(res));
    }
    @Test
    public void testSave(){
        Editor editor = new Editor(0,"10101", "1010101010", "vika", "loziuk");
        Editor resCr = editorRepository.save(editor);

        Story story = new Story(0,resCr,"1122","1111",null,null,null);
        storyRepository.save(story);
        assertNotEquals(story.getId(), 0);
    }
    @Test
    public void testDeleteById(){
        Editor editor = new Editor(0,"10102", "1010101010", "vika", "loziuk");
        Editor resCr = editorRepository.save(editor);

        Story story = new Story(0,resCr,"1133","1111",null,null,null);
        Story res = storyRepository.save(story);
        storyRepository.deleteById(res.getId());
        assertFalse(storyRepository.existsById(res.getId()));
    }

}