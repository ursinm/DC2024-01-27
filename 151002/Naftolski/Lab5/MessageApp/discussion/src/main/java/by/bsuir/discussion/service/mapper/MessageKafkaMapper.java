package by.bsuir.discussion.service.mapper;

import by.bsuir.discussion.event.MessageInTopicTo;
import by.bsuir.discussion.event.MessageOutTopicTo;
import by.bsuir.discussion.model.entity.Message;
import by.bsuir.discussion.model.response.MessageResponseTo;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageKafkaMapper {
    MessageOutTopicTo responseDtoToOutTopicDto(MessageResponseTo messageResponseTo);
    List<MessageOutTopicTo> responseDtoToOutTopicDto(Collection<MessageResponseTo> messageResponseTo);

    @Mapping(target = "key", source = ".")
    Message toEntity(MessageInTopicTo messageInTopicTo);

    @Mapping(target = ".", source = "key")
    MessageOutTopicTo entityToDto(Message message);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "key.storyId", source = "storyId")
    @Mapping(target = "state", ignore = true)
    Message partialUpdate(MessageInTopicTo messageInTopicTo, @MappingTarget Message message);
}
