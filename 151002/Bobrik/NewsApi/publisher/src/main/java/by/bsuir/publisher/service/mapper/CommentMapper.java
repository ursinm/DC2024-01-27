package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.model.request.CommentRequestTo;
import by.bsuir.publisher.model.response.CommentResponseTo;
import by.bsuir.publisher.event.CommentInTopicTo;
import by.bsuir.publisher.event.CommentOutTopicTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentResponseTo toDto(CommentOutTopicTo messageOutTopicEvent);

    List<CommentResponseTo> toDto(Collection<CommentOutTopicTo> commentOutTopicTo);

    @Mapping(target = "country", ignore = true)
    CommentInTopicTo toInTopicDto(CommentRequestTo commentResponseTo);

    List<CommentInTopicTo> toInTopicDto(Collection<CommentRequestTo> commentRequestTo);
}