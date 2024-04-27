package com.example.discussion.mapper;

import com.example.discussion.dto.MessageRequestTo;
import com.example.discussion.dto.MessageResponseTo;
import com.example.discussion.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageRequestToMessage(MessageRequestTo messageRequestTo);
    MessageResponseTo messageToMessageResponse(Message message);
}
