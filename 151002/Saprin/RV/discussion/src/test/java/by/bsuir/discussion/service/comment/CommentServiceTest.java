package by.bsuir.discussion.service.comment;

import by.bsuir.discussion.bean.Comment;
import by.bsuir.discussion.dto.CommentRequestTo;
import by.bsuir.discussion.dto.CommentResponseTo;
import by.bsuir.discussion.exception.DuplicateEntityException;
import by.bsuir.discussion.exception.EntityNotFoundException;
import by.bsuir.discussion.repository.comment.CommentRepository;
import by.bsuir.discussion.service.comment.impl.CommentService;
import by.bsuir.discussion.util.converter.CommentConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentConverter commentConverter;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetComments() {
        // Arrange
        List<Comment> comments = new ArrayList<>();
        CommentResponseTo response = new CommentResponseTo();
        when(commentRepository.findAll()).thenReturn(comments);
        when(commentConverter.convertToResponse(any())).thenReturn(response);

        // Act
        List<CommentResponseTo> result = commentService.getComments();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }


    @Test
    public void testDeleteComment() throws EntityNotFoundException {
        // Arrange
        BigInteger id = BigInteger.ONE;
        when(commentRepository.findByCom_id(id)).thenReturn(Optional.of(new Comment()));

        // Act & Assert
        assertDoesNotThrow(() -> commentService.deleteComment(id));
    }

    @Test
    public void testGetCommentById() throws EntityNotFoundException {
        // Arrange
        BigInteger id = BigInteger.ONE;
        when(commentRepository.findByCom_id(id)).thenReturn(Optional.of(new Comment()));
        CommentResponseTo response = new CommentResponseTo();
        when(commentConverter.convertToResponse(any())).thenReturn(response);

        // Act
        CommentResponseTo result = commentService.getCommentById(id);

        // Assert
        assertNotNull(result);
    }
}
