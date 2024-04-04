package by.bsuir.rv.util.converter.comment;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    public CommentResponseTo convertToResponse(Comment comment) {
        return new CommentResponseTo(comment.getCom_id(), comment.getCom_issue().getIss_id(), comment.getCom_content());
    }

    public Comment convertToEntity(CommentRequestTo comment, Issue issue) {
        return new Comment(comment.getId(), issue, comment.getContent());
    }
}
