package by.bsuir.rv.util.converter.comment;

import by.bsuir.rv.bean.Comment;
import by.bsuir.rv.bean.Issue;
import by.bsuir.rv.dto.CommentRequestTo;
import by.bsuir.rv.dto.CommentResponseTo;
import by.bsuir.rv.util.converter.IConverter;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements IConverter<Comment, CommentResponseTo, CommentRequestTo> {
    public CommentResponseTo convertToResponse(Comment comment) {
        return new CommentResponseTo(comment.getId(), comment.getIssueId(), comment.getContent());
    }

    @Override
    public Comment convertToEntity(CommentRequestTo comment) {
        return new Comment(comment.getId(), comment.getIssueId(), comment.getContent());
    }
}
