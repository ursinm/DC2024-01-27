package com.example.restapplication.mappers;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.entites.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toMessage(MessageRequestTo message);
    @Mapping(target = "storyId", source = "message.story.id")
    MessageResponseTo toMessageResponse(Message message);
}
