package by.bsuir.publisher.service.mapper;

import by.bsuir.publisher.event.MessageInTopicTo;
import by.bsuir.publisher.event.MessageOutTopicTo;
import by.bsuir.publisher.model.request.MessageRequestTo;
import by.bsuir.publisher.model.response.MessageResponseTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageResponseTo toDto(MessageOutTopicTo messageOutTopicEvent);

    List<MessageResponseTo> toDto(Collection<MessageOutTopicTo> messageOutTopicTo);

    @Mapping(target = "country", ignore = true)
    MessageInTopicTo toInTopicDto(MessageRequestTo messageResponseTo);

    List<MessageInTopicTo> toInTopicDto(Collection<MessageRequestTo> messageRequestTo);
}
