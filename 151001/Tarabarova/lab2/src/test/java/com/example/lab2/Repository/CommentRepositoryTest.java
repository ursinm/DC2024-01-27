package com.example.lab2.Repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.Model.Comment;
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
public class CommentRepositoryTest {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private EditorRepository editorRepository;


    @Test
    public void testFindById(){
        Editor editor = new Editor(0,"1010", "1010101010", "vika", "loziuk");
        Editor resCr = editorRepository.save(editor);

        Story story = new Story(0,resCr,"0000","1111",null,null,null);
        Story resIss = storyRepository.save(story);

        Comment comment = new Comment(0,resIss,"1212" );
        Comment res = commentRepository.save(comment);
        assertEquals(commentRepository.findById(res.getId()), Optional.of(res));
    }

    @Test
    public void testSave(){
        Editor editor = new Editor(0,"1010", "1010101010", "vika", "loziuk");
        Editor resCr = editorRepository.save(editor);

        Story story = new Story(0,resCr,"0000","1111",null,null,null);
        Story resIss = storyRepository.save(story);

        Comment comment = new Comment(0,resIss,"1212" );
        commentRepository.save(comment);
        assertNotEquals(comment.getId(), 0);
    }

    @Test
    public void testDeleteById(){
        Editor editor = new Editor(0,"1010", "1010101010", "vika", "loziuk");
        Editor resCr = editorRepository.save(editor);

        Story story = new Story(0,resCr,"0000","1111",null,null,null);
        Story resIss = storyRepository.save(story);

        Comment comment = new Comment(0,resIss,"1212" );
        Comment res = commentRepository.save(comment);
        commentRepository.deleteById(res.getId());
        assertFalse(commentRepository.existsById(res.getId()));
    }
}