package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.model.entity.Comment;
import by.bsuir.discussion.model.request.CommentRequestTo;
import by.bsuir.discussion.model.response.CommentResponseTo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", source = "key.id")
    @Mapping(target = "storyId", source = "key.storyId")
    CommentResponseTo getResponseTo(Comment comment);

    List<CommentResponseTo> getListResponseTo(Iterable<Comment> comments);

    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.storyId", source = "storyId")
    Comment getComment(CommentRequestTo commentRequestTo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "key.storyId", source = "storyId")
    Comment partialUpdate(CommentRequestTo commentRequestTo, @MappingTarget Comment comment);
}
