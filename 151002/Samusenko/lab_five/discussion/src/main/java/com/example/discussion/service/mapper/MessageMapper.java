package com.example.discussion.service.mapper;

import com.example.discussion.model.entity.Message;
import com.example.discussion.model.entity.MessageKey;
import com.example.discussion.model.request.MessageRequestTo;
import com.example.discussion.model.response.MessageResponseTo;

import java.util.List;

public interface MessageMapper {

    MessageRequestTo messageToRequestTo(Message message);

    List<MessageRequestTo> messageToRequestTo(Iterable<Message> messages);

    Message dtoToEntity(MessageRequestTo messageRequestTo, String country);

    MessageResponseTo messageToResponseTo(Message message);

    List<MessageResponseTo> messageToResponseTo(Iterable<Message> message);
}
