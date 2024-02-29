package by.bsuir.mapper;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentRequestToComment(CommentRequestTo commentRequestTo);

    CommentResponseTo commentToCommentResponse(Comment comment);
}
