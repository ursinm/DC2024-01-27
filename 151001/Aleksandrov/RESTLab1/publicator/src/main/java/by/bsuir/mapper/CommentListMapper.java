package by.bsuir.mapper;

import by.bsuir.dto.CommentRequestTo;
import by.bsuir.dto.CommentResponseTo;
import by.bsuir.entities.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface CommentListMapper {
    List<Comment> toCommentList(List<CommentRequestTo> comments);
    List<CommentResponseTo> toCommentResponseList(List<Comment> comments);
}
