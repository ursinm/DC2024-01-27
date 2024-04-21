package com.example.discussion.service.dto_converter.interfaces;

import com.example.discussion.exception.model.dto_converting.ToConvertingException;
import com.example.discussion.model.dto.message.MessageRequestTo;
import com.example.discussion.model.dto.message.MessageResponseTo;
import com.example.discussion.model.entity.implementations.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageToConverter extends ToConverter<Message, MessageRequestTo, MessageResponseTo> {

    @Mapping(target = "news", ignore = true)
    Message convertToEntity(MessageRequestTo requestTo) throws ToConvertingException;
}
