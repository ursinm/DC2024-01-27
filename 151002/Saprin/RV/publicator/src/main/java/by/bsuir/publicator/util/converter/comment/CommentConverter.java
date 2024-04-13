package by.bsuir.publicator.util.converter.comment;


import by.bsuir.publicator.bean.Comment;
import by.bsuir.publicator.dto.CommentRequestTo;
import by.bsuir.publicator.dto.CommentResponseTo;
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
