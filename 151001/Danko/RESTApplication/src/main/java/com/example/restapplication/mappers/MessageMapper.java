package com.example.restapplication.mappers;

import com.example.restapplication.dto.MessageRequestTo;
import com.example.restapplication.dto.MessageResponseTo;
import com.example.restapplication.entites.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toMessage(MessageRequestTo message);
    MessageResponseTo toMessageResponse(Message message);
}
