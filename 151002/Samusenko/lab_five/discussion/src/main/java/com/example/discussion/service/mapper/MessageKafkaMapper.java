package com.example.discussion.service.mapper;

import com.example.discussion.event.MessageInTopicTo;
import com.example.discussion.event.MessageOutTopicTo;
import com.example.discussion.model.entity.Message;
import com.example.discussion.model.entity.MessageKey;
import com.example.discussion.model.response.MessageResponseTo;
import org.mapstruct.*;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageKafkaMapper {

    MessageOutTopicTo responseDtoToOutTopicDto(MessageResponseTo messageResponseTo);

    List<MessageOutTopicTo> responseDtoToOutTopicDto(Collection<MessageResponseTo> messageResponseTo);

    @Mapping(target = "key", source = ".")
    Message toEntity(MessageInTopicTo dto);

    @Mapping(target = ".", source = "key")
    MessageOutTopicTo entityToDto(Message entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "key.message_country", ignore = true)
    @Mapping(target = "key.id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "key.issueId", source = "issueId")
    Message partialUpdate(MessageInTopicTo messageInTopicTo, @MappingTarget Message message);
}
