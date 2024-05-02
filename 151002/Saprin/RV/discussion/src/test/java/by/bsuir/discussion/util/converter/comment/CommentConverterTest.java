package by.bsuir.discussion.util.converter.comment;

import by.bsuir.discussion.bean.Comment;
import by.bsuir.discussion.dto.CommentRequestTo;
import by.bsuir.discussion.dto.CommentResponseTo;
import by.bsuir.discussion.util.converter.CommentConverter;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class CommentConverterTest {

    @Test
    void convertToResponse_shouldConvertCommentToResponse() {
        Comment comment = new Comment(BigInteger.ONE, "local", BigInteger.ONE, "Test Comment");
        CommentConverter commentConverter = new CommentConverter();

        CommentResponseTo response = commentConverter.convertToResponse(comment);

        assertNotNull(response);
        assertEquals(comment.getCom_id(), response.getId());
        assertEquals(comment.getCom_content(), response.getContent());
    }

    @Test
    void convertToEntity_shouldConvertCommentRequestToComment() {
        CommentRequestTo commentRequest = new CommentRequestTo(BigInteger.ONE, BigInteger.ONE, "Test Comment");
        CommentConverter commentConverter = new CommentConverter();

        Comment comment = commentConverter.convertToEntity(commentRequest);

        assertNotNull(comment);
        assertEquals(commentRequest.getId(), comment.getCom_id());
        assertEquals(commentRequest.getContent(), comment.getCom_content());
    }
}
