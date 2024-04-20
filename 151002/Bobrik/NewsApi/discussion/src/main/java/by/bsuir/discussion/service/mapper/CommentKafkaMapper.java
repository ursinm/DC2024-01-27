package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.model.response.CommentResponseTo;
import by.bsuir.discussion.event.CommentInTopicTo;
import by.bsuir.discussion.event.CommentOutTopicTo;
import by.bsuir.discussion.model.entity.Comment;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentKafkaMapper {

    CommentOutTopicTo responseDtoToOutTopicDto(CommentResponseTo commentResponseTo);

    List<CommentOutTopicTo> responseDtoToOutTopicDto(Collection<CommentResponseTo> commentResponseTo);

    @Mapping(target = "key", source = ".")
    Comment toEntity(CommentInTopicTo dto);

    @Mapping(target = ".", source = "key")
    CommentOutTopicTo entityToDto(Comment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "key.newsId", source = "newsId")
    Comment partialUpdate(CommentInTopicTo commentInTopicTo, @MappingTarget Comment comment);
}