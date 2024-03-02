package by.bsuir.mapper;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.entities.Comment;
import by.bsuir.entities.Issue;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentRequestToComment(CommentRequestTo commentRequestTo);

    @Mapping(target = "issueId", source = "comment.issue.id")
    CommentResponseTo commentToCommentResponse(Comment comment);
}
