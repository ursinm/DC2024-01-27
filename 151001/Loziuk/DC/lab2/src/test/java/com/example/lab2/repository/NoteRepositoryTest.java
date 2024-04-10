package com.example.lab2.repository;

import com.example.lab2.ForEachPostgresqlContainer;
import com.example.lab2.model.Creator;
import com.example.lab2.model.Issue;
import com.example.lab2.model.Note;
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
public class NoteRepositoryTest {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = ForEachPostgresqlContainer.getInstance();

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private CreatorRepository creatorRepository;


    @Test
    public void testFindById(){
        Creator creator = new Creator(0,"1010", "1010101010", "vika", "loziuk");
        Creator resCr = creatorRepository.save(creator);

        Issue issue = new Issue(0,resCr,"0000","1111",null,null,null);
        Issue resIss = issueRepository.save(issue);

        Note note = new Note(0,resIss,"1212" );
        Note res = noteRepository.save(note);
        assertEquals(noteRepository.findById(res.getId()), Optional.of(res));
    }

    @Test
    public void testSave(){
        Creator creator = new Creator(0,"1010", "1010101010", "vika", "loziuk");
        Creator resCr = creatorRepository.save(creator);

        Issue issue = new Issue(0,resCr,"0000","1111",null,null,null);
        Issue resIss = issueRepository.save(issue);

        Note note = new Note(0,resIss,"1212" );
        noteRepository.save(note);
        assertNotEquals(note.getId(), 0);
    }

    @Test
    public void testDeleteById(){
        Creator creator = new Creator(0,"1010", "1010101010", "vika", "loziuk");
        Creator resCr = creatorRepository.save(creator);

        Issue issue = new Issue(0,resCr,"0000","1111",null,null,null);
        Issue resIss = issueRepository.save(issue);

        Note note = new Note(0,resIss,"1212" );
        Note res = noteRepository.save(note);
        noteRepository.deleteById(res.getId());
        assertFalse(noteRepository.existsById(res.getId()));
    }
}