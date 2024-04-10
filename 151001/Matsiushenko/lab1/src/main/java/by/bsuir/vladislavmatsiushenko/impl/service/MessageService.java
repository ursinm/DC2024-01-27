package by.bsuir.vladislavmatsiushenko.impl.service;


import by.bsuir.vladislavmatsiushenko.api.Service;
import by.bsuir.vladislavmatsiushenko.api.MessageMapper;
import by.bsuir.vladislavmatsiushenko.impl.bean.Message;
import by.bsuir.vladislavmatsiushenko.impl.dto.*;
import by.bsuir.vladislavmatsiushenko.impl.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageService implements Service<MessageResponseTo, MessageRequestTo> {
    @Autowired
    private MessageRepository messageRepository;

    public MessageService() {}

    public List<MessageResponseTo> getAll() {
        List<Message> messageList = messageRepository.getAll();
        List<MessageResponseTo> resultList = new ArrayList<>();
        for (int i = 0; i < messageList.size(); i++) {
            resultList.add(MessageMapper.INSTANCE.MessageToMessageResponseTo(messageList.get(i)));
        }

        return resultList;
    }

    public MessageResponseTo update(MessageRequestTo updatingMessage) {
        Message message = MessageMapper.INSTANCE.MessageRequestToToMessage(updatingMessage);
        if (validateMessage(message)) {
            boolean result = messageRepository.update(message);
            MessageResponseTo responseTo = result ? MessageMapper.INSTANCE.MessageToMessageResponseTo(message) : null;

            return responseTo;
        }
        return new MessageResponseTo();
    }

    public MessageResponseTo get(long id) {
        return MessageMapper.INSTANCE.MessageToMessageResponseTo(messageRepository.get(id));
    }

    public MessageResponseTo delete(long id) {
        return MessageMapper.INSTANCE.MessageToMessageResponseTo(messageRepository.delete(id));
    }

    public MessageResponseTo add(MessageRequestTo messageRequestTo) {
        Message message = MessageMapper.INSTANCE.MessageRequestToToMessage(messageRequestTo);

        return MessageMapper.INSTANCE.MessageToMessageResponseTo(messageRepository.insert(message));
    }

    private boolean validateMessage(Message message) {
        String name = message.getContent();

        return name.length() >= 2 && name.length() <= 32;
    }
}
