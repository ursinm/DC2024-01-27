package by.bsuir.rv.util.converter.comment;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Editor;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentConverterTest {

    private final Issue issue = new Issue(BigInteger.ONE, new Editor(), "Title", "Text", new Date(), new Date());

    @Test
    void convertToResponse_shouldConvertCommentToResponse() {
        Comment comment = new Comment(BigInteger.ONE, issue, "Test Comment");
        CommentConverter commentConverter = new CommentConverter();

        CommentResponseTo response = commentConverter.convertToResponse(comment);

        assertNotNull(response);
        assertEquals(comment.getCom_id(), response.getId());
        assertEquals(issue.getIss_id(), response.getIssueId());
        assertEquals(comment.getCom_content(), response.getContent());
    }

    @Test
    void convertToEntity_shouldConvertCommentRequestToComment() {
        CommentRequestTo commentRequest = new CommentRequestTo(BigInteger.ONE, issue.getIss_id(), "Test Comment");
        CommentConverter commentConverter = new CommentConverter();

        Comment comment = commentConverter.convertToEntity(commentRequest, issue);

        assertNotNull(comment);
        assertEquals(commentRequest.getId(), comment.getCom_id());
        assertEquals(commentRequest.getContent(), comment.getCom_content());
        assertEquals(commentRequest.getIssueId(), comment.getCom_issue().getIss_id());
    }
}
