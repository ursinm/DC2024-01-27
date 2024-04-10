package by.bsuir.discussion.util.converter;


import by.bsuir.discussion.bean.Comment;
import by.bsuir.discussion.dto.CommentRequestTo;
import by.bsuir.discussion.dto.CommentResponseTo;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public CommentResponseTo convertToResponse(Comment comment) {
        return new CommentResponseTo(comment.getCom_id(), comment.getCom_issueId(), comment.getCom_content());
    }

    public Comment convertToEntity(CommentRequestTo comment) {
        return new Comment(comment.getId(), "local", comment.getIssueId(), comment.getContent());
    }
}
