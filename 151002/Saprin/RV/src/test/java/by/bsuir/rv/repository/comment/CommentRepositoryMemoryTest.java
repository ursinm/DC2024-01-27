package by.bsuir.rv.repository.comment;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.repository.exception.RepositoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommentRepositoryMemoryTest {

    @InjectMocks
    private CommentRepositoryMemory commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Arrange
        Comment comment = new Comment();

        // Act
        Comment savedComment = commentRepository.save(comment);

        // Assert
        assertNotNull(savedComment.getId());
        assertEquals(comment.getContent(), savedComment.getContent());
        assertEquals(1, commentRepository.findAll().size());
    }

    @Test
    void testFindAll() {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAll();

        assertEquals(2, comments.size());
    }

    @Test
    void testFindById() throws RepositoryException {
        Comment comment = new Comment();
        Comment savedComment = commentRepository.save(comment);

        Comment foundComment = commentRepository.findById(savedComment.getId());

        assertNotNull(foundComment);
        assertEquals(savedComment.getId(), foundComment.getId());
    }

    @Test
    void testFindById_shouldThrowExceptionIfCommentNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> commentRepository.findById(nonExistingId));
    }

    @Test
    void testFindAllById_shouldReturnCommentsByIds() throws RepositoryException {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment savedComment1 = commentRepository.save(comment1);
        Comment savedComment2 = commentRepository.save(comment2);

        List<BigInteger> idsToFind = Arrays.asList(savedComment1.getId(), savedComment2.getId());

        List<Comment> foundComments = commentRepository.findAllById(idsToFind);

        assertEquals(2, foundComments.size());
    }

    @Test
    void testFindAllById_shouldThrowExceptionIfCommentsNotFound() {
        BigInteger nonExistingId1 = BigInteger.valueOf(999);
        BigInteger nonExistingId2 = BigInteger.valueOf(1000);
        List<BigInteger> nonExistingIds = Arrays.asList(nonExistingId1, nonExistingId2);

        assertThrows(RepositoryException.class, () -> commentRepository.findAllById(nonExistingIds));
    }

    @Test
    void testDeleteById_shouldDeleteCommentById() throws RepositoryException {
        Comment comment = new Comment();
        Comment savedComment = commentRepository.save(comment);

        commentRepository.deleteById(savedComment.getId());

        assertEquals(0, commentRepository.findAll().size());
    }

    @Test
    void testDeleteById_shouldThrowExceptionIfCommentNotFound() {
        BigInteger nonExistingId = BigInteger.valueOf(999);

        assertThrows(RepositoryException.class, () -> commentRepository.deleteById(nonExistingId));
    }
}
