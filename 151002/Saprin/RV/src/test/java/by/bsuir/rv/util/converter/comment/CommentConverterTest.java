package by.bsuir.rv.util.converter.comment;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentConverterTest {

    private final Issue issue = new Issue(BigInteger.ONE, BigInteger.ONE, "Title", "Text", new Date(), new Date());

    @Test
    void convertToResponse_shouldConvertCommentToResponse() {
        Comment comment = new Comment(BigInteger.ONE, BigInteger.ONE, "Test Comment");
        CommentConverter commentConverter = new CommentConverter();

        CommentResponseTo response = commentConverter.convertToResponse(comment);

        assertNotNull(response);
        assertEquals(comment.getId(), response.getId());
        assertEquals(issue.getIss_id(), response.getIssueId());
        assertEquals(comment.getContent(), response.getContent());
    }

    @Test
    void convertToEntity_shouldConvertCommentRequestToComment() {
        CommentRequestTo commentRequest = new CommentRequestTo(BigInteger.ONE, issue.getIss_id(), "Test Comment");
        CommentConverter commentConverter = new CommentConverter();

        Comment comment = commentConverter.convertToEntity(commentRequest);

        assertNotNull(comment);
        assertEquals(commentRequest.getId(), comment.getId());
        assertEquals(commentRequest.getContent(), comment.getContent());
        assertEquals(commentRequest.getIssueId(), comment.getIssueId());
    }
}
