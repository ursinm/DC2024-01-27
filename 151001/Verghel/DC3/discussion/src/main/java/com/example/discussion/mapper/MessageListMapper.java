package com.example.discussion.mapper;

import com.example.discussion.dto.MessageRequestTo;
import com.example.discussion.dto.MessageResponseTo;
import com.example.discussion.model.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface MessageListMapper {
    List<Message> toMessageList(List<MessageRequestTo> messageRequestToList);

    List<MessageResponseTo> toMessageResponseList(List<Message> messageList);
}
