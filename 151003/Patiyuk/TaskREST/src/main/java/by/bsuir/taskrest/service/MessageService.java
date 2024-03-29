package by.bsuir.taskrest.service;

import by.bsuir.taskrest.dto.request.MessageRequestTo;
import by.bsuir.taskrest.dto.response.MessageResponseTo;

import java.util.List;

public interface MessageService {
    List<MessageResponseTo> getAllMessages();
    MessageResponseTo getMessageById(Long id);
    List<MessageResponseTo> getMessagesByStoryId(Long id);
    MessageResponseTo createMessage(MessageRequestTo message);
    MessageResponseTo updateMessage(MessageRequestTo message);
    MessageResponseTo updateMessage(Long id, MessageRequestTo message);
    void deleteMessage(Long id);
}
