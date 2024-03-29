package by.bsuir.rv.service.comment;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import by.bsuir.rv.exception.DuplicateEntityException;
import by.bsuir.rv.exception.EntityNotFoundException;
import by.bsuir.rv.repository.comment.CommentRepository;
import by.bsuir.rv.repository.issue.IssueRepository;
import by.bsuir.rv.service.comment.impl.CommentService;
import by.bsuir.rv.util.converter.comment.CommentConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private CommentConverter commentConverter;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getComments_shouldReturnListOfComments() {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();

        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepository.findAll()).thenReturn(comments);

        Issue issue1 = new Issue();
        Issue issue2 = new Issue();
        when(issueRepository.findAllById(any())).thenReturn(Arrays.asList(issue1, issue2));

        when(commentConverter.convertToResponse(any())).thenReturn(new CommentResponseTo());

        List<CommentResponseTo> result = commentService.getComments();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void addComment_shouldSaveComment() throws DuplicateEntityException, EntityNotFoundException {
        CommentRequestTo commentRequest = new CommentRequestTo();
        Comment comment = new Comment();
        Issue issue = new Issue();

        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentRepository.findById(commentRequest.getId())).thenReturn(Optional.of(comment));
        when(issueRepository.findById(commentRequest.getIssueId())).thenReturn(Optional.of(issue));
        when(commentConverter.convertToEntity(commentRequest, issue)).thenReturn(comment);

        commentService.addComment(commentRequest);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void deleteComment_shouldDeleteComment() throws EntityNotFoundException {
        BigInteger commentId = BigInteger.valueOf(1);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(new Comment()));

        commentService.deleteComment(commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void updateComment_shouldUpdateComment() throws EntityNotFoundException, DuplicateEntityException {
        BigInteger commentId = BigInteger.valueOf(1);
        CommentRequestTo commentRequest = new CommentRequestTo(commentId, BigInteger.ONE, "Content");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(new Comment(commentId, new Issue(), "Content")));
        when(issueRepository.findById(commentRequest.getIssueId())).thenReturn(Optional.of(new Issue()));

        commentService.updateComment(commentRequest);

        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(any());
    }

    @Test
    void getCommentById_shouldReturnCommentById() throws EntityNotFoundException {
        BigInteger commentId = BigInteger.valueOf(1);
        Comment comment = new Comment(commentId, new Issue(), "Content");
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Issue issue = new Issue();
        when(issueRepository.findById(comment.getCom_issue().getIss_id())).thenReturn(Optional.of(issue));

        when(commentConverter.convertToResponse(comment)).thenReturn(new CommentResponseTo());

        CommentResponseTo result = commentService.getCommentById(commentId);

        assertNotNull(result);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentConverter, times(1)).convertToResponse(comment);
    }
}
